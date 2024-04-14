package com.thoughtworks.androidtrain.viewModel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.thoughtworks.androidtrain.data.ApplicationDatabase
import com.thoughtworks.androidtrain.data.repositories.TweetRepository
import com.thoughtworks.androidtrain.entity.Image
import com.thoughtworks.androidtrain.entity.Sender
import com.thoughtworks.androidtrain.model.entity.Tweet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TweetViewModelTest {

    private val applicationMock: Application = Mockito.mock(Application::class.java)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private val tweetRepository = TweetRepository(ApplicationDatabase(applicationMock).tweetDao())

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun should_fetch_right_data() {
        val mockData = flow{ emit(getFakeTweetList()) }
        `when`(tweetRepository.fetchTweets()).thenReturn(mockData)

        val actual = TweetViewModel(applicationMock,tweetRepository).tweets.value
        Assert.assertEquals(getFakeTweetList().get(0).content, actual?.get(0)?.content)
        Assert.assertEquals(getFakeTweetList().get(1).content, actual?.get(1)?.content)
    }

    private fun getFakeTweetList(): List<Tweet> {
        return listOf(
            Tweet(
                content = "content1",
                images = listOf(Image("url1")),
                sender = Sender("name1", "nick1", "url1"),
                comments = emptyList(),
                error = null,
                unknownError = null,
                date = "1612390640000"
            ),
            Tweet(
                content = "content2",
                images = listOf(Image("url2")),
                sender = Sender("name2", "nick2", "url2"),
                comments = emptyList(),
                error = null,
                unknownError = null,
                date = "1702390640001"
            )
        )
    }

    private fun getLiveData(list : List<Tweet>): MutableLiveData<List<Tweet>> {
        val liveData = MutableLiveData<List<Tweet>>()
        liveData.value = list
        return liveData
    }
}