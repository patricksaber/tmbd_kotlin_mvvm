package com.neugelb.base

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neugelb.data.SessionManager
import com.neugelb.data.repository.Repository
import com.neugelb.interfaces.Connect
import com.neugelb.room.database.CachingRepository
import com.neugelb.room.database.RoomResponse
import com.neugelb.room.model.CachingModel
import com.neugelb.utils.EventTwoParameter
import java.util.*

abstract class BaseViewModel(
    val repository: Repository,
    val sessionManager: SessionManager,
    val cachingRepository: CachingRepository
) : ViewModel() {
    protected var bundle: Bundle? = null
    protected var intent: Intent? = null

    private val _openNoInternetDialogLiveData = MutableLiveData<EventTwoParameter<Int,Connect>>()

    val openNoInternetDialogLiveData: LiveData<EventTwoParameter<Int,Connect>>
        get() = _openNoInternetDialogLiveData


    fun setBundle(arguments: Bundle?, intent: Intent) {
        bundle = arguments ?: Bundle()
        this.intent = intent
    }

    protected fun isConnectedToTheInternet(type: Int,connect: Connect) {
        _openNoInternetDialogLiveData.value = EventTwoParameter(type,connect)
    }

    suspend inline fun <reified T> getCache(id: Long, type: Int): RoomResponse<T> {
        return when (val result = cachingRepository.findById(id, type)) {
            null -> RoomResponse.Error()
            else -> RoomResponse.Success(Gson().fromJson(result.modelValue, genericType<T>()))
        }
    }

    inline fun <reified T> genericType() = object : TypeToken<T>() {}.type!!


    open suspend fun clearDatabase() {
        cachingRepository.clearDatabase()
        cachingRepository.clearFavorites()
    }

    suspend inline fun <reified T> insertData(id: Long, value: T, type: Int) {
        when (getCache<T>(id, type)) {
            is RoomResponse.Success -> {
                cachingRepository.updateCaching(Gson().toJson(value), Date(), id, type)
            }
            is RoomResponse.Error -> {
                val cachingModel = CachingModel(0, id.toInt(), Gson().toJson(value), Date(), type)
                cachingRepository.insert(cachingModel)
            }
        }
    }

}