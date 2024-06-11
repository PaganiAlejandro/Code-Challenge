package com.alepagani.codechallengeyape.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alepagani.codechallengeyape.core.ResultResource
import com.alepagani.codechallengeyape.data.model.RecipeResult
import com.alepagani.codechallengeyape.domain.getRecipesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val getRecipesUseCases: getRecipesUseCases): ViewModel() {

    val recipesList: StateFlow<ResultResource<List<RecipeResult>>> = flow {
        runCatching {
            getRecipesUseCases()
        }.onSuccess { recipesResult ->
            emit(recipesResult)
        }.onFailure { throwable ->
            emit(ResultResource.Failure(Exception(throwable.message)))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ResultResource.Loading()
    )
}