package com.neugelb.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.neugelb.R
import com.neugelb.base.BaseActivity
import com.neugelb.data.UiState
import com.neugelb.data.req.LoginReq
import com.neugelb.databinding.ActivityLoginBinding
import com.neugelb.utils.ConnectionDetector.haveInternet
import com.neugelb.viewmodel.activity.LoginViewModel
import kotlinx.coroutines.flow.collect

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {


    override fun getLayoutId(): Int = R.layout.activity_login
    override fun getViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun setViewModel() {
        binding.viewModel = viewModel
    }

    override fun setupView() {
        super.setupView()

        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                if (it.isLoading)
                    binding.loading.visibility = View.VISIBLE
                else binding.loading.visibility = View.GONE

                when (it.error) {
                    UiState.Error.NetworkError -> {
                        showSnackError(getString(R.string.network_error))
                    }
                    UiState.Error.ServerError -> {
                        showSnackError(getString(R.string.server_error))
                    }
                    UiState.Error.Invalid -> {
                        showSnackError(getString(R.string.invalid_error))
                    }
                    else -> Unit
                }
                if (it.items != null) {
                    checkRememberMe(it.items.name)
                    startActivity<MainActivity>()
                }
            }
        }


        with(binding) {

            username.afterTextChanged {
                if (username.text.toString().isNotEmpty() && it.isNotEmpty())
                    login.isEnabled = true
            }

            password.apply {
                afterTextChanged {
                    if (username.text.toString().isNotEmpty() && it.isNotEmpty())
                        login.isEnabled = true
                }

                setOnEditorActionListener { _, actionId, _ ->
                    when (actionId) {
                        EditorInfo.IME_ACTION_DONE -> login()
                    }
                    false
                }

                login.setOnClickListener {
                    login()
                }
            }
        }
    }

    private fun checkRememberMe(name: String) =
        viewModel.saveUser(binding.rememberMe.isChecked, name)


    private fun login() {
        if (haveInternet())
            viewModel.fakeLoginChecker(with(binding) {
                LoginReq(
                    username.text.toString(),
                    password.text.toString()
                )
            })
        else showSnackError(getString(R.string.connection_error))
    }

    override fun onBackPressed() = finishAffinity()

}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}