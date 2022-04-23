package com.neugelb.navigator

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.neugelb.R
import com.neugelb.base.BaseActivity
import com.neugelb.base.BaseViewModel

/**
 * Created by Patrick on 22/03/2022.
 */

abstract class NavigationActivity<T : BaseViewModel, S : ViewDataBinding> : BaseActivity<T, S>() {
    private var fragmentContainer = 0
    private var homeReselectEnabled = true
    private var rootFragments = LinkedHashMap<Int, Fragment>()
    private val fragmentsStack: MutableList<Fragment> = ArrayList()
    fun init(fragments: LinkedHashMap<Int, Fragment>, fragmentContainer: Int) {
        rootFragments = fragments
        this.fragmentContainer = fragmentContainer
        loadFirstTab()
    }

    fun setHomeReselectEnabled(enable: Boolean) {
        homeReselectEnabled = enable
    }

    abstract fun tabChanged(id: Int)


    fun onNavigationItemSelected(item: Int): Boolean {
        if (!rootFragments.containsKey(item)) {
            return true
        }
        val selectedFragment = rootFragments[item]
        val selectedTag = selectedFragment!!.javaClass.name
        val fragment = supportFragmentManager.findFragmentByTag(selectedTag)
        if (fragment != null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            for (frag in fragmentsStack) {
                if (frag.isVisible)
                    fragmentTransaction.hide(frag)
            }
            fragmentTransaction
                .show(fragment)
                .addToBackStack(selectedTag)
                .commitAllowingStateLoss()
            modifyStack(fragment)
        } else {
            val fragmentWrapper = FragmentWrapper()
            fragmentWrapper.loadInnerFragment(selectedFragment)
            supportFragmentManager
                .beginTransaction()
                .hide(supportFragmentManager.findFragmentById(fragmentContainer)!!)
                .add(fragmentContainer, fragmentWrapper, selectedTag)
                .addToBackStack(selectedTag)
                .commitAllowingStateLoss()
            fragmentsStack.add(0, fragmentWrapper)
        }
        return true
    }

    private fun loadFirstTab() {
        val firstFragment = rootFragments.values.iterator().next()
        val fragmentWrapper = FragmentWrapper()
        fragmentWrapper.loadInnerFragment(firstFragment)
        supportFragmentManager
            .beginTransaction()
            .add(fragmentContainer, fragmentWrapper, firstFragment.javaClass.name)
            .addToBackStack(firstFragment.javaClass.name)
            .commit()
        fragmentsStack.add(0, fragmentWrapper)
    }

    @Synchronized
    private fun modifyStack(newFragment: Fragment) {
        for (i in fragmentsStack.indices) {
            if (fragmentsStack[i] === newFragment) {
                fragmentsStack.removeAt(i)
                fragmentsStack.add(0, newFragment)
                return
            }
        }
        fragmentsStack.add(0, newFragment)
    }

    private fun getItemIdByFragment(currentFragment: FragmentWrapper): Int {
        for ((key, value) in rootFragments) {
            if (value.javaClass.name == currentFragment.tag)
                return key
        }
        return 0
    }

    private fun getCorrectId(value: Fragment): Int {
        if (value.javaClass.name.contains("Home"))
            return R.id.navigation_home
        else if (value.javaClass.name.contains("Setting"))
            return R.id.navigation_settings
        else
            return R.id.navigation_favorite
    }


    override fun onBackPressed() {
        if (fragmentsStack.size < 1) {
            finish()
            return
        }
        val currentFragment = fragmentsStack[0] as FragmentWrapper
        if (currentFragment.popIfHasChild(this)) {

            return
        }
        if (fragmentsStack.size == 1) {
            val fragment = rootFragments.values.iterator().next()
            val fragTag = fragment.javaClass.name
            if (fragmentsStack[0].tag == fragTag) {
                finish()
                return
            }
            val fragmentWrapper =
                supportFragmentManager.findFragmentByTag(fragTag) as FragmentWrapper?
            if (fragmentWrapper == null) {
                finish()
                return
            }
            supportFragmentManager
                .beginTransaction()
                .hide(currentFragment)
                .show(fragmentWrapper)
                .addToBackStack(fragTag)
                .commit()
            fragmentsStack.removeAt(0)
            fragmentsStack.add(0, fragmentWrapper)
            tabChanged(getItemIdByFragment(fragmentWrapper))
            return
        }
        fragmentsStack.removeAt(0)
        val newFragment = fragmentsStack[0]
        supportFragmentManager
            .beginTransaction()
            .hide(currentFragment)
            .show(newFragment)
            .addToBackStack(newFragment.javaClass.name)
            .commit()
        tabChanged(getItemIdByFragment((newFragment as FragmentWrapper)))
    }

}