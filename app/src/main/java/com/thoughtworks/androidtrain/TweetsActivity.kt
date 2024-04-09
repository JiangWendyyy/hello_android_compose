package com.thoughtworks.androidtrain

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.model.Tweet
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class TweetsActivity : AppCompatActivity(R.layout.tweets_layout) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tweetList: List<Tweet> = convertJsonToList(R.raw.tweets)
        val filteredTweets =
            tweetList.filter { it.unknownError == null && it.error == null && it.sender != null }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TweetAdapter(filteredTweets) // 使用 Gson 解析的评论数据
        (recyclerView.adapter as TweetAdapter).notifyItemInserted(filteredTweets.size)

        // 添加滚动监听器
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val footerViewHolder = recyclerView.findViewHolderForAdapterPosition(filteredTweets.size) as? TweetAdapter.FooterViewHolder
                val isLastItemVisible = layoutManager.findLastVisibleItemPosition() >= filteredTweets.size
                val isAtBottom = dy > 0 && isLastItemVisible
                if (isAtBottom && footerViewHolder != null) {
                    footerViewHolder.buttonReachedEnd.visibility = View.VISIBLE
                } else {
                    footerViewHolder?.buttonReachedEnd?.visibility = View.GONE
                }
            }
        })
    }

    private fun convertJsonToList(resourceId: Int): List<Tweet> {
        val jsonRaw = resources.openRawResource(resourceId)
        val gson = Gson()
        val typeToken = object : TypeToken<List<Tweet>>() {}.type
        return gson.fromJson(jsonRaw.reader().readText(), typeToken)
    }

}