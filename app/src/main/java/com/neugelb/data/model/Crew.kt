package com.neugelb.data.model

import com.neugelb.config.EXTRA_IMG_URL_W500
import java.io.Serializable


data class Crew(
    val credit_id: String? = null,
    val department: String? = null,
    val gender: Int? = null,
    val id: String? = null,
    val job: String? = null,
    val name: String? = null,
    val profile_path: String? = null
) : Serializable {

    fun getFullProfilePath() =
        if (profile_path.isNullOrBlank()) null else EXTRA_IMG_URL_W500 + profile_path

}