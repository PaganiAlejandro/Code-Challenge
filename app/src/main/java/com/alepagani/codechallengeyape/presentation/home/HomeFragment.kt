package com.alepagani.codechallengeyape.presentation.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.alepagani.codechallengeyape.R
import com.alepagani.codechallengeyape.core.ResultResource
import com.alepagani.codechallengeyape.data.model.RecipeResult
import com.alepagani.codechallengeyape.databinding.FragmentHomeBinding
import com.alepagani.codechallengeyape.presentation.home.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), RecipesAdapter.onRecipesClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var adapterRecipes: RecipesAdapter
    private lateinit var searchHandler: Handler
    private lateinit var searchRunnable: Runnable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        initUI()
    }

    private fun initUI() {
        adapterRecipes = RecipesAdapter(emptyList(),this@HomeFragment)
        binding.rvRecipes.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = adapterRecipes
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipesStateFlow.collect { result ->
                    when (result) {
                        is ResultResource.Failure -> failurePath(result.exception.message.toString())
                        is ResultResource.Loading -> loadingPath()
                        is ResultResource.Success -> loadData(result.data)
                    }
                }
            }
        }

        searchHandler = Handler(Looper.getMainLooper())
        searchRunnable = Runnable {
            val query = binding.searchEditText.text.toString().trim()
            viewModel.filterRecipes(query)
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchHandler.removeCallbacks(searchRunnable)
                searchHandler.postDelayed(searchRunnable, 500) // Wait 500ms before to filter
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }

    private fun loadingPath() {
        binding.progresssBar.visibility = View.VISIBLE
    }

    private fun loadData(data: List<RecipeResult>) {
        adapterRecipes.updateList(data)
        binding.progresssBar.visibility = View.GONE
    }

    private fun failurePath(message: String) {
        Log.e("Error", "message: ${message}")
        binding.progresssBar.visibility = View.GONE
    }

    override fun onRecipesClick(recipes: RecipeResult) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
            name = recipes.name,
            description = recipes.description,
            imageUrl = recipes.thumbnail_url,
            latitude = "40.68940391241841",  //This position should be in the api response
            longitude = "-74.04453258110205"
        )
        findNavController().navigate(action)
    }


}