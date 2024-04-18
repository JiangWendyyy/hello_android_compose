package com.thoughtworks.androidtrain.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.thoughtworks.androidtrain.data.model.entity.Sender

@Dao
interface SenderDao {
    @Query("SELECT * FROM sender")
    fun getAll(): List<Sender>

    @Query("SELECT * FROM sender WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Sender>

    @Insert
    fun insertAll(senders: List<Sender>)

    @Delete
    fun delete(sender: Sender)
}