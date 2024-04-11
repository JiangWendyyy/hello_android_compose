package com.thoughtworks.androidtrain.model.api

import com.thoughtworks.androidtrain.model.entity.Tweet

import com.thoughtworks.androidtrain.model.api.RetrofitBuilder.retrofit
import retrofit2.Response
import retrofit2.http.GET

class TweetApi {
    suspend fun fetchTweets(): List<Tweet> {
        return if (retrofit.fetchTweets().isSuccessful) {
            retrofit.fetchTweets().body() ?: emptyList()
        } else {
            emptyList()
        }
    }

    interface ITweetApi {
        @GET("tweets.json")
        suspend fun fetchTweets(): Response<List<Tweet>>
    }
}