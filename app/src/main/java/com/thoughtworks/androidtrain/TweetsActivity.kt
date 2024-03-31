package com.thoughtworks.androidtrain

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.model.Tweet

class TweetsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tweets_layout)

        val tweetList = deserializeFromJson<List<Tweet>>(JsonConstants.feedList)
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
                val isLastItemVisible = layoutManager.findLastVisibleItemPosition() == filteredTweets.size - 1
                val isAtBottom = dy > 0 && isLastItemVisible
                val footerViewHolder = recyclerView.findViewHolderForAdapterPosition(filteredTweets.size) as? TweetAdapter.FooterViewHolder
                if (isAtBottom && footerViewHolder != null) {
                    footerViewHolder.buttonReachedEnd.visibility = View.VISIBLE
                } else {
                    footerViewHolder?.buttonReachedEnd?.visibility = View.GONE
                }
            }
        })
    }



    // 使用 Gson 解析 JSON 字符串并创建 Comment 列表

    // 反序列化 JSON 字符串为对象
    private inline fun <reified T> deserializeFromJson(json: String): T {
        val gson = Gson()
        return gson.fromJson(json, object : TypeToken<T>() {}.type)
    }
}