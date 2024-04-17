package com.thoughtworks.androidtrain.ui.view.composes

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.thoughtworks.androidtrain.data.model.entity.Tweet


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
    val openDialog = remember { mutableStateOf(false) }
    val openTextField = remember { mutableStateOf(false) }

    DiaLogItem(openDialog, tweet)
    Row {
        AsyncImage(
            modifier = Modifier
                .padding(all = 5.dp)
                .clickable {
                    openDialog.value = true
                }
                .size(60.dp),
            model = tweet.sender?.avatar,
            contentDescription = null
        )
        Column(modifier = Modifier.clickable {
            openTextField.value = true
        }) {
            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = tweet.sender?.nick ?: "",
                color = Color.Black
            )
            Text(
                modifier = Modifier.padding(vertical = 2.dp),
                text = tweet.content ?: "",
                color = Color.Black
            )
            CommentListItem(openTextField)
        }
    }
}

@Composable
private fun CommentListItem(
    openTextField: MutableState<Boolean>,
) {
    val commentList = remember { mutableListOf<String>() }
    commentList.forEach {
        Text(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .background(color = Color.LightGray),
            text = it
        )
    }

    if (!openTextField.value) {
        return
    }

    var text by remember { mutableStateOf(TextFieldValue("")) }
    Row {
        TextField(
            modifier = Modifier.width(170.dp),
            value = text, onValueChange = { nextText -> text = nextText })
        Button(
            modifier = Modifier.padding(2.dp),
            onClick = {
                commentList.add(text.text)
                openTextField.value = false
            }) { Text(text = "save") }
        Button(
            modifier = Modifier.padding(2.dp),
            onClick = { openTextField.value = false }
        ) { Text(text = "cancel") }
    }
}

@Composable
private fun DiaLogItem(
    openDialog: MutableState<Boolean>,
    tweet: Tweet
) {
    if (!openDialog.value) {
        return
    }
    Dialog(onDismissRequest = { openDialog.value = false }) {
        AsyncImage(
            modifier = Modifier
                .padding(all = 5.dp)
                .clickable {
                    openDialog.value = true
                }
                .size(300.dp),
            model = tweet.sender?.avatar,
            contentDescription = null
        )
    }
}

@Composable
fun BottomItem() {
    Text(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        text = "到底了",
        textAlign = TextAlign.Center
    )
}