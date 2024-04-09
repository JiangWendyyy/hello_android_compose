package com.thoughtworks.androidtrain

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.databases.ApplicationDatabase
import com.thoughtworks.androidtrain.entity.Tweet
import com.thoughtworks.androidtrain.repositories.DataStoreManager
import com.thoughtworks.androidtrain.repositories.TweetRepository
import kotlinx.coroutines.launch
private const val DATA_INPUT_KEY = "isJsonDataInserted"
class TweetsActivity : AppCompatActivity(R.layout.tweets_layout) {
    private val database by lazy { ApplicationDatabase(this) }
    private val tweets: List<Tweet> by lazy { convertJsonToList(R.raw.tweets) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStoreManager = DataStoreManager(this)
        initTweets(dataStoreManager)
        val filteredTweets = getTweetList()?.filter { it.isValid() }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        initRecycleView(recyclerView, filteredTweets)

        // 添加滚动监听器
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (filteredTweets != null) {
                    val footerViewHolder =
                        recyclerView.findViewHolderForAdapterPosition(filteredTweets.size) as? TweetAdapter.FooterViewHolder
                    val isLastItemVisible =
                        layoutManager.findLastVisibleItemPosition() >= filteredTweets.size
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

    private fun initRecycleView(
        recyclerView: RecyclerView,
        filteredTweets: List<Tweet>?
    ) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = filteredTweets?.let { TweetAdapter(it) } // 使用 Gson 解析的评论数据
        if (filteredTweets != null) {
            (recyclerView.adapter as TweetAdapter).notifyItemInserted(filteredTweets.size)
        }
    }

    private fun convertJsonToList(resourceId: Int): List<Tweet> {
        val jsonRaw = resources.openRawResource(resourceId)
        val gson = Gson()
        val typeToken = object : TypeToken<List<Tweet>>() {}.type
        return gson.fromJson(jsonRaw.reader().readText(), typeToken)
    }

    private fun initTweets(dataStoreManager:DataStoreManager) {
        dataStoreManager.getIsTweetInit().asLiveData().observe(this) {
            Log.i("dataStore", "initTweets: $it")
            Log.i("tweets", "initTweets: ${tweets.size}")
            if (!it) {
                lifecycleScope.launch {
                    val filterTweets = tweets.filter { tweet -> tweet .isValid() }
                    database.tweetDao().insertAll(filterTweets)
                    dataStoreManager.setIsTweetInit(true)
                }
            }
        }
    }
    private fun getTweetList(): List<Tweet>? = TweetRepository(database.tweetDao()).fetchTweets().asLiveData().value

}