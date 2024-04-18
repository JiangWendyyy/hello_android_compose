package com.thoughtworks.androidtrain.androidassignment.data.dao

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.google.gson.annotations.SerializedName


@Entity
data class User(
    @ColumnInfo(name = "profile_image")@SerializedName("profile-image") var profileImage: String,
    @ColumnInfo var avatar: String,
    @ColumnInfo var nick: String,
    @ColumnInfo var username: String,
) {
    @PrimaryKey
    @ColumnInfo
    var id: String = ""


    fun generateAndBindId() {
        this.id = (nick.hashCode() * username.hashCode()).toString()
    }
}


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(user: User)

}