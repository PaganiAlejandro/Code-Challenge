package com.alepagani.codechallengeyape.domain

import com.alepagani.codechallengeyape.core.ResultResource
import com.alepagani.codechallengeyape.data.model.RecipeResult

interface Repository {

    suspend fun getRecipes(): ResultResource<List<RecipeResult>>
}