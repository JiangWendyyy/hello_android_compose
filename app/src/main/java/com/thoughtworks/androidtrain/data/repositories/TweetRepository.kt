package com.thoughtworks.androidtrain.data.repositories

import androidx.lifecycle.LiveData
import com.thoughtworks.androidtrain.dao.TweetDao
import com.thoughtworks.androidtrain.api.TweetApi
import com.thoughtworks.androidtrain.model.entity.Tweet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TweetRepository(private val tweetDao: TweetDao) : DataSource {
    override fun fetchTweets(): Flow<List<Tweet>> = flow {
        emit(tweetDao.getAll())
    }

    suspend fun saveFromRemote() {
        val tweets = TweetApi().fetchTweets().filter { it.isValid() }
        tweets.forEach(Tweet::generateAndBindId)
        tweetDao.insertAll(tweets)
    }
}