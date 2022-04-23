package com.neugelb.data.model

import java.io.Serializable

data class ProductionCompany(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
) : Serializable