package com.neugelb.ui.activity

import androidx.fragment.app.Fragment
import com.neugelb.R
import com.neugelb.adapter.AutoCompleteAdapter
import com.neugelb.adapter.MovieListAdapter
import com.neugelb.databinding.ActivityMainBinding
import com.neugelb.navigator.NavigationActivity
import com.neugelb.ui.fragment.FavoriteFragment
import com.neugelb.ui.fragment.HomeFragment
import com.neugelb.ui.fragment.SettingFragment
import com.neugelb.viewmodel.activity.MainActivityViewModel
import javax.inject.Inject


class MainActivity : NavigationActivity<MainActivityViewModel, ActivityMainBinding>() {

    @Inject
    lateinit var mAdapter: MovieListAdapter

    @Inject
    lateinit var autoCompleteAdapter: AutoCompleteAdapter

    override fun getLayoutId(): Int = R.layout.activity_main
    override fun getViewModel(): Class<MainActivityViewModel> = MainActivityViewModel::class.java
    override fun setViewModel() {
        binding.viewModel = viewModel
    }

    override fun setupView() {
        val rootFragments = LinkedHashMap<Int, Fragment>()
        rootFragments[R.id.navigation_home] = HomeFragment()
        rootFragments[R.id.navigation_favorite] = FavoriteFragment()
        rootFragments[R.id.navigation_settings] = SettingFragment()
        init(rootFragments, R.id.fragment)
        binding.navView.setOnItemSelectedListener {
            onNavigationItemSelected(it.itemId)
        }
    }

    override fun onBackPressed() {
        if (binding.navView.selectedItemId == R.id.navigation_home)
            finishAffinity()
        else super.onBackPressed()
    }

    override fun tabChanged(id: Int) {
        binding.navView.selectedItemId = id
    }
}


