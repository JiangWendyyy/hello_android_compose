package com.thoughtworks.androidtrain.ui.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.androidtrain.androidassignment.ui.screens.DiscoverScreen
import com.thoughtworks.androidtrain.androidassignment.ui.theme.AndroidAssignmentTheme
import com.thoughtworks.androidtrain.ui.AppContent


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAssignmentTheme {
                AppContent(this)
            }
        }
    }

}