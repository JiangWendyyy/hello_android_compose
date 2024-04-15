package com.thoughtworks.androidtrain.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.thoughtworks.androidtrain.R
import com.thoughtworks.androidtrain.data.repositories.DataStoreManager
import kotlinx.coroutines.launch
class DataStoreActivity : AppCompatActivity(R.layout.shared_preference_layout_remind) {
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(this)
        val button = findViewById<Button>(R.id.know)
        dataStoreManager.getIsHintShown().asLiveData().observe(this) {
            Log.i("live data", "onCreate: $it")
            if (it) { updateView(button) }
        }
        button.setOnClickListener {
            updateHintShown()
        }
    }

    private fun updateView(button:Button) {
        button.visibility = View.GONE
        val textView = findViewById<TextView>(R.id.text)
        textView.setText(R.string.welcome_back)
    }

    private fun updateHintShown() {
        lifecycleScope.launch { dataStoreManager.setIsHintShown(true) }
        recreate()
    }
}