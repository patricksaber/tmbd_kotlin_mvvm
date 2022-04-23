package com.neugelb.viewmodel.activity

import androidx.lifecycle.viewModelScope
import com.neugelb.base.BaseViewModel
import com.neugelb.config.PREF_REMEMBER
import com.neugelb.config.PREF_USERNAME
import com.neugelb.data.Resources
import com.neugelb.data.SessionManager
import com.neugelb.data.UiState
import com.neugelb.data.req.LoginReq
import com.neugelb.data.repository.Repository
import com.neugelb.room.database.CachingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    repository: Repository,
    session: SessionManager,
    cachingRepository: CachingRepository
) : BaseViewModel(repository, session, cachingRepository) {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun fakeLoginChecker(req: LoginReq) {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            when (val data = repository.checkUser(req)) {
                is Resources.Success -> {
                    _uiState.value = UiState(isLoading = false)
                    _uiState.value = UiState(items = data.data)
                }
                is Resources.Error -> {
                    _uiState.value = UiState(isLoading = false)
                    _uiState.value = UiState(error = data.error)
                }
            }
        }
    }

    fun saveUser(checked: Boolean, name: String) {
        sessionManager.savePreString(name, PREF_USERNAME)
        sessionManager.savePreBool(value = checked, key = PREF_REMEMBER)
    }

}