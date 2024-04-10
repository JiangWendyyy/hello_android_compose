package com.thoughtworks.androidtrain

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.databases.ApplicationDatabase
import com.thoughtworks.androidtrain.Entity.Tweet
import com.thoughtworks.androidtrain.api.TweetController
import com.thoughtworks.androidtrain.api.util.JsonUtil
import com.thoughtworks.androidtrain.repositories.DataStoreManager
import com.thoughtworks.androidtrain.repositories.TweetRepository
import kotlinx.coroutines.launch
import org.json.JSONArray

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
                    try {
                        TweetController().fetchTweets().use { response ->
                            val tweets = convertJsonToList(JsonArray(response.body?.string()))
                            database.tweetDao().insertAll(tweets)
                            dataStoreManager.setIsTweetInit(true)
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@TweetsActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun getTweetList(): List<Tweet>? = TweetRepository(database.tweetDao()).fetchTweets().asLiveData().value

}