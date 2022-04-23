package com.neugelb.room.database

import com.neugelb.room.model.CachingModel
import com.neugelb.room.model.CachingMovie
import java.util.*

interface CachingRepository {
    suspend fun clearDatabase()
    suspend fun findById(id: Long, idType: Int): CachingModel?
    suspend fun insert(product: CachingModel)
    suspend fun insertFavorite(cachingMovie: CachingMovie)
    suspend fun clearFavorites()
    suspend fun getFavorites(): List<CachingMovie>
    suspend fun updateCaching(value: String, dateUpdated: Date, id: Long, type: Int)
    suspend fun checkFavoriteById(i: Long): CachingMovie?
    suspend fun deleteFavoriteById(i: Long)
}