package com.alepagani.codechallengeyape.data

import com.alepagani.codechallengeyape.core.ResultResource
import com.alepagani.codechallengeyape.data.model.RecipeResult
import com.alepagani.codechallengeyape.domain.Repository

class FakeRepositoryImplTest : Repository {
    override suspend fun getRecipes(): ResultResource<List<RecipeResult>> {
        val recipeList = listOf(
            RecipeResult(name = "Pasta", description = "Pasta", thumbnail_url = ""),
            RecipeResult(name = "Onion", description = "Onion", thumbnail_url = ""),
        )

        return ResultResource.Success(recipeList)
    }
}