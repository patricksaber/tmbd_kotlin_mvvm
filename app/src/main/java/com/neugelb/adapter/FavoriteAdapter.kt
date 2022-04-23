package com.neugelb.adapter


import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.neugelb.R
import com.neugelb.base.BaseListAdapter
import com.neugelb.binding.setSingleClick
import com.neugelb.databinding.FavoriteItemBinding
import com.neugelb.room.model.CachingMovie


class FavoriteAdapter(var itemClickListener: (Int) -> Unit = {}) :
    BaseListAdapter<CachingMovie, FavoriteItemBinding>(object :
        DiffUtil.ItemCallback<CachingMovie>() {
        override fun areItemsTheSame(oldItem: CachingMovie, newItem: CachingMovie): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CachingMovie, newItem: CachingMovie): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.favorite_item
    }

    override fun bindFirstTime(binding: FavoriteItemBinding) {
        binding.apply {
            root.setSingleClick {
                item?.let {
                    itemClickListener.invoke(it.movieId)
                }
            }
        }
    }

    override fun bindView(binding: FavoriteItemBinding, item: CachingMovie, position: Int) {
        super.bindView(binding, item, position)
        binding.textItemScore.text = item.rate.toString()
    }

}
