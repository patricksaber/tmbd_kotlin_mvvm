package com.neugelb.data

import com.neugelb.data.resp.GetCastAndCrewResponse
import com.neugelb.data.model.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("discover/movie")
    suspend fun getPopularTvShows(
        @Query("primary_release_year") year: Int, @Query("page") page: Int
    ): PaginatedListResponse<Movie>

    @GET("movie/{movie_id}")
    suspend fun getDetails(
        @Path("movie_id") movieId: Long
    ): Movie


    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") movieId: Long): GetCastAndCrewResponse
}
