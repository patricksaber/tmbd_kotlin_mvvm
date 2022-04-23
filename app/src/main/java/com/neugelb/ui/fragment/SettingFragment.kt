package com.neugelb.ui.fragment

import com.neugelb.R
import com.neugelb.base.BaseFragment
import com.neugelb.databinding.SettingFragmentBinding
import com.neugelb.ui.activity.LauncherActivity
import com.neugelb.viewmodel.fragment.SettingsViewModel

class SettingFragment : BaseFragment<SettingsViewModel, SettingFragmentBinding>() {

    override fun getLayoutId(): Int = R.layout.setting_fragment
    override fun getViewModel(): Class<SettingsViewModel> = SettingsViewModel::class.java
    override fun setViewModel() {
        binding.viewModel = viewModel
    }

    override fun setupView() {
        super.setupView()
        binding.logout.setOnClickListener{
            viewModel.logout()
            requireActivity().startActivity<LauncherActivity>()

        }
    }

}

