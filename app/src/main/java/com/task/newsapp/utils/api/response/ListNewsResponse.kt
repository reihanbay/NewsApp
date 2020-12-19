package com.task.newsapp.utils.api.response

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class ListNewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<News>
) {
    data class News(
        @SerializedName("author") val author: String?,
        @SerializedName("title") val title : String?,
        @SerializedName("description") val desc : String?,
        val url : String?,
        @SerializedName("urlToImage") val urlImg: String?,
        @SerializedName("publishedAt") val published: String?,
        @SerializedName("content") val content: String?
    )
}