package com.thoughtworks.androidtrain.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sender(
    @ColumnInfo var userName: String,
    @ColumnInfo var nick: String,
    @ColumnInfo var avatar: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int=0
}