package com.example.challengecp5

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.challengecp6.R
import com.example.challengecp6.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    //private var myDatabase: MyDatabase? = null
    lateinit var authRepository: AuthRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // myDatabase = MyDatabase.getInstance(requireContext())
        authRepository = AuthRepository(requireContext())
        val sharedPreference = context?.getSharedPreferences("SHARED_FILE", Context.MODE_PRIVATE)

        authRepository.emailPreferences.observe(viewLifecycleOwner){
            if (it==""){
                //tombol tegister
                binding.tvSignUp.setOnClickListener {
                    it.findNavController().navigate(R.id.action_fragmentLogin_to_fragmentRegister)
                }
                //tombol login
                binding.btnLogin.setOnClickListener {

                    val username = binding.etLoginUser.text.toString()
                    val password = binding.etLoginPassword.text.toString()

                    lifecycleScope.launch(Dispatchers.IO) {
                        val login = authRepository.login(username, password)
                        activity?.runOnUiThread{
                            //untuk mengecek apakah login sudah sukses atau belum
                            if (login==null){
                                Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                            }else{
                                //val editor = sharedPreference.edit()
                                //editor.putString("islogin",login.username)
                                //editor.putString("email",username)
                                //editor.apply()
                                    lifecycleScope.launch(Dispatchers.IO){
                                        authRepository.setEmailPreference(login.email)
                                        authRepository.setNamaPreference(username)
                                    }
                                it.findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
                            }
                        }
                    }
                }
            }else{
                findNavController().navigate(R.id.action_fragmentLogin_to_fragmentHome)
            }
        }
        val islogin = sharedPreference?.getString("islogin","")

    }
}