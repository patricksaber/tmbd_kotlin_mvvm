package com.neugelb.data

import com.squareup.moshi.Json

data class PaginatedListResponse<Movie>(
    val page: Int? = 0,
    @Json(name = "total_results") val totalResults: Int? = 0,
    @Json(name = "total_pages") val totalPages: Int? = 0,
    val results: List<Movie>? = null
)
