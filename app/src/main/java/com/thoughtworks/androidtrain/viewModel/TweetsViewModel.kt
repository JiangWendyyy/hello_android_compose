package com.thoughtworks.androidtrain.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thoughtworks.androidtrain.model.databases.ApplicationDatabase
import com.thoughtworks.androidtrain.model.entity.Tweet
import com.thoughtworks.androidtrain.model.repositories.TweetRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TweetViewModel(application: Application) : AndroidViewModel(application) {

    private val tweetRepository: TweetRepository by lazy {
        TweetRepository(ApplicationDatabase(application).tweetDao()) }

    val tweets : MutableLiveData<List<Tweet>> = MutableLiveData<List<Tweet>>()

    fun pullData() {
        viewModelScope.launch {
            tweetRepository.saveFromRemote()
            tweets.postValue(tweetRepository.fetchTweets().first())
        }
    }
}