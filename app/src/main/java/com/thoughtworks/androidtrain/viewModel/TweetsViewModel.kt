package com.thoughtworks.androidtrain.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thoughtworks.androidtrain.data.ApplicationDatabase
import com.thoughtworks.androidtrain.model.entity.Tweet
import com.thoughtworks.androidtrain.data.repositories.TweetRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TweetViewModel(application: Application, private val tweetRepository: TweetRepository) : AndroidViewModel(application) {

    private val _tweets : MutableLiveData<List<Tweet>> = MutableLiveData<List<Tweet>>()

    val tweets: LiveData<List<Tweet>> = _tweets

    init {
        viewModelScope.launch {
            tweetRepository.saveFromRemote()
            _tweets.postValue(tweetRepository.fetchTweets().first())
        }
    }
}