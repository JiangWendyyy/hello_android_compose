package com.thoughtworks.androidtrain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Tweet(
    @ColumnInfo var content: String?,
    @ColumnInfo var images: List<Image>?,
    @ColumnInfo var sender: Sender?,
    @ColumnInfo var comments: List<Comment>?,
    @ColumnInfo var error: String?,
    @ColumnInfo @SerializedName("unknown error") var unknownError: String?
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    override fun toString(): String {
        return "content $content \n"
    }

    fun isValid(): Boolean = error.isNullOrEmpty() && unknownError.isNullOrEmpty() && sender!=null
}