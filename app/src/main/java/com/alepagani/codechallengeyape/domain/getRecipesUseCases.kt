package com.alepagani.codechallengeyape.domain

import javax.inject.Inject

class getRecipesUseCases @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke() = repository.getRecipes()
}