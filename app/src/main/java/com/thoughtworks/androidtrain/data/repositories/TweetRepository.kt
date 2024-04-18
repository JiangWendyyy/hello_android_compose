package com.thoughtworks.androidtrain.data.repositories

import com.thoughtworks.androidtrain.dao.TweetDao
import com.thoughtworks.androidtrain.data.api.Api
import com.thoughtworks.androidtrain.data.model.entity.Tweet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TweetRepository(private val tweetDao: TweetDao){
    fun fetchTweets(): Flow<List<Tweet>> = flow {
        emit(tweetDao.getAll())
    }

    suspend fun saveFromRemote():List<Tweet> {
        val tweets = Api().fetchTweets().filter { it.isValid() }
        tweets.forEach(Tweet::generateAndBindId)
        tweetDao.insertAll(tweets)
        return tweets
    }
}