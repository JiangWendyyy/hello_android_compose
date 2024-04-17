package com.thoughtworks.androidtrain.data.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.entity.Image

class ImageConverter {

    @TypeConverter
    fun toImageList(json: String?): List<Image> {
        if (json == null) {
            return emptyList()
        }
        val type = object : TypeToken<List<Image>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(images: List<Image>?): String? {
        val type = object: TypeToken<List<Image>>(){}.type
        return images?.let { Gson().toJson(it, type) }
    }
}