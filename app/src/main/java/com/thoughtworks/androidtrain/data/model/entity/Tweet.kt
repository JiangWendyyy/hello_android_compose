package com.thoughtworks.androidtrain.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.thoughtworks.androidtrain.entity.Comment
import com.thoughtworks.androidtrain.entity.Image
import com.thoughtworks.androidtrain.entity.Sender

@Entity
data class Tweet(
    var content: String?,
    var images: List<Image>?,
    var sender: Sender?,
    var comments: List<Comment>?,
    var error: String?,
    var date: String?,
    @SerializedName("unknown error") var unknownError: String?,
    @PrimaryKey @ColumnInfo(name = "id") var id: String = ""
) {

    override fun toString(): String {
        return "content $content \n"
    }

    fun isValid(): Boolean = error.isNullOrEmpty() && unknownError.isNullOrEmpty() && sender!=null && (content!=null || images!=null)
    fun generateAndBindId() {
        this.id = (sender?.nick + date).hashCode().toString()
    }
}