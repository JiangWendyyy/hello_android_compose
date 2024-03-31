package com.thoughtworks.androidtrain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.androidtrain.model.Tweet

class TweetAdapter (private val tweets: List<Tweet>) : RecyclerView.Adapter<TweetAdapter.CommentViewHolder>() {

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nickTextView: TextView = itemView.findViewById(R.id.nick)
        val contentTextView: TextView = itemView.findViewById(R.id.content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tweet_itme_layout, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val tweet = tweets[position]
        holder.nickTextView.text = tweet.sender.nick
        holder.contentTextView.text = tweet.content
    }

    override fun getItemCount() = tweets.size
}