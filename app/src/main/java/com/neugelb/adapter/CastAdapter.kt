package com.neugelb.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.neugelb.R
import com.neugelb.base.BaseListAdapter
import com.neugelb.binding.setSingleClick
import com.neugelb.data.model.Cast
import com.neugelb.databinding.ItemCastBinding

class CastAdapter(val itemClickListener: ((ImageView, Cast) -> Unit)? = null) :
    BaseListAdapter<Cast, ItemCastBinding>(object : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.item_cast
    }

    override fun bindFirstTime(binding: ItemCastBinding) {
        binding.apply {
            root.setSingleClick {
                item?.let {
                    itemClickListener?.invoke(imageCast, it)
                }
            }
        }
    }

}