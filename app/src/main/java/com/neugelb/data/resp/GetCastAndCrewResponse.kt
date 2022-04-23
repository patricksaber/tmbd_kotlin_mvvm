package com.neugelb.data.resp

import com.neugelb.data.model.Cast
import com.neugelb.data.model.Crew
import com.squareup.moshi.Json
import java.io.Serializable

class GetCastAndCrewResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "cast") val cast: List<Cast>? = null,
    @Json(name = "crew") val crew: List<Crew>? = null
) : Serializable