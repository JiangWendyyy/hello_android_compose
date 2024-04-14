package com.thoughtworks.androidtrain.viewTest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.adapter.TweetAdapter
import com.thoughtworks.androidtrain.data.ApplicationDatabase
import com.thoughtworks.androidtrain.data.repositories.TweetRepository
import com.thoughtworks.androidtrain.viewModel.TweetViewModel

class TweetsActivity : AppCompatActivity(R.layout.tweets_layout) {
    private val adapter = TweetAdapter(emptyList())
    private val tweetRepository: TweetRepository by lazy { TweetRepository(ApplicationDatabase(application).tweetDao()) }
    private val tweetViewModel: TweetViewModel by lazy { TweetViewModel(application, tweetRepository) }

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

    private fun initTweets() {
        tweetViewModel.tweets.observe(this) { tweets ->
            adapter.updateData(tweets.sortedByDescending { it.date?.toLong() })
        }
    }


}