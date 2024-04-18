package com.thoughtworks.androidtrain.data.repositories

import com.thoughtworks.androidtrain.data.dao.TweetDao
import com.thoughtworks.androidtrain.data.api.Api
import com.thoughtworks.androidtrain.data.model.entity.Tweet

class TweetRepository(private val tweetDao: TweetDao){

    suspend fun saveFromRemote():List<Tweet> {
        val tweets = Api().fetchTweets().filter { it.isValid() }
        tweets.forEach(Tweet::generateAndBindId)
        tweetDao.insertAll(tweets)
        return tweets
    }
}