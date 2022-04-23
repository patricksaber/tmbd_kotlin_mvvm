package com.neugelb.data.model

import java.io.Serializable

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
) : Serializable