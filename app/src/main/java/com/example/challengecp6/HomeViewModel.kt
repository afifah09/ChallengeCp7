package com.example.challengecp5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {
    private val _dataMovie: MutableLiveData<DataMovieRespons> by lazy {
        MutableLiveData<DataMovieRespons>().also {
            getupcoming()
        }
    }
    val dataMovie: LiveData<DataMovieRespons> = _dataMovie

    //mengambil data movie
    private fun getupcoming(){
        ApiClient.instance.getupcoming().enqueue(object : Callback<DataMovieRespons> {
            override fun onResponse(call: Call<DataMovieRespons>, response: Response<DataMovieRespons>) {
                if (response.code() == 200){
                    _dataMovie.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DataMovieRespons>, t: Throwable) {
            }
        })
    }
    val detailMovie: MutableLiveData<Detail> = MutableLiveData()
    //mengambil data detail
    fun getdetail(id:Int){
        ApiClient.instance.getdetail(id).enqueue(object : Callback<Detail> {
            override fun onResponse(call: Call<Detail>, response: Response<Detail>) {
                if (response.code() == 200){
                    detailMovie.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Detail>, t: Throwable) {
            }
        })
    }
}