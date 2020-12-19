package com.task.newsapp.detailnews

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.task.newsapp.R
import com.task.newsapp.databinding.ActivityDetailBinding
import com.task.newsapp.utils.helper.DateHelper
import com.task.newsapp.utils.model.NewsModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val date = DateHelper()
        val data : NewsModel? = intent.getParcelableExtra("SEND")
        Glide.with(this).load(data?.urlImg).into(binding.ivNews)
        binding.tvTitle.text = data?.title
        binding.tvDate.text = data?.published?.let { date.convertDate(it) }
        binding.tvContent.text = data?.content
        binding.tvAuthor.text = "Author-${data?.author}"
        val urlNews = data?.url

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.statusBarColor= Color.TRANSPARENT

        binding.btnUrl.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(urlNews)
            startActivity(intent)
        }
        binding.btnBack.setOnClickListener { onBackPressed() }



    }
}