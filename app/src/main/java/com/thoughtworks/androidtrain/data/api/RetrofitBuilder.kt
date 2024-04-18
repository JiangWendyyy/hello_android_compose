package com.thoughtworks.androidtrain.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TWEET_URL = "https://xianmobilelab.gitlab.io/moments-data/"
private const val User_URL = "https://xianmobilelab.gitlab.io/moments-data/"

object RetrofitBuilder {

    val retrofitTweet: Api.ITweetApi = Retrofit.Builder()
        .baseUrl(TWEET_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api.ITweetApi::class.java)

    val retrofitUser: Api.IUserApi = Retrofit.Builder()
        .baseUrl(User_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api.IUserApi::class.java)
}