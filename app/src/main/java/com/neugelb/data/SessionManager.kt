package com.neugelb.data

import android.content.SharedPreferences
import com.neugelb.config.PREF_REMEMBER

class SessionManager(
    private var pref: SharedPreferences,
    private var editor: SharedPreferences.Editor
) {


    fun savePreString(value: String, key: String) {
        try {
            if (value.isNotEmpty()) {
                editor.putString(key, value)
                editor.commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getPrefString(key: String): String {
        var num = ""
        try {
            num = pref.getString(key, "").toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return num
    }

    fun getPreBool(key: String): Boolean {
        var num = false
        try {
            num = pref.getBoolean(key, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return num
    }

    fun savePreBool(value: Boolean, key: String) {
        try {
            editor.putBoolean(key, value)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun logout() = savePreBool(value = false, key = PREF_REMEMBER)
}