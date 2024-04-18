package com.thoughtworks.androidtrain.ui.view.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.data.model.entity.User
import com.thoughtworks.androidtrain.data.model.entity.Tweet
import com.thoughtworks.androidtrain.data.model.entity.Image

@Composable
fun TweetScreen(
    tweets: List<Tweet>?,
    user: User?,
) {
    if (user == null) {
        return
    }
    Column(
        modifier = Modifier.verticalScroll(state = ScrollState(1), enabled = true)
    ) {
        UserTitleItem(user)
        TweetsItem(tweets, user)
    }
}

@Composable
private fun UserTitleItem(user: User) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(370.dp),
            contentScale = ContentScale.Crop,
            model = user.profileImage,
            contentDescription = user.id
        )
        Row(modifier = Modifier.align(Alignment.BottomEnd)) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = user.nick,
                color = Color.White
            )
            AsyncImage(
                modifier = Modifier
                    .padding(all = 5.dp)
                    .size(100.dp),
                model = user.avatar,
                contentDescription = user.id
            )
        }
    }
}

@Composable
private fun TweetsItem(tweets: List<Tweet>?, user: User) {
    if (tweets?.isEmpty() == true) {
        return
    }
    tweets?.forEach { TweetItem(tweet = it, user = user) }
    BottomItem()
}

@Composable
fun TweetItem(tweet: Tweet, user: User) {

    val likeFlag = remember { mutableStateOf(false) }
    val addCommentFlag = remember { mutableStateOf(false) }

    Row(Modifier.padding(10.dp)) {
        AsyncImage(
            modifier = Modifier
                .padding(all = 5.dp)
                .size(60.dp),
            model = tweet.sender?.avatar,
            contentDescription = null
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = tweet.sender?.nick ?: "",
                fontSize = 20.sp,
                color = Color.Blue
            )
            if(!tweet.content.isNullOrBlank()) {
                Text(
                    modifier = Modifier.padding(vertical = 2.dp),
                    text = tweet.content ?: "",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
            ImageItem(tweet.images)
            Row(modifier = Modifier.align(Alignment.End)) {
                ButtonsRow(likeFlag, addCommentFlag)
            }
            LikeRowItem(likeFlag, user)
            CommentListItem(addCommentFlag, user)
        }
    }
}

@Composable
private fun ImageItem(images: List<Image>?) {
    Row {
        images?.forEach {
            AsyncImage(
                modifier = Modifier
                    .padding(all = 5.dp)
                    .height(100.dp)
                    .width(100.dp),
                contentScale = ContentScale.Crop,
                model = it.url,
                contentDescription = it.id.toString()
            )
        }
    }
}

@Composable
private fun ButtonsRow(likeFlag: MutableState<Boolean>, addCommentFlag: MutableState<Boolean>) {
    val openFlag = remember { mutableStateOf(false) }
    var heartRid by remember { mutableIntStateOf(R.drawable.heart) }

    if (openFlag.value) {
        Button(
            modifier = Modifier.padding(2.dp),
            onClick = {
                heartRid =
                    if (heartRid == R.drawable.heart) R.drawable.heart_fill else R.drawable.heart
                likeFlag.value = !likeFlag.value
            }
        ) {
            Image(
                modifier = Modifier
                    .height(16.dp)
                    .padding(horizontal = 2.dp),
                imageVector = ImageVector.vectorResource(heartRid),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(horizontal = 2.dp),
                text = "Cancel"
            )
        }
        Button(
            modifier = Modifier.padding(2.dp),
            onClick = {
                openFlag.value = false
                addCommentFlag.value = true
            }
        ) {
            Image(
                modifier = Modifier
                    .height(16.dp)
                    .padding(horizontal = 2.dp),
                imageVector = ImageVector.vectorResource(R.drawable.comment),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(horizontal = 2.dp),
                text = "Comment"
            )
        }
    }


    Button(
        modifier = Modifier
            .height(40.dp)
            .width(60.dp)
            .padding(4.dp),
        onClick = { openFlag.value = !openFlag.value }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.more),
            contentDescription = ""
        )
    }
}

@Composable
private fun LikeRowItem(likeFlag: MutableState<Boolean>, user: User) {
    if (!likeFlag.value) {
        return
    }
    Row {
        Image(
            modifier = Modifier
                .height(16.dp)
                .padding(horizontal = 2.dp),
            imageVector = ImageVector.vectorResource(R.drawable.heart),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = user.nick
        )
    }
}


@Composable
private fun CommentListItem(
    openTextField: MutableState<Boolean>,
    user: User
) {
    val commentList = remember { mutableListOf<String>() }
    commentList.forEach {
        Text(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            text = it
        )
    }

    if (!openTextField.value) {
        return
    }

    var text by remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text, onValueChange = { nextText -> text = nextText })
        Row(modifier = Modifier.align(Alignment.End)) {
            Button(
                modifier = Modifier.padding(2.dp),
                onClick = {
                    commentList.add(user.nick + ": " + text.text)
                    openTextField.value = false
                }) { Text(text = "save") }
            Button(
                modifier = Modifier.padding(2.dp),
                onClick = { openTextField.value = false }
            ) { Text(text = "cancel") }
        }
    }
}

@Composable
private fun BottomItem() {

    Text(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        text = "到底了",
        textAlign = TextAlign.Center
    )
}
