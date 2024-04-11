package com.thoughtworks.androidtrain.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.entity.Comment

class CommentConverter {

    @TypeConverter
    fun toCommentList(json: String?): List<Comment> {
        if (json == null) {
            return emptyList()
        }
        val type = object : TypeToken<List<Comment>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(comments: List<Comment>?): String? {
        val type = object: TypeToken<List<Comment>>(){}.type
        return comments?.let { Gson().toJson(it, type) }
    }
}