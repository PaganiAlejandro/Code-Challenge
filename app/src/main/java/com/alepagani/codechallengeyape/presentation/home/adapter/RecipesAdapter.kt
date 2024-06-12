package com.alepagani.codechallengeyape.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alepagani.codechallengeyape.core.BaseViewHolder
import com.alepagani.codechallengeyape.data.model.RecipeResult
import com.alepagani.codechallengeyape.databinding.RecipeItemBinding
import com.bumptech.glide.Glide

class RecipesAdapter(
    private val recipesList: List<RecipeResult>,
    private val itemClickList: onRecipesClickListener
): RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var filteredRecipes: List<RecipeResult> = recipesList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = RecipesViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickList.onRecipesClick(filteredRecipes[position])
        }
        return holder
    }

    override fun getItemCount() = filteredRecipes.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is RecipesViewHolder -> holder.bind(filteredRecipes[position])
        }
    }

    fun updateList(newList: List<RecipeResult>) {
        filteredRecipes = newList
        notifyDataSetChanged()
    }

    interface onRecipesClickListener {
        fun onRecipesClick(recipes: RecipeResult)
    }

    private inner class RecipesViewHolder(
        val binding: RecipeItemBinding,
        val context: Context
    ) : BaseViewHolder<RecipeResult>(binding.root) {
        override fun bind(item: RecipeResult) {
            Glide.with(context)
                .load(item.thumbnail_url)
                .centerCrop()
                .into(binding.imgRecipe)

            binding.txtRecipeName.setText(item.name)
        }
    }
}