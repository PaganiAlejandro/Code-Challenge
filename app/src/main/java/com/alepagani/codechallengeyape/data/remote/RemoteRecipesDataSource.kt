package com.alepagani.codechallengeyape.data.remote

import com.alepagani.codechallengeyape.core.AppConstant
import com.alepagani.codechallengeyape.data.model.RecipesResponse
import javax.inject.Inject

class RemoteRecipesDataSource @Inject constructor(private val apiService: TastyApiService) {

    suspend fun getRecipesList(): RecipesResponse = apiService.getRecipesList(
        AppConstant.API_HOST,
        AppConstant.API_KEY,
        0,
        20,
        AppConstant.API_TAGS
    )
}