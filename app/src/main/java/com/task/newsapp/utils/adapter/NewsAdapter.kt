package com.task.newsapp.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.task.newsapp.R
import com.task.newsapp.databinding.ContainerRvNewsBinding
import com.task.newsapp.utils.helper.DateHelper
import com.task.newsapp.utils.model.NewsModel


class NewsAdapter(val items: ArrayList<NewsModel>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val TAG = javaClass.simpleName

    fun refreshAdapter(list: List<NewsModel>) {
        items.addAll(list)
        notifyItemRangeChanged(0, items.size)
    }
    fun addList(list: List<NewsModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
    interface OnItemClickListener{
        fun onClick(data: NewsModel)
    }

    private lateinit var listener : OnItemClickListener

    fun setOnItemClick(onClick: OnItemClickListener){
        this.listener = onClick
    }


    class NewsViewHolder(val binding: ContainerRvNewsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.container_rv_news, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items[position]
        val date = DateHelper()

        holder.binding.tvTitle.text = item.title
        holder.binding.tvDate.text = item.published?.let { date.convertDate(it) }
        Glide.with(holder.itemView).load(item.urlImg).into(holder.binding.ivNews)
        holder.binding.ivNews.clipToOutline = true
        holder.binding.containerRv.setOnClickListener {
            listener.onClick(item)
        }

    }

    override fun getItemCount(): Int = items.size
}