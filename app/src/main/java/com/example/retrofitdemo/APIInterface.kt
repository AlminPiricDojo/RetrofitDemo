package com.example.retrofitdemo

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    // we don't need headers for this demo
    @GET("eur.json")
    fun getPrices(): Call<Prices>

    @GET("eur.json")
    fun getAll(): Call<PriceData>
}