package com.thoughtworks.androidtrain.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thoughtworks.androidtrain.data.model.entity.User
import com.thoughtworks.androidtrain.data.model.entity.Tweet
import com.thoughtworks.androidtrain.data.repositories.TweetRepository
import com.thoughtworks.androidtrain.data.repositories.UserRepository
import kotlinx.coroutines.launch

class TweetsViewModel(
    application: Application,
    private val tweetRepository: TweetRepository,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {

    var tweets = MutableLiveData<List<Tweet>>()
    var user = MutableLiveData<User?>()

    init{
        viewModelScope.launch {
            val tweetList = tweetRepository.saveFromRemote()
            tweets.postValue(tweetList)
            Log.d("tweets", ": $tweetList")
            Log.d("tweets", ": ${tweets.value}")

            val userInfo = userRepository.saveFromRemote()
            user.postValue(userInfo)
        }
    }
}