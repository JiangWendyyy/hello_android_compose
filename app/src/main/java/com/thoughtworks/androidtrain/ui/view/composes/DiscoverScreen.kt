package com.thoughtworks.androidtrain.androidassignment.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.ui.view.TweetsComposeActivity


private const val MOMENTS = "Moments"
private const val CHANNELS = "Channels"
private const val LIVE = "Live"
private const val SCAN = "Scan"
private const val LISTEN = "Listen"

@Composable
fun DiscoverScreen(context: Context) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column (modifier = Modifier.padding(vertical = 2.dp)) {
            BoxItem(context = context, icon = R.drawable.moments, title = MOMENTS)
        }
        Column (modifier = Modifier.padding(vertical = 2.dp)) {
            BoxItem(context = context, icon = R.drawable.channels, title = CHANNELS)
            BoxItem(context = context, icon = R.drawable.live, title = LIVE)
        }
        Column (modifier = Modifier.padding(vertical = 2.dp)) {
            BoxItem(context = context, icon = R.drawable.scan, title = SCAN)
            BoxItem(context = context, icon = R.drawable.listen, title = LISTEN)
        }
    }
}

@Composable
private fun BoxItem(
    context: Context,
    icon: Int,
    title: String
) {
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .height(60.dp)
            .fillMaxWidth()
            .clickable {
                if (title == MOMENTS) {
                    context.startActivity(Intent(context, TweetsComposeActivity::class.java))
                }
            },
    ) {
        Row(modifier = Modifier.fillMaxHeight()) {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterVertically),
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = ""
            )
            Text(modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically), text = title)
            Icon(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterVertically),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = ""
            )
        }
    }
}