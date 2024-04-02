package com.thoughtworks.androidtrain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.androidtrain.model.Tweet

class TweetAdapter (private val tweets: List<Tweet>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nickTextView: TextView = itemView.findViewById(R.id.nick)
        val contentTextView: TextView = itemView.findViewById(R.id.content)
    }

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonReachedEnd: Button = itemView.findViewById(R.id.buttonReachedEnd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_COMMENT -> CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tweet_itme_layout, parent, false))
            TYPE_FOOTER -> FooterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bottom_button, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    companion object {
        private const val TYPE_COMMENT = 0
        private const val TYPE_FOOTER = 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CommentViewHolder) {
            // 绑定评论数据
            val tweet = tweets[position]
            holder.nickTextView.text = tweet.sender.nick
            holder.contentTextView.text = tweet.content
        } else if (holder is FooterViewHolder && position == tweets.size) {
            // 显示底部按钮
            holder.buttonReachedEnd.visibility = View.VISIBLE
        }
    }

    override fun getItemCount() = tweets.size+1
}