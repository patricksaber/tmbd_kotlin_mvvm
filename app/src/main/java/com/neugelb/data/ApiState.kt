package com.neugelb.data

class ApiState<out T>(val status: Status, val data: T? = null, val message: String? = null) {

    companion object {
        fun <T> success(data: T?): ApiState<T> {
            return ApiState(status = Status.SUCCESS, data)
        }

        fun <T> error(msg: String): ApiState<T> {
            return ApiState(status = Status.ERROR, message = msg)
        }

        fun <T> loading(): ApiState<T> {
            return ApiState(Status.LOADING)
        }
    }
}

// An enum to store the
// current state of api call
sealed class Status {
    object SUCCESS : Status()

    object ERROR : Status()

    object LOADING : Status()
}