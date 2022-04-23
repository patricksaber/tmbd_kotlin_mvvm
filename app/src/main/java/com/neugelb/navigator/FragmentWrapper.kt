package com.neugelb.navigator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neugelb.R

/**
 * Created by Patrick on 22/03/2022.
 */

class FragmentWrapper : Fragment() {
    var childFragment: Fragment? = null
    private var isInflated = false

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView= inflater.inflate(R.layout.fragment_wrapper, null)
        if (isInflated) {
            return rootView
        }
        if (childFragment == null) {
            return rootView
        }
        val fragmentTransaction = childFragmentManager.beginTransaction()
        val fragmentById = childFragmentManager.findFragmentById(R.id.inner_container)
        if (fragmentById != null) {
            fragmentTransaction.hide(fragmentById)
        }
        fragmentTransaction
            .add(R.id.inner_container, childFragment!!, childFragment!!.javaClass.name)
            .addToBackStack(childFragment!!.javaClass.name)
            .commit()
        isInflated = true
        return rootView
    }

    fun loadInnerFragment(fragment: Fragment?) {
        childFragment = fragment
    }

    fun popIfHasChild(navigationActivity: NavigationActivity<*, *>): Boolean {
        if (childFragmentManager.backStackEntryCount > 1) {
            try {
                childFragmentManager.popBackStackImmediate()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
            navigationActivity.run {
                childFragmentManager
                    .beginTransaction()
                    .show(childFragmentManager.findFragmentById(R.id.inner_container)!!)
                    .commitAllowingStateLoss()
            }
            return true
        }
        return false
    }

    override fun onPause() {
        super.onPause()
        onFragmentHidden()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            onFragmentHidden()
        } else {
            onFragmentShowen()
        }
    }

    private fun onFragmentHidden() {
//        if (childFragment is ZNavigationFragment) {
//            try {
//                val childFrag = childFragment as ZNavigationFragment
//                childFrag.onHiddenChanged(true)
//            } catch (e: Exception) {
//                Timber.d("Moved fragment but cant notify about it")
//            }
//        }
    }

    private fun onFragmentShowen() {
//        if (childFragment is ZNavigationFragment) {
//            try {
//                val childFrag = childFragment as ZNavigationFragment
//                childFrag.onHiddenChanged(false)
//            } catch (e: Exception) {
//                Timber.d("Moved fragment but cant notify about it")
//            }
//        }
    }
}