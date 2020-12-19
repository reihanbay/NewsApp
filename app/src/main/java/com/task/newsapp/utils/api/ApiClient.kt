package com.task.newsapp.utils.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        const val BASE_URL = "http://newsapi.org/v2/"
        private var retrofit : Retrofit? = null

        private fun provideHttpLoggingInterceptor() = kotlin.run {
            HttpLoggingInterceptor().apply {
                apply { level = HttpLoggingInterceptor.Level.BODY }
            }
        }

        fun getApiClientToken(mContext : Context) : Retrofit?{
            if (retrofit == null) {
                val okHttpClient = OkHttpClient
                    .Builder()
                    .addInterceptor(HeaderInterceptor(mContext))
                    .addInterceptor(provideHttpLoggingInterceptor())
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}