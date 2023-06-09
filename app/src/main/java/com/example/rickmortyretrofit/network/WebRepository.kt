package com.example.rickmortyretrofit.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebRepository {

     val retrofit: InterfaceApi =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
            BASE_URL
        ).build().create(InterfaceApi::class.java)




    companion object {
        const val BASE_URL = "https://rickandmortyapi.com"
    }
}





