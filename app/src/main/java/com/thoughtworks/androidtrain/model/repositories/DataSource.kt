package com.thoughtworks.androidtrain.model.repositories

import com.thoughtworks.androidtrain.model.entity.Tweet
import kotlinx.coroutines.flow.Flow


interface DataSource {
    fun fetchTweets(): Flow<List<Tweet>>
}