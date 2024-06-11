package com.alepagani.codechallengeyape.data.remote

import com.alepagani.codechallengeyape.data.model.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TastyApiService {

    @GET("recipes/list")
    suspend fun getRecipesList(
        @Header("x-rapidapi-host") host: String?,
        @Header("x-rapidapi-key") apiKey: String?,
        @Query("from") from: Int,
        @Query("size") size: Int,
        @Query("tags") tags: String?
    ) : RecipesResponse
}