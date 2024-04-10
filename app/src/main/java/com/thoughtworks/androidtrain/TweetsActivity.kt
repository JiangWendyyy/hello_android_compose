package com.thoughtworks.androidtrain

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.androidtrain.databases.ApplicationDatabase
import com.thoughtworks.androidtrain.Entity.Tweet
import com.thoughtworks.androidtrain.api.RetrofitBuilder.retrofit
import com.thoughtworks.androidtrain.repositories.DataStoreManager
import com.thoughtworks.androidtrain.repositories.TweetRepository
import kotlinx.coroutines.launch
class TweetsActivity : AppCompatActivity(R.layout.tweets_layout) {
    private val database by lazy { ApplicationDatabase(this) }
    private val adapter = TweetAdapter(emptyList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStoreManager = DataStoreManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        initRecycleView(recyclerView)
        initTweets(dataStoreManager)

        // 添加滚动监听器
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val count = recyclerView.adapter?.itemCount ?: 0
                if (count != 0) {
                    val footerViewHolder =
                        recyclerView.findViewHolderForAdapterPosition(count) as? TweetAdapter.FooterViewHolder
                    val isLastItemVisible =
                        layoutManager.findLastVisibleItemPosition() >= count
                    val isAtBottom = dy > 0 && isLastItemVisible
                    if (isAtBottom && footerViewHolder != null) {
                        footerViewHolder.buttonReachedEnd.visibility = View.VISIBLE
                    } else {
                        footerViewHolder?.buttonReachedEnd?.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun initRecycleView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initTweets(dataStoreManager: DataStoreManager) {
        dataStoreManager.getIsTweetInit().asLiveData().observe(this) { isTweetInit ->
            if (!isTweetInit) {
                lifecycleScope.launch {
                    try {
                        val response = retrofit.fetchTweets()
                        if (response.isSuccessful) {
                            val tweets = response.body()
                            if (tweets != null) {
                                tweets.forEach(Tweet::generateAndBindId)
                                tweets.filter { it.isValid() }
                                lifecycleScope.launch { database.tweetDao().insertAll(tweets) }
                                dataStoreManager.setIsTweetInit(true)
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@TweetsActivity,
                            "error:${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                TweetRepository(database.tweetDao()).fetchTweets().asLiveData().observe(this) {
                    adapter.tweets = it
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}