package com.alepagani.codechallengeyape.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alepagani.codechallengeyape.R
import com.alepagani.codechallengeyape.core.ResultResource
import com.alepagani.codechallengeyape.data.model.RecipeResult
import com.alepagani.codechallengeyape.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        initUI()
    }

    private fun initUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipesList.collect { result ->
                    when (result) {
                        is ResultResource.Failure -> failurePath(result.exception.message.toString())
                        is ResultResource.Loading -> loadingPath()
                        is ResultResource.Success -> loadData(result.data)
                    }
                }
            }
        }
    }

    private fun loadingPath() {
        binding.progresssBar.visibility = View.VISIBLE
    }

    private fun loadData(data: List<RecipeResult>) {
        Log.d("ALE", "${data.get(0).name}")
        binding.progresssBar.visibility = View.GONE
    }

    private fun failurePath(message: String) {
        Log.e("ERROR", "Error: ${message}")
        binding.progresssBar.visibility = View.GONE
    }
}