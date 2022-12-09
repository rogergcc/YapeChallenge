package com.rogergcc.yapechallenge.presentation.recipes_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rogergcc.yapechallenge.databinding.RecipeItemBinding
import com.rogergcc.yapechallenge.domain.model.Recipe
import com.rogergcc.yapechallenge.utils.loadImage

class RecipeListAdapter : ListAdapter<Recipe, RecipeListAdapter.ViewHolder>(DiffCall()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecipeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    // onItemClickListener
    private var onItemClickListener: ((Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(
        private val binding: RecipeItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.apply {
                tvRecipeName.text = recipe.name
                ivRecipeImage.loadImage(imgUrl = recipe.image)


                // onItemClickListener
                root.setOnClickListener {
                    onItemClickListener?.let { it(recipe.id) }
                }
            }
        }
    }

    // Diff Callback
    class DiffCall : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
}





























