package com.neugelb.room.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neugelb.room.model.CachingModel
import com.neugelb.room.model.CachingMovie
import java.util.*


@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cashingModel: CachingModel)

    @Query("SELECT * from Caching WHERE modelId=:id And type=:idType")
    suspend fun findById(id: Long, idType: Int): CachingModel

    @Query("UPDATE caching SET modelValue=:value, lastUpdatedDate=:dateUpdated WHERE modelId =:id AND type=:type ")
    suspend fun updateCaching(value: String, dateUpdated: Date, id: Long, type: Int)

    @Query("DELETE  FROM caching")
    suspend fun clearDatabase()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(cachingMovie: CachingMovie)

    @Query("SELECT * from cachingMovie WHERE type=2 ")
    suspend fun getAllFavorite(): List<CachingMovie>

    @Query("DELETE  FROM cachingMovie")
    suspend fun clearFavorites()

    @Query("SELECT * from cachingMovie WHERE movieId=:id And type=2 ")
    suspend fun checkFavoriteById(id: Long): CachingMovie

    @Query("DELETE  FROM cachingMovie WHERE movieId=:id ")
    suspend fun deleteFavoriteByID(id: Long)
}