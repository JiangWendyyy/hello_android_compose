package com.thoughtworks.androidtrain

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SharedPreferenceActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private val PREFERENCES_NAME = "MyPrefs"
    private val IS_HINT_SHOWN_KEY = "isHintShown"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)

        val isHintShown = sharedPreferences.getBoolean(IS_HINT_SHOWN_KEY, false)
        if (isHintShown) {
            setContentView(R.layout.shared_preference_layout_back)
        } else {
            setContentView(R.layout.shared_preference_layout_remind)
            // 初始化 layout1 的按钮点击事件
        }

        if (!isHintShown) {
            setContentView(R.layout.shared_preference_layout_remind)
            val buttonShowHint = findViewById<Button>(R.id.know)
            buttonShowHint.setOnClickListener {
                // 更新 SharedPreferences 并重新加载布局
                updateSharedPreferencesAndReloadLayout()
            }
        }


    }
    private fun updateSharedPreferencesAndReloadLayout() {
        // 保存新的 isHintShown 状态
        val editor = sharedPreferences.edit()
        editor.putBoolean(IS_HINT_SHOWN_KEY, true)
        editor.apply()

        // 重新加载布局
        recreate()
    }
}