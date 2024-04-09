package com.thoughtworks.androidtrain.repositories

import com.thoughtworks.androidtrain.entity.Tweet
import kotlinx.coroutines.flow.Flow


interface DataSource {
    fun fetchTweets(): Flow<List<Tweet>>
}