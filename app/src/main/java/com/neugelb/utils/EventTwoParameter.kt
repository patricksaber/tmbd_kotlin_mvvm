package com.neugelb.utils

class EventTwoParameter<out T, out S>(private val content: T, private val secondContent: S) {

    var hasBeenHandled = false
        private set // Allow external read but not write


    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T {
        return content
    }

    fun peekSecondContent(): S {
        return secondContent
    }
}