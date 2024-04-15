package com.thoughtworks.androidtrain.view.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thoughtworks.androidtrain.model.entity.Tweet


@Composable
fun LoadTweets(tweets: List<Tweet>) {
    Column(
        modifier = Modifier.verticalScroll(state = ScrollState(1), enabled = true)
    ) {
        tweets.forEach { TweetItem(tweet = it) }
        BottomItem()
    }
}


@Composable
fun TweetItem(tweet: Tweet) {
    Row {
        Image(
            modifier = Modifier
                .padding(all = 5.dp)
                .size(60.dp),
            imageVector = Icons.Rounded.AccountBox,
            contentDescription = "avatar"
        )
        Column {
            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = tweet.sender?.nick ?: "error name",
                color = Color.Black
            )
            Text(
                modifier = Modifier.padding(vertical = 2.dp),
                text = tweet.content ?: "error content",
                color = Color.Black
            )
        }
    }
}

@Composable
fun BottomItem() {
    Text(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxWidth()
            .padding(vertical = 10.dp)
        ,
        text = "到底了",
        textAlign = TextAlign.Center
    )
}