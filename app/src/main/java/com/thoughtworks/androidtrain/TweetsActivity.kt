package com.thoughtworks.androidtrain

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.databases.ApplicationDatabase
import com.thoughtworks.androidtrain.Entity.Tweet
import com.thoughtworks.androidtrain.repositories.DataStoreManager
import com.thoughtworks.androidtrain.repositories.TweetRepository
import kotlinx.coroutines.launch
class TweetsActivity : AppCompatActivity(R.layout.tweets_layout) {
    private val database by lazy { ApplicationDatabase(this) }
    private val tweets: List<Tweet> by lazy { convertJsonToList(R.raw.tweets) }
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
                val count = recyclerView.adapter?.itemCount?:0
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

    private fun convertJsonToList(resourceId: Int): List<Tweet> {
        val jsonRaw = resources.openRawResource(resourceId)
        val gson = Gson()
        val typeToken = object : TypeToken<List<Tweet>>() {}.type
        return gson.fromJson(jsonRaw.reader().readText(), typeToken)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initTweets(dataStoreManager:DataStoreManager) {
        dataStoreManager.getIsTweetInit().asLiveData().observe(this) { isTweetInit ->
            if (!isTweetInit) {
                lifecycleScope.launch {
                    val filterTweets = tweets.filter { tweet -> tweet.isValid() }
                    Log.i("filterTweets", "initTweets: ${filterTweets.size}")
                    database.tweetDao().insertAll(filterTweets)
                    dataStoreManager.setIsTweetInit(true)
                }
            }else{
                TweetRepository(database.tweetDao()).fetchTweets().asLiveData().observe(this) {
                    adapter.tweets = it
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

}