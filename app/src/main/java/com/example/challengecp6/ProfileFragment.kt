package com.example.challengecp5

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.challengecp6.PermissionUtils
import com.example.challengecp6.StorageUtils
import com.example.challengecp6.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    //lateinit var authRepository: AuthRepository
    private val authRepository by inject<AuthRepository>()
    private var imageUri: Uri? = null
    private var imageSource = -1
    var userid : Int? = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = context?.getSharedPreferences("SHARED_FILE", Context.MODE_PRIVATE)
        var email = ""

        authRepository.emailPreferences().observe(viewLifecycleOwner){
            email=it
            Log.d("email", "onViewCreated:$it ")
            getUser(email)
        }

        binding.imgLogin.setOnClickListener {
            if (PermissionUtils.isPermissionsGranted(requireActivity(), getRequiredPermission()) {
                    activity?.let {
                        requestPermissionLauncher.launch(getRequiredPermission())
                        imageSource=1
                    }
                }){
                openGallery()
            }
        }
        binding.btnImgprof.setOnClickListener {
            if (PermissionUtils.isPermissionsGranted(requireActivity(), getRequiredPermission()) {
                    activity?.let {
                        requestPermissionLauncher.launch(getRequiredPermission())
                        imageSource=2
                    }
                }){
                openCamera()
            }
        }
        //fungsi updatenya
        binding.btnUpdet.setOnClickListener{
            val username = binding.etUpdateUsername.text.toString()
            val ubahpass = binding.etUbahPassword.text.toString()
            val foto = if (imageUri==null){
                ""
            }else{
                imageUri.toString()
            }
            val updateuser = User(userid,username,email.toString(), ubahpass,foto)
            lifecycleScope.launch(Dispatchers.IO){
                val newuser = authRepository.update(updateuser)
                activity?.runOnUiThread {
                    if (newuser != 0){
                        Toast.makeText(context, "Update Sukses", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    fun getUser(email:String){
        lifecycleScope.launch(Dispatchers.IO){
            val user = authRepository.getuser(email.toString())
            activity?.runOnUiThread {
                binding.apply {
                    etUpdateUsername.setText(user?.username)
                    etUpdateEmail.setText(user?.email)
                    etUpdateEmail.isEnabled = false
                    etUbahPassword.setText(user?.password)
                    if (imageUri==null){
                        imgLogin.setImageURI(user?.foto?.toUri())
                    }else{
                        imgLogin.setImageURI(imageUri)
                    }
                }
            }
            userid = user?.id
        }
    }
    private var galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imageUri = data?.data
            imageUri?.let { loadImage(it) }
        }
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap = result.data?.extras?.get("data") as Bitmap
                val uri = StorageUtils.savePhotoToExternalStorage(
                    context?.contentResolver,
                    UUID.randomUUID().toString(),
                    bitmap
                )
                imageUri = uri
                uri?.let {
                    loadImage(it)
                }
            }
        }

    private fun loadImage(uri: Uri) {
        binding.imgLogin.setImageURI(uri)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(cameraIntent)
    }

    private fun openGallery() {
        val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        galleryLauncher.launch(intentGallery)
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {
            openImage(imageSource)
        }
    }

    private fun openImage(imageSource: Int) {
        if (imageSource==1){
            openGallery()
        }else{
            openCamera()
        }
    }

    private fun getRequiredPermission(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        }
    }
}