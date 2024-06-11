package com.alepagani.codechallengeyape.data

import com.alepagani.codechallengeyape.core.ResultResource
import com.alepagani.codechallengeyape.data.model.RecipeResult
import com.alepagani.codechallengeyape.data.remote.RemoteRecipesDataSource
import com.alepagani.codechallengeyape.domain.Repository
import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val remoteDataSource: RemoteRecipesDataSource): Repository {
    override suspend fun getRecipes(): ResultResource<List<RecipeResult>> {
        remoteDataSource.getRecipesList().results?.let {
            return ResultResource.Success(it)
        } ?: return ResultResource.Failure(Exception("API doesn't have data"))
    }
}