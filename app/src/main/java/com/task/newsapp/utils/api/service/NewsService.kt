package com.task.newsapp.utils.api.service

import com.task.newsapp.utils.api.response.ListNewsResponse
import retrofit2.http.GET

interface NewsService {
    @GET("everything?domains=wsj.com")
    suspend fun getNews() : ListNewsResponse
}