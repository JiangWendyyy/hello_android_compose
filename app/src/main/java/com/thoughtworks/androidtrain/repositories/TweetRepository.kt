package com.thoughtworks.androidtrain.repositories

import com.thoughtworks.androidtrain.dao.TweetDao
import com.thoughtworks.androidtrain.entity.Tweet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TweetRepository(private val tweetDao: TweetDao) : DataSource {
    override fun fetchTweets(): Flow<List<Tweet>> = flow {
        emit(tweetDao.getAll())
    }

}