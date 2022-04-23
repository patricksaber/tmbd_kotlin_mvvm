package com.neugelb.data.repository

import com.neugelb.data.*
import com.neugelb.data.model.Person
import com.neugelb.data.model.User
import com.neugelb.data.req.LoginReq
import com.neugelb.data.resp.GetCastAndCrewResponse
import com.neugelb.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.random.Random

class Repository(private val api: Api) {

    suspend fun getDetails(
        movieId: Long
    ): Flow<ApiState<Movie>> {
        return flow {
            emit(ApiState.success(api.getDetails(movieId = movieId)))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPopularTvShows(
        year: Int,
        page: Int
    ): Flow<ApiState<PaginatedListResponse<Movie>>> {
        return flow {
            emit(ApiState.success(api.getPopularTvShows(year = year, page = page)))
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getCastAndCrew(movieId: Long): Flow<ApiState<GetCastAndCrewResponse>> {
        return flow {
            emit(ApiState.success(api.getMovieCredits(movieId)))
        }.flowOn(Dispatchers.IO)
    }


    suspend fun checkUser(req: LoginReq): Resources<Person> {
        return try {
            delay(3000)
            if (Random.nextInt(4) == 0) {
                throw Exception()
            } else {
                for (user in getUserData()) {
                    if (user.username == req.username && user.password == req.password) {
                        return Resources.Success(
                            Person(name = user.username)
                        )
                    }
                }
                Resources.Error(UiState.Error.Invalid)
            }
        } catch (e: Exception) {
            Resources.Error(UiState.Error.NetworkError)
        }
    }

    private fun getUserData(): List<User> {
        return listOf(
            User(username = "admin", password = "admin"),
            User(username = "alex", password = "admin"),
            User(username = "robert", password = "123"),
            User(username = "patrick", password = "happy")
        )
    }
}
