package com.task.newsapp.fragment.listnews

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.newsapp.utils.api.response.ListNewsResponse
import com.task.newsapp.utils.api.service.NewsService
import com.task.newsapp.utils.model.NewsModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NewsViewModel : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    private lateinit var service: NewsService
    val newsLiveData = MutableLiveData<List<NewsModel>>()
    val emptyLiveData = MutableLiveData<Boolean>()
    val shimmerLiveData = MutableLiveData<Boolean>()


    fun setService(service: NewsService) {
        this.service = service
    }

    fun getNews() {
        launch {
            shimmerLiveData.value = true
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getNews()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main){
                        emptyLiveData.value = true
                    }
                }
            }
            if (response is ListNewsResponse) {
                emptyLiveData.value = false
                shimmerLiveData.value = false
                val list = response.articles.map {
                    NewsModel(
                        it.author.toString(),
                        it.title.toString(),
                        it.desc.toString(),
                        it.url.toString(),
                        it.urlImg.toString(),
                        it.published.toString(),
                        it.content.toString()
                    )
                }
                newsLiveData.value = list
            }
        }
        cancel()
    }
}