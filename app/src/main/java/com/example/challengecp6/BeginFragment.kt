package com.example.challengecp5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.challengecp6.R
import com.example.challengecp6.databinding.FragmentBeginBinding

class BeginFragment : Fragment() {
    private lateinit var binding: FragmentBeginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBeginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLoginbegin.setOnClickListener{
            it.findNavController().navigate(R.id.action_beginFragment_to_fragmentLogin)
        }
        binding.btnRegisterbegin.setOnClickListener{
            it.findNavController().navigate(R.id.action_beginFragment_to_fragmentRegister)
        }
    }
}