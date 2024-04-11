package com.thoughtworks.androidtrain.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://raw.githubusercontent.com/TW-Android-Junior-Training/android_training_practice/main/json/"

object RetrofitBuilder {

    val retrofit: TweetApi.ITweetApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TweetApi.ITweetApi::class.java)
}