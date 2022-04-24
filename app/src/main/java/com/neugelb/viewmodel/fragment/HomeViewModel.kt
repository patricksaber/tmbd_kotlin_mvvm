package com.neugelb.viewmodel.fragment

import androidx.lifecycle.viewModelScope
import com.neugelb.base.BaseViewModel
import com.neugelb.config.DATABASE_LATEST
import com.neugelb.config.MOVIE_YEAR
import com.neugelb.config.TYPE_LOAD_NO_CONNECTION
import com.neugelb.data.ApiState
import com.neugelb.data.PaginatedListResponse
import com.neugelb.data.SessionManager
import com.neugelb.data.Status
import com.neugelb.data.model.Movie
import com.neugelb.data.repository.Repository
import com.neugelb.interfaces.Connect
import com.neugelb.room.database.CachingRepository
import com.neugelb.room.database.RoomResponse
import com.neugelb.utils.SingleLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    repository: Repository,
    session: SessionManager,
    cachingRepository: CachingRepository
) : BaseViewModel(repository, session, cachingRepository) {
    private var year = MOVIE_YEAR
    private var page = 1


    private val _apiState =
        MutableStateFlow(ApiState(Status.LOADING, PaginatedListResponse<Movie>()))
    val apiState = _apiState.asStateFlow()
    val offlineLiveData = SingleLiveData<RoomResponse<List<Movie>>>()

    private fun getDataOfflineMode() = runBlocking {
        offlineLiveData.value = getCache(page.toLong(), DATABASE_LATEST)
    }

    private fun getMovies() {
        viewModelScope.launch {
            _apiState.value = ApiState.loading()
            repository.getPopularTvShows(year = year, page = page)
                .catch {
                    _apiState.value = ApiState.error(it.message.toString())
                }
                .collect {
                    page++
                    _apiState.value = ApiState.success(it.data)
                    insertData(it.data?.page!!.toLong(), it.data.results, DATABASE_LATEST)
                }
        }
    }


    fun loadMore() = loadData()

    init {
        loadData()
    }

    private fun loadData() {
        isConnectedToTheInternet(TYPE_LOAD_NO_CONNECTION, object : Connect() {
            override fun isConnected() = getMovies()
            override fun retry() = loadData()
            override fun getDataOffline() = getDataOfflineMode()
        })
    }

    fun updatePage() = page++

}