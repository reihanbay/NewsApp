package com.task.newsapp.utils.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsModel(
    val author: String,
    val title: String,
    val desc: String,
    val url: String,
    val urlImg: String,
    val published: String,
    val content: String
) : Parcelable