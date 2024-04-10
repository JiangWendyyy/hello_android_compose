package com.thoughtworks.androidtrain.api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class TweetController {

    private val client: OkHttpClient = OkHttpClient()
    private val url =
        "https://raw.githubusercontent.com/TW-Android-Junior-Training/android_training_practice/main/json/tweets.json"

    @Throws(IOException::class)
    suspend fun fetchTweets(): Response {
        val request: Request = Request.Builder().url(url).build()
        val call = client.newCall(request)
        return call.execute()
    }

}