package com.neugelb.adapter

import androidx.recyclerview.widget.DiffUtil
import com.neugelb.R
import com.neugelb.base.BaseListAdapter
import com.neugelb.binding.setSingleClick
import com.neugelb.data.model.Movie
import com.neugelb.databinding.MovieItemBinding


class MovieListAdapter(var itemClickListener: (Movie) -> Unit = {}) :
    BaseListAdapter<Movie, MovieItemBinding>(object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }) {

    fun updateData(results: List<Movie>?) {
        val list = ArrayList<Movie>()
        list.addAll(currentList)
        list.addAll(results!!)
        submitList(list)
    }


    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.movie_item
    }

    override fun bindFirstTime(binding: MovieItemBinding) {
        binding.apply {
            root.setSingleClick {
                item?.let {
                    itemClickListener.invoke(it)
                }
            }
        }
    }
}
