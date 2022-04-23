package com.neugelb.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.neugelb.config.TYPE_LOAD_NO_CONNECTION
import com.neugelb.config.TYPE_NAVIGATE_NO_CONNECTION
import com.neugelb.interfaces.Connect
import com.neugelb.utils.Dialogs
import com.neugelb.utils.EventTwoParameter
import com.neugelb.utils.SnackBar
import com.neugelb.utils.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<T : BaseViewModel, S : ViewDataBinding> : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: T
    lateinit var binding: S

    protected abstract fun getLayoutId(): Int
    protected abstract fun getViewModel(): Class<T>
    protected abstract fun setViewModel()

    protected val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        AndroidInjection.inject(this)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)[getViewModel()]
        viewModel.setBundle(intent.extras, intent)
        setViewModel()
        setupObservables()
        setupView()
    }

    open fun setupView() {}

    inline fun <reified T : Activity> Context.createIntent() =
        Intent(this, T::class.java)

    inline fun <reified T : Activity> Activity.startActivity() {
        startActivity(createIntent<T>(), getBundleExtra())
        finish()
    }

    fun getBundleExtra(): Bundle {
        return bundle
    }


    fun showSnackError(text: String) {
        hideKeyboard()
        SnackBar.showError(activity = this, text = text)
    }

    private fun checkInternetWithDialog(it: EventTwoParameter<Int, Connect>) {
        when (it.peekContent()) { // Only proceed if the event has never been handled
            TYPE_LOAD_NO_CONNECTION -> Dialogs.openConnectionDialog(
                this,
                it.peekSecondContent()
            )
            TYPE_NAVIGATE_NO_CONNECTION -> Dialogs.retryConnectionDialog(
                this,
                it.peekSecondContent()
            )
        }
    }

    private fun setupObservables() {
        viewModel.openNoInternetDialogLiveData.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                checkInternetWithDialog(event)
            }
        }
    }



    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isConnectedToTheInternet(type: Int, connect: Connect) = checkInternetWithDialog(EventTwoParameter(type, connect))
}