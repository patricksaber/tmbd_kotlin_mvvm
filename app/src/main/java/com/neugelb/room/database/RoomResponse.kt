package com.neugelb.room.database

sealed class RoomResponse<T>(val data: T? = null) {
    class Success<T>(data: T) : RoomResponse<T>(data)
    class Error<T> : RoomResponse<T>()
}
