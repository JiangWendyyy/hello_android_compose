package com.thoughtworks.androidtrain.viewTest

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thoughtworks.androidtrain.R

class ConstraintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.constraint_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener{
            changeColor(button1)
        }
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener{
            changeColor(button2)
        }
    }

    private fun changeColor(button: Button) {
        if (button.backgroundTintList?.defaultColor  == ContextCompat.getColor(
                this,
                R.color.gray
            )
        ) {
            button.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.pink))
        } else {
            button.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray))
        }
    }
}