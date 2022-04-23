package com.neugelb.data.model

data class Person(
    val name: String,
    val gender: Gender? = null
) {
    sealed class Gender {
        object Male : Gender()
        object Female : Gender()
    }
}