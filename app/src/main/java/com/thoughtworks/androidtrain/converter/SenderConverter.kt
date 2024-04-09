package com.thoughtworks.androidtrain.converter

import androidx.datastore.preferences.protobuf.Empty
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thoughtworks.androidtrain.entity.Sender
import java.util.Objects

class SenderConverter {

    @TypeConverter
    fun toSender(json: String?): Sender? {
        if (json == null) {
            return null
        }
        val type = object : TypeToken<Sender>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(sender: Sender?): String? {
        val type = object: TypeToken<Sender>(){}.type
        return sender?.let { Gson().toJson(it, type) }
    }
}