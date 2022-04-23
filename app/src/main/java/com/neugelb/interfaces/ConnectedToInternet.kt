package com.neugelb.interfaces

interface ConnectedToInternet {
    fun retry()
    fun getDataOffline()
    fun isConnected()
}