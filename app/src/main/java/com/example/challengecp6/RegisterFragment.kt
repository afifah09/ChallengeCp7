package com.example.challengecp5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.challengecp6.databinding.FragmentRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    //private var myDatabase: MyDatabase? = null
    //lateinit var authRepository: AuthRepository
    private val authRepository by inject<AuthRepository>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //myDatabase = MyDatabase.getInstance(requireContext())
        binding.btnRegister.setOnClickListener {
            val user = User(
                null,
                binding.etRegisterUser.text.toString(),
                binding.etRegisterEmail.text.toString(),
                binding.etRegisterPassword.text.toString()
            )
            lifecycleScope.launch(Dispatchers.IO) {
                authRepository.insertUser(user)
                activity?.runOnUiThread{
                    it.findNavController().popBackStack()
                }
            }
        }
    }
}