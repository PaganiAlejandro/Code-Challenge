package com.alepagani.codechallengeyape.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alepagani.codechallengeyape.core.ResultResource
import com.alepagani.codechallengeyape.data.model.Instruction
import com.alepagani.codechallengeyape.data.model.RecipeResult
import com.alepagani.codechallengeyape.domain.getRecipesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val getRecipesUseCases: getRecipesUseCases): ViewModel() {

    private val _recipesStateFlow = MutableStateFlow<ResultResource<List<RecipeResult>>>(ResultResource.Loading())
    val recipesStateFlow: StateFlow<ResultResource<List<RecipeResult>>> = _recipesStateFlow

    private var allRecipes: List<RecipeResult> = emptyList()

    init {
        fetchRecipes()
    }

    private fun fetchRecipes() {
        viewModelScope.launch {
            runCatching {
                getRecipesUseCases()
            }.onSuccess { recipesResult ->
                if (recipesResult is ResultResource.Success) {
                    allRecipes = recipesResult.data
                }
                _recipesStateFlow.emit(recipesResult)
            }.onFailure { throwable ->
                _recipesStateFlow.emit(ResultResource.Failure(Exception(throwable.message)))
            }
        }
    }

    fun filterRecipes(query: String) {
        viewModelScope.launch {
            val filteredList = if (query.isEmpty()) {
                allRecipes
            } else {
                allRecipes.filter { recipe ->
                    recipe.name.contains(query, ignoreCase = true) ||
                        recipeContainQueryInInstrucctions(recipe.instructions, query)
                }
            }
            _recipesStateFlow.emit(ResultResource.Success(filteredList))
        }
    }

    private fun recipeContainQueryInInstrucctions(instructions: List<Instruction>, query: String): Boolean {
        instructions.forEach {
            if (it.display_text.contains(query, ignoreCase = true))
                return true
        }
        return false
    }
}
