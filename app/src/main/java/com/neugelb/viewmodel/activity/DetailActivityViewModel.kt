package com.neugelb.viewmodel.activity

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.neugelb.base.BaseViewModel
import com.neugelb.config.DATABASE_FAVORITE
import com.neugelb.config.INTENT_ID
import com.neugelb.data.SessionManager
import com.neugelb.data.resp.GetCastAndCrewResponse
import com.neugelb.data.model.Movie
import com.neugelb.data.ApiState
import com.neugelb.data.Status
import com.neugelb.data.repository.Repository
import com.neugelb.room.model.CachingMovie
import com.neugelb.room.database.CachingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailActivityViewModel @Inject constructor(
    repository: Repository,
    sessionManager: SessionManager,
    cachingRepository: CachingRepository
) : BaseViewModel(repository, sessionManager, cachingRepository) {
    private val _apiState = MutableStateFlow(
        ApiState(Status.LOADING, GetCastAndCrewResponse())
    )
    val apiState = _apiState.asStateFlow()

    var id = 0L
    val movie = ObservableField<Movie>()
    val isFavorite = ObservableField(false)

    init {
        viewModelScope.launch {
            delay(500)
            id = bundle!!.getLong(INTENT_ID)
            getMovieDetails()
            getCastAndCrew()
        }
    }

    private suspend fun getCastAndCrew() {
        _apiState.value = ApiState.loading()
        repository.getCastAndCrew(id)
            .catch {
                _apiState.value = ApiState.error(it.message.toString())
            }
            .collect {
                _apiState.value = ApiState.success(it.data)
            }
    }

    private suspend fun getMovieDetails() {
        repository.getDetails(id)
            .catch {
                    _apiState.value = ApiState.error(it.message.toString())
            }
            .collect {
                movie.set(it.data)
                isFavorite.set(checkIfFavorite(id))
            }
    }

    fun favoriteMovie() {
        viewModelScope.launch {
            if (isFavorite.get() == false) {
                cachingRepository.insertFavorite(getCachingModel(movie.get()!!))
                isFavorite.set(true)
            } else {
                cachingRepository.deleteFavoriteById(id)
                isFavorite.set(false)
            }
        }
    }

    private suspend fun checkIfFavorite(id: Long): Boolean {
        return cachingRepository.checkFavoriteById(id) != null
    }

    private fun getCachingModel(movie: Movie): CachingMovie =
        CachingMovie(
            0,
            id.toInt(),
            DATABASE_FAVORITE,
            movie.title,
            movie.overview,
            movie.getFullImagePath() ?: "",
            movie.vote_average
        )

}
