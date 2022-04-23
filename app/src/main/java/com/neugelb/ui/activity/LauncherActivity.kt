package com.neugelb.ui.activity

import androidx.lifecycle.lifecycleScope
import com.neugelb.R
import com.neugelb.base.BaseActivity
import com.neugelb.databinding.ActivitySplashBinding
import com.neugelb.viewmodel.activity.LauncherViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LauncherActivity : BaseActivity<LauncherViewModel, ActivitySplashBinding>() {


    override fun setupView() {
        super.setupView()
        lifecycleScope.launch {
            delay(2000)
            viewModel.checkUserFlow.collect {
                if (it) startActivity<MainActivity>()
                else startActivity<LoginActivity>()
            }
        }

    }

    override fun getLayoutId(): Int = R.layout.activity_splash
    override fun getViewModel(): Class<LauncherViewModel> = LauncherViewModel::class.java
    override fun setViewModel() {
        binding.viewModel = viewModel
    }

}