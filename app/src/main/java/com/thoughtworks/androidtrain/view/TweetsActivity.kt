package com.thoughtworks.androidtrain.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.androidtrain.model.databases.ApplicationDatabase
import com.thoughtworks.androidtrain.model.entity.Tweet
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.adapter.TweetAdapter
import com.thoughtworks.androidtrain.model.api.RetrofitBuilder.retrofit
import com.thoughtworks.androidtrain.model.repositories.DataStoreManager
import com.thoughtworks.androidtrain.model.repositories.TweetRepository
import com.thoughtworks.androidtrain.viewModel.TweetViewModel
import kotlinx.coroutines.launch
class TweetsActivity : AppCompatActivity(R.layout.tweets_layout) {
    private val database by lazy { ApplicationDatabase(this) }
    private val adapter = TweetAdapter(emptyList())
    private val tweetViewModel: TweetViewModel by lazy { TweetViewModel(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        initRecycleView(recyclerView)
        initTweets()
    }

    private fun initRecycleView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initTweets() {
        tweetViewModel.pullData()
        tweetViewModel.tweets.observe(this) { tweets ->
            adapter.tweets = tweets.sortedByDescending { it.date }
            adapter.notifyDataSetChanged()
        }
    }
}