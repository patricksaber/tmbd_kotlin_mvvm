package com.neugelb.ui.fragment

import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neugelb.R
import com.neugelb.adapter.FavoriteAdapter
import com.neugelb.base.BaseFragment
import com.neugelb.config.INTENT_ID
import com.neugelb.databinding.FavoriteFragmentBinding
import com.neugelb.ui.activity.DetailActivity
import com.neugelb.viewmodel.fragment.FavoriteViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment : BaseFragment<FavoriteViewModel, FavoriteFragmentBinding>() {

    @Inject
    lateinit var mAdapter: FavoriteAdapter

    override fun getLayoutId(): Int = R.layout.favorite_fragment
    override fun getViewModel(): Class<FavoriteViewModel> = FavoriteViewModel::class.java
    override fun setViewModel() {
        binding.viewModel = viewModel
    }

    override fun setupView() {
        with(binding) {
            rvFavorites.adapter = mAdapter.apply {
                itemClickListener = { onMovieItemClick(it) }
            }
            GridLayoutManager(
                requireContext(),
                3,
                RecyclerView.VERTICAL,
                false
            ).apply {
                rvFavorites.layoutManager = this
            }

        }
    }

    private fun onMovieItemClick(id: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(INTENT_ID, id.toLong())
        startActivity(intent)
    }

    override fun onResume() {
        lifecycleScope.launch {
            viewModel.dataFlow.collect {
                mAdapter.submitList(it)
                binding.errorTv.visibility =
                    if (it.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
            }
        }
        super.onResume()
    }

    override fun onDetach() {
        viewModel.clearSize()
        super.onDetach()
    }
}

