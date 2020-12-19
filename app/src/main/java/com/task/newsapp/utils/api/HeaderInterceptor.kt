package com.task.newsapp.utils.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(val mContext : Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
     val apiKey = "6ca07e9973d64ca88cf3a1aacc65427e"
     proceed(
         request().newBuilder()
             .addHeader("Authorization", "Bearer $apiKey")
             .build()
     )
    }

}