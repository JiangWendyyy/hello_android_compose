package com.thoughtworks.androidtrain.ui.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.androidtrain.data.ApplicationDatabase
import com.thoughtworks.androidtrain.data.repositories.TweetRepository
import com.thoughtworks.androidtrain.ui.view.composes.LoadTweets
import com.thoughtworks.androidtrain.ui.viewModel.TweetViewModel

class TweetsComposeActivity : AppCompatActivity() {

    private val repository: TweetRepository by lazy {
        TweetRepository(
            ApplicationDatabase(
                application
            ).tweetDao()
        )
    }
    private val tweetViewModel: TweetViewModel by lazy { TweetViewModel(application, repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tweetViewModel.tweets.observe(this) { tweets ->
            setContent {
                LoadTweets(tweets)
            }
        }
    }
}