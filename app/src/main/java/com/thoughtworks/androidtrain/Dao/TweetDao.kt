package com.thoughtworks.androidtrain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.thoughtworks.androidtrain.Entity.Tweet

@Dao
interface TweetDao {
    @Query("SELECT * FROM tweet")
    fun getAll(): List<Tweet>

    @Query("SELECT * FROM tweet WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Tweet>

    @Insert
    fun insertAll(tweets: List<Tweet>)

    @Delete
    fun delete(tweet: Tweet)
}