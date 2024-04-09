package com.thoughtworks.androidtrain

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.thoughtworks.androidtrain.repositories.DataStoreManager
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
class DataStoreActivity : AppCompatActivity() {
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(this)
        dataStoreManager.getIsHintShown().map {
            if(it){
                setContentView(R.layout.shared_preference_layout_back)
                Log.d("back", "onCreate: shared_preference_layout_back")
            }else{
                setContentView(R.layout.shared_preference_layout_remind)
                Log.d("remind", "onCreate: shared_preference_layout_remind")
            }
        }

        val button = findViewById<Button>(R.id.know)
        button.setOnClickListener {
            updateHintShown()
        }

    }

    private fun updateHintShown() {
        lifecycleScope.launch { dataStoreManager.setIsHintShown(true) }
        recreate()
    }
}