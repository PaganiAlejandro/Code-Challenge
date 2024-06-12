package com.alepagani.codechallengeyape.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alepagani.codechallengeyape.R
import com.alepagani.codechallengeyape.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        initUI()
    }

    private fun initUI() {
        Glide.with(requireContext())
            .load(args.imageUrl)
            .centerCrop()
            .into(binding.imgRecipes)

        binding.apply {
            txtRecipeName.setText(args.name)
            txtRecipeDescription.setText(args.description)
            btnLocation.setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentToMapFragment(
                    latitude = args.latitude,
                    longitude = args.longitude,
                    name = args.name
                )
                findNavController().navigate(action)
            }
        }
    }
}