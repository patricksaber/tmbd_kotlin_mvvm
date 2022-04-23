package com.neugelb.ui.activity

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.neugelb.R
import com.neugelb.adapter.CastAdapter
import com.neugelb.base.BaseActivity
import com.neugelb.data.Status
import com.neugelb.databinding.ActivityDetailBinding
import com.neugelb.viewmodel.activity.DetailActivityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DetailActivity : BaseActivity<DetailActivityViewModel, ActivityDetailBinding>() {
    private val castAdapter = CastAdapter(itemClickListener = { _, cast ->
        cast.getFullProfilePath()?.let {
            // we can open the image in full view or open the cast details
        }
    })

    override fun getLayoutId(): Int = R.layout.activity_detail
    override fun getViewModel(): Class<DetailActivityViewModel> =
        DetailActivityViewModel::class.java

    override fun setViewModel() {
        binding.viewModel = viewModel
    }

    override fun setupView() {
        super.setupView()
        with(binding) {
            buttonFavorite.setOnClickListener {
                viewModel!!.favoriteMovie()
            }
            imageBack.setOnClickListener{
                onBackPressed()
            }
        }
        binding.recyclerCast.adapter = castAdapter
        lifecycleScope.launch {
            viewModel.apiState.collect {
                when (it.status) {
                    is Status.SUCCESS -> {
                        showLoading(View.GONE)
                        castAdapter.submitList(it.data?.cast)
                    }
                    is Status.ERROR ->{
                        showLoading(View.GONE)
                        showSnackError(it.message.toString())
                        delay(500)
                        finish()
                    }
                    Status.LOADING -> showLoading(View.VISIBLE)
                }
            }
        }

    }

    private fun showLoading(show:Int) {
        binding.loading.visibility = show
    }

}
