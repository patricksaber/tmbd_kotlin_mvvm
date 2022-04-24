@file:Suppress("unused")

package com.neugelb.config

import com.neugelb.BuildConfig

const val BASE_URL = BuildConfig.BASE_URL
const val API_KEY = BuildConfig.TMDB_API_KEY
const val EXTRA_IMG_URL_W500 = "https://image.tmdb.org/t/p/w500"
const val EXTRA_IMG_URL_W780 = "https://image.tmdb.org/t/p/w780"
const val MOVIE_YEAR = 2022
const val INTENT_ID = "intentID"
const val NAV_TAG = "NAV_TAG"
const val PREF_REMEMBER = "INTENT_REMEMBER"
const val PREF_USERNAME = "INTENT_USERNAME"
const val PREFER_NAME = "neugelb"
const val DATABASE_NAME = "neugelb.db"
const val DATABASE_VERSION = 2
const val DATABASE_FAVORITE = 2
const val DATABASE_LATEST = 3
const val THRESHOLD_CLICK_TIME = 250


const val TYPE_LOAD_NO_CONNECTION = 1
const val TYPE_NAVIGATE_NO_CONNECTION = 2
