package com.neugelb.viewmodel.fragment

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.neugelb.base.BaseViewModel
import com.neugelb.config.PREF_USERNAME
import com.neugelb.data.SessionManager
import com.neugelb.data.repository.Repository
import com.neugelb.room.database.CachingRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    repository: Repository,
    session: SessionManager,
    cachingRepository: CachingRepository
) : BaseViewModel(repository, session, cachingRepository) {
    var text = ObservableField(session.getPrefString(PREF_USERNAME))
    fun logout() {
        sessionManager.logout()
        viewModelScope.launch {
            clearDatabase()

        }
    }
}