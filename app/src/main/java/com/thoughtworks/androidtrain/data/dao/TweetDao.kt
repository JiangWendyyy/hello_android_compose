package com.thoughtworks.androidtrain.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thoughtworks.androidtrain.model.entity.Tweet

@Dao
interface TweetDao {
    @Query("SELECT * FROM tweet")
    fun getAll(): List<Tweet>

    @Query("SELECT * FROM tweet WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Tweet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tweets: List<Tweet>)

    @Delete
    fun delete(tweet: Tweet)
}