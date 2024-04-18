package com.thoughtworks.androidtrain.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.thoughtworks.androidtrain.data.model.entity.Image

@Dao
interface ImageDao {
    @Query("SELECT * FROM image")
    fun getAll(): List<Image>

    @Query("SELECT * FROM image WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Image>

    @Insert
    fun insertAll(images: List<Image>)

    @Delete
    fun delete(image: Image)
}