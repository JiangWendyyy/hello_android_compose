package com.thoughtworks.androidtrain.api.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.Entity.Tweet
import org.json.JSONArray

class JsonUtil{
    fun getTweetListFromJsonStr(string: String): ArrayList<Tweet> {

        val list = ArrayList<Tweet>()
        val data = JSONArray(string)
        val gson = Gson()
        for (i in 0..<data.length()) {
            val tweetModel: Tweet = gson.fromJson(data.optJSONObject(i).toString(), Tweet::class.java)
            list.add(tweetModel)
        }
        return list
    }
}
