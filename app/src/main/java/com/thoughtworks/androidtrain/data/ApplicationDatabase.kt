package com.thoughtworks.androidtrain.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thoughtworks.androidtrain.data.model.converter.CommentConverter
import com.thoughtworks.androidtrain.data.model.converter.ImageConverter
import com.thoughtworks.androidtrain.data.model.converter.SenderConverter
import com.thoughtworks.androidtrain.dao.CommentDao
import com.thoughtworks.androidtrain.dao.ImageDao
import com.thoughtworks.androidtrain.dao.SenderDao
import com.thoughtworks.androidtrain.dao.TweetDao
import com.thoughtworks.androidtrain.entity.Comment
import com.thoughtworks.androidtrain.entity.Image
import com.thoughtworks.androidtrain.entity.Sender
import com.thoughtworks.androidtrain.data.model.entity.Tweet

private const val DB_NAME = "android_train_db"

@Database(entities = [Tweet::class, Image::class, Sender::class, Comment::class],
    version = 1, exportSchema = false)
@TypeConverters(value = [ImageConverter::class, SenderConverter::class, CommentConverter::class])
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun tweetDao(): TweetDao
    abstract fun imageDao(): ImageDao
    abstract fun senderDao(): SenderDao
    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        private var inastance: ApplicationDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): ApplicationDatabase {
            return inastance ?: synchronized(LOCK) {
                inastance ?: buildDatabase(context).also { inastance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, ApplicationDatabase::class.java, DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }
}