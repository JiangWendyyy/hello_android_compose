package com.thoughtworks.androidtrain.data.api

import com.thoughtworks.androidtrain.data.api.RetrofitBuilder.retrofitTweet
import com.thoughtworks.androidtrain.data.api.RetrofitBuilder.retrofitUser
import com.thoughtworks.androidtrain.data.model.entity.Tweet
import com.thoughtworks.androidtrain.data.model.entity.User

import retrofit2.Response
import retrofit2.http.GET

class Api {
    suspend fun fetchTweets(): List<Tweet> {
        return if (retrofitTweet.fetchTweets().isSuccessful) {
            retrofitTweet.fetchTweets().body() ?: emptyList()
        } else { emptyList() }
    }

    suspend fun fetchUser(): User? {
        return if (retrofitUser.fetchUser().isSuccessful) {
            retrofitUser.fetchUser().body()
        } else { null }
    }

    interface ITweetApi {
        @GET("tweets.json")
        suspend fun fetchTweets(): Response<List<Tweet>>
    }

    interface IUserApi {
        @GET("user.json")
        suspend fun fetchUser(): Response<User>
    }
}