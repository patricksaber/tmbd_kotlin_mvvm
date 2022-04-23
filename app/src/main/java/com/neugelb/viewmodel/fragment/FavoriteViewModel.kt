package com.neugelb.viewmodel.fragment

import android.view.View
import androidx.databinding.ObservableField
import com.neugelb.base.BaseViewModel
import com.neugelb.data.SessionManager
import com.neugelb.data.repository.Repository
import com.neugelb.room.database.CachingRepository
import com.neugelb.room.model.CachingMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    repository: Repository,
    session: SessionManager,
    cachingRepository: CachingRepository
) : BaseViewModel(repository, session, cachingRepository) {

    var noDataFound = ObservableField(View.INVISIBLE)

    private var oldSize = 0

    val dataFlow: Flow<List<CachingMovie>> = flow {
        val data = cachingRepository.getFavorites()
        if (data.isNullOrEmpty())
            noDataFound.set(View.VISIBLE)
        if (oldSize != data.size) {
            oldSize = data.size
            emit(data)
        }
    }

    fun clearSize() = 0.also { oldSize = it }


}