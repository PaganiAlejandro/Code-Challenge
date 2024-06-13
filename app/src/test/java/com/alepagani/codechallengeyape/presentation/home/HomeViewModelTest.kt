package com.alepagani.codechallengeyape.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import com.alepagani.codechallengeyape.core.ResultResource
import com.alepagani.codechallengeyape.data.FakeRepositoryImplTest
import com.alepagani.codechallengeyape.data.model.RecipeResult
import com.alepagani.codechallengeyape.domain.getRecipesUseCases
import com.alepagani.codechallengeyape.testRule.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `recipesStateFlow emits a list of recipes after ViewModel is initialized`() = runTest {
        val fakeRepository = FakeRepositoryImplTest()
        val useCases = getRecipesUseCases(fakeRepository)
        val viewModel = HomeViewModel(useCases)

        viewModel.recipesStateFlow.test {
            val emission = awaitItem()
            assert(emission is ResultResource.Success<*>)
            val successEmission = emission as ResultResource.Success<List<RecipeResult>>
            val recipes = successEmission.data
            assertNotNull(recipes)
            assertEquals("Pasta", recipes[0].name)
            assertEquals("Onion", recipes[1].name)
        }
    }

    @Test
    fun `filterRecipes filters recipes by query`() = runTest {
        val fakeRepository = FakeRepositoryImplTest()
        val useCases = getRecipesUseCases(fakeRepository)
        val viewModel = HomeViewModel(useCases)

        viewModel.recipesStateFlow.test {
            awaitItem()

            viewModel.filterRecipes("Onion")

            val emission = awaitItem()
            assert(emission is ResultResource.Success<*>)
            val successEmission = emission as ResultResource.Success<List<RecipeResult>>
            val filteredRecipes = successEmission.data
            assertEquals(1, filteredRecipes.size)
            assertEquals("Onion", filteredRecipes[0].name)
        }
    }
}