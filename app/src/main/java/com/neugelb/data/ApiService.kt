package com.neugelb.data

import com.neugelb.config.API_KEY
import com.neugelb.config.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiService {

    private fun createInterceptor() = Interceptor { chain ->
        val originalRequest = chain.request()
        val urlQueryParameter = originalRequest.url.newBuilder()
            .addQueryParameter(
                "api_key",
                API_KEY
            ).build()
        val new = originalRequest.newBuilder().url(urlQueryParameter)
            .build()
        chain.proceed(new)
    }


    private fun getClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    fun getClient(): Api {
        return Retrofit.Builder()
            .client(getClient(createInterceptor()))
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}