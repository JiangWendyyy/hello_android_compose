package com.thoughtworks.androidtrain

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.thoughtworks.androidtrain.entity.Tweet

class TweetAdapter (public var tweets: List<Tweet>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nickTextView: TextView = itemView.findViewById(R.id.nick)
        val contentTextView: TextView = itemView.findViewById(R.id.content)
        val avatarImageView: ImageView = itemView.findViewById(R.id.image)
    }

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonReachedEnd: Button = itemView.findViewById(R.id.buttonReachedEnd)
    }

    override fun getItemViewType(position: Int): Int {
        return if(position<tweets.size){
            TYPE_COMMENT
        }else{
            TYPE_FOOTER
        }
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
            Log.d("position", "CommentViewHolder position: $position: ")
            holder.nickTextView.text = tweet.sender?.nick
            holder.contentTextView.text = tweet.content
            holder.avatarImageView.load(tweet.sender?.avatar) {
                crossfade(true)
                placeholder(R.drawable.avatar)
                transformations(CircleCropTransformation())
            }
        } else if (holder is FooterViewHolder ) {
            Log.d("FooterViewHolder", "is FooterViewHolder")
            if (position == tweets.size) {
                // 显示底部按钮
                Log.d("position", "FooterViewHolder position: ${tweets.size} ")
                holder.buttonReachedEnd.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount() = tweets.size+1
}