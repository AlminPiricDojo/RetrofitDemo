package com.example.retrofitdemo

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("eur.json")
    fun getAll(): Call<PriceData>
}