package com.alepagani.codechallengeyape.data.model

data class RecipesResponse(
    val count: Int? =0,
    val results: List<RecipeResult>? = listOf()
)