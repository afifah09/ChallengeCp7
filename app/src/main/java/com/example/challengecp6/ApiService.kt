package com.example.challengecp5

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/upcoming")
    fun getupcoming():Call<DataMovieRespons>
    @GET("movie/{id}")
    fun getdetail(@Path("id")id:Int):Call<Detail>
}