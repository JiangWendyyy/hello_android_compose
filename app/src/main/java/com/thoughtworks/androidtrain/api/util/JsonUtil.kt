package com.thoughtworks.androidtrain.api.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.Entity.Tweet
import org.json.JSONArray

class JsonUtil{
    companion object {
        fun getTweetListFromJsonStr(string: String?): List<Tweet> {
            val list = ArrayList<Tweet>()
            val data = JSONArray(string)
            val gson = Gson()

            val type = object : TypeToken<List<Tweet>> {}.type

            val tweets: List<Tweet> = gson.fromJson(data, type)
            return list

        }
    }
}
