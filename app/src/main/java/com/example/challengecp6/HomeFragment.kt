package com.example.challengecp5

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.challengecp6.R
import com.example.challengecp6.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    //lateinit var homeViewModel: HomeViewModel
    private val homeViewModel by viewModel<HomeViewModel>()
    //lateinit var authRepository: AuthRepository
    private val authRepository by inject<AuthRepository>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = context?.getSharedPreferences("SHARED_FILE", Context.MODE_PRIVATE)
        //menampilkan list movie dari api
        homeViewModel.dataMovie.observe(viewLifecycleOwner){
            showlistmovie(it.results)
        }
        //tombol untuk profile
        binding.homeProfile.setOnClickListener{
            it.findNavController().navigate(R.id.action_fragmentHome_to_profileFragment)
        }
        //tombol untuk logout
        binding.btnOut.setOnClickListener{
           lifecycleScope.launch(Dispatchers.IO){
               authRepository.delatePreference()
           }
            it.findNavController().navigate(R.id.action_fragmentHome_to_fragmentLogin)
        }
    }
    //mengatur adaptor untuk ke receycleview
    private fun showlistmovie(results: List<Result>) {
        val adapter= MovieAdapter{
            val bundle = bundleOf("id" to it.id)
            findNavController().navigate(R.id.action_fragmentHome_to_detailFragment,bundle)
        }
        adapter.submitList(results)
        binding.reycycleview1.adapter=adapter
        binding.reycycleview2.adapter=adapter
    }
}