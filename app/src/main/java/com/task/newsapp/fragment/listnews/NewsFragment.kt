package com.task.newsapp.fragment.listnews

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.task.newsapp.R
import com.task.newsapp.databinding.FragmentNewsBinding
import com.task.newsapp.detailnews.DetailActivity
import com.task.newsapp.utils.adapter.NewsAdapter
import com.task.newsapp.utils.api.ApiClient
import com.task.newsapp.utils.api.service.NewsService
import com.task.newsapp.utils.model.NewsModel
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel


class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var rvAdapter: NewsAdapter
    private lateinit var sv : SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        val service = ApiClient.getApiClientToken(activity as AppCompatActivity)
            ?.create(NewsService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }

        viewModel.getNews()
        setRecyclerView()
        subscribeLiveData()
        binding.refresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener{
            viewModel.getNews()
            setRecyclerView()
            subscribeLiveData()
            binding.refresh.isRefreshing = false
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.frameShimmer.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        binding.frameShimmer.stopShimmerAnimation()
    }
    fun subscribeLiveData() {
        viewModel.emptyLiveData.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.ivEmpty.visibility = View.VISIBLE
                binding.rvNews.visibility = View.GONE
                binding.frameShimmer.visibility = View.GONE
            } else {
                binding.ivEmpty.visibility = View.GONE
                binding.rvNews.visibility = View.VISIBLE
            }
        })

        viewModel.shimmerLiveData.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.rvNews.visibility = View.GONE
                binding.frameShimmer.visibility = View.VISIBLE
                binding.frameShimmer.startShimmerAnimation()
            }else {
                binding.rvNews.visibility = View.VISIBLE
                binding.frameShimmer.visibility = View.GONE
                binding.frameShimmer.stopShimmerAnimation()
            }
        })

        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer {
            (binding.rvNews.adapter as NewsAdapter).addList(it)

            sv = binding.svNews

            sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    var result : ArrayList<NewsModel> = ArrayList()
                    for(find in it){
                        if (find.title.contains(query) || find.content.contains(query) || find.author.contains(query) || find.desc.contains(query) || find.published.contains(query)) {
                            result.add(find)
                        }
                    }
                    (binding.rvNews.adapter as NewsAdapter).addList(result)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    var result : ArrayList<NewsModel> = ArrayList()
                    for(find in it){
                        if (find.title.contains(newText) || find.content.contains(newText) || find.author.contains(newText) || find.desc.contains(newText) || find.published.contains(newText)) {
                            result.add(find)
                        }
                    }
                    (binding.rvNews.adapter as NewsAdapter).addList(result)
                    return false
                }

            })
        })
    }

    fun setRecyclerView() {
        rvAdapter = NewsAdapter(arrayListOf())
        binding.rvNews.adapter = rvAdapter
        binding.rvNews.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvAdapter.setOnItemClick(object : NewsAdapter.OnItemClickListener {
            override fun onClick(data: NewsModel) {
                val intent = Intent(activity, DetailActivity::class.java)
                val data = NewsModel(
                    data.author,
                    data.title,
                    data.desc,
                    data.url,
                    data.urlImg,
                    data.published,
                    data.content
                )
                intent.putExtra("SEND", data)
                startActivity(intent)
            }

        })
    }

}