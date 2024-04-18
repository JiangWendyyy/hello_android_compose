package com.thoughtworks.androidtrain.ui.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import com.thoughtworks.androidtrain.androidassignment.ui.screens.TweetScreen
import com.thoughtworks.androidtrain.androidassignment.ui.theme.AndroidAssignmentTheme
import com.thoughtworks.androidtrain.androidassignment.ui.viewModel.TweetsViewModel
import com.thoughtworks.androidtrain.data.ApplicationDatabase
import com.thoughtworks.androidtrain.data.repositories.TweetRepository
import com.thoughtworks.androidtrain.data.repositories.UserRepository

class TweetsComposeActivity : ComponentActivity() {

    private val tweetRepository: TweetRepository by lazy {
        TweetRepository(ApplicationDatabase(application).tweetDao())
    }
    private val userRepository: UserRepository by lazy {
        UserRepository(ApplicationDatabase(application).userDao())
    }
    private val tweetViewModel: TweetsViewModel by lazy{
        TweetsViewModel(application,tweetRepository,userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        setContent {
            AndroidAssignmentTheme {
                val tweets = tweetViewModel.tweets.observeAsState().value
                val user = tweetViewModel.user.observeAsState().value
                TweetScreen(tweets = tweets, user)
            }
        }
    }
}