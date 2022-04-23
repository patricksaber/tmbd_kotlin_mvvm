package com.neugelb.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neugelb.config.TYPE_LOAD_NO_CONNECTION
import com.neugelb.config.TYPE_NAVIGATE_NO_CONNECTION
import com.neugelb.interfaces.Connect
import com.neugelb.utils.Dialogs.openConnectionDialog
import com.neugelb.utils.Dialogs.retryConnectionDialog
import com.neugelb.utils.EventTwoParameter
import com.neugelb.utils.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<T : BaseViewModel, S : ViewDataBinding> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    protected lateinit var viewModel: T
    protected lateinit var binding: S


    protected abstract fun getLayoutId(): Int
    protected abstract fun getViewModel(): Class<T>
    protected abstract fun setViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.e("onCreateView ${getViewModel().name}")
        return initDataBinding(inflater, container)
    }

    open fun initDataBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)[getViewModel()]
        viewModel.setBundle(arguments, requireActivity().intent)
        setViewModel()
        setupObservables()
        setupView()
        return binding.root
    }

    open fun setupView() {}

    private fun setupObservables() {
        viewModel.openNoInternetDialogLiveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                checkInternetWithDialog(event)
            }
        }
    }

    private fun checkInternetWithDialog(it: EventTwoParameter<Int, Connect>) {
        when (it.peekContent()) { // Only proceed if the event has never been handled
            TYPE_LOAD_NO_CONNECTION -> openConnectionDialog(
                requireContext(),
                it.peekSecondContent()
            )
            TYPE_NAVIGATE_NO_CONNECTION -> retryConnectionDialog(
                requireContext(),
                it.peekSecondContent()
            )
        }
    }


    inline fun <reified T : Activity> Activity.startActivity() {
        requireActivity().startActivity(createIntent<T>())
    }

    inline fun <reified T : Activity> Context.createIntent() =
        Intent(this, T::class.java)


    fun isConnectedToTheInternet(type: Int, connect: Connect) = checkInternetWithDialog(EventTwoParameter(type, connect))

}