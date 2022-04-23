package com.neugelb.room.database

import com.neugelb.room.model.CachingModel
import com.neugelb.room.model.CachingMovie
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*
import javax.inject.Inject


@DelicateCoroutinesApi
class DataSource @Inject constructor(private val dao: DataDao) : CachingRepository {

    override suspend fun findById(id: Long, idType: Int) = dao.findById(id, idType)


    override suspend fun insert(product: CachingModel) = dao.insert(cashingModel = product)

    override suspend fun insertFavorite(cachingMovie: CachingMovie) =
        dao.insertFavorite(cachingMovie = cachingMovie)

    override suspend fun clearFavorites() = dao.clearFavorites()

    override suspend fun getFavorites(): List<CachingMovie> = dao.getAllFavorite()

    override suspend fun updateCaching(value: String, dateUpdated: Date, id: Long, type: Int) =
        dao.updateCaching(
            value,
            dateUpdated,
            id,
            type
        )

    override suspend fun checkFavoriteById(i: Long) = dao.checkFavoriteById(i)
    override suspend fun deleteFavoriteById(i: Long) = dao.deleteFavoriteByID(i)
    override suspend fun clearDatabase() = dao.clearDatabase()

}