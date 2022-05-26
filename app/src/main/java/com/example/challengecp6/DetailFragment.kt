package com.example.challengecp5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.challengecp6.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(){
    private lateinit var binding: FragmentDetailBinding
    //lateinit var homeViewModel: HomeViewModel
    private val homeViewModel by viewModel<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.detailMovie.observe(viewLifecycleOwner){
            //menampilkan detail movie
            binding.apply {
                detJudul.text = it.title
                detSipnosis.text = it.overview
                Glide.with(binding.detGambar)
                    .load("https://image.tmdb.org/t/p/w500"+it.posterPath)
                    .into(detGambar)
            }
        }
        val id = arguments?.getInt("id",-1)
        homeViewModel.getdetail(id!!)


    }
}