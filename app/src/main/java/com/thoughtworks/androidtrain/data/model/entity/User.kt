package com.thoughtworks.androidtrain.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @ColumnInfo(name = "profile_image")@SerializedName("profile-image") var profileImage: String,
    val avatar: String,
    val nick: String,
    val username: String,
) {
    @PrimaryKey
    @ColumnInfo
    var id: String = ""


    fun generateAndBindId() {
        this.id = (nick.hashCode() * username.hashCode()).toString()
    }
}

