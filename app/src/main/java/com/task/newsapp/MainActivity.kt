package com.task.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.task.newsapp.databinding.ActivityMainBinding
import com.task.newsapp.fragment.listnews.NewsFragment
import com.task.newsapp.fragment.savednews.SavedFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)

        var newsFragment = NewsFragment()
        var savedFragment = SavedFragment()

        currentScreen(newsFragment)
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_news -> currentScreen(newsFragment)
                R.id.ic_save -> currentScreen(savedFragment)
                else -> currentScreen(newsFragment)
            }

        }
    }

    fun currentScreen(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_container, fragment)
                .commit()
        }
        return true
    }
}