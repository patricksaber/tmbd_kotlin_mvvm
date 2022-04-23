package com.neugelb.interfaces

open class Connect: ConnectedToInternet {
    override fun retry() {}
    override fun getDataOffline() {}
    override fun isConnected() {}

}