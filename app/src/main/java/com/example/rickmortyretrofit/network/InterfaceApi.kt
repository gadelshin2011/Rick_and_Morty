package com.example.rickmortyretrofit.network

import com.example.rickmortyretrofit.model.Results
import com.example.rickmortyretrofit.model.SearchCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InterfaceApi {
//    @GET("/api/episode/{id}")
//    suspend fun getProductById(@Path("id") id: Int): Product

    @GET("/api/character")
    suspend fun getCharacter(): SearchCharacterResponse

    @GET("/api/character/")
    suspend fun getPage(@Query("page") page: Int): SearchCharacterResponse

    @GET("/api/character/")
    suspend fun getSearchByName(@Query("name") name: String): SearchCharacterResponse

    @GET("/api/character/{id}")
    suspend fun getMultiCharacter(@Path("id") id: Int): SearchCharacterResponse

}