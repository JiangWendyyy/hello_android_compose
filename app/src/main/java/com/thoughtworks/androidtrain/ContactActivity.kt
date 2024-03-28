package com.thoughtworks.androidtrain

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContactActivity : AppCompatActivity() {
    data class Contact(val name: String, val phone: String)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.contact_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 选择联系人并返回结果
        val contact = Contact("John Doe", "123-456-7890")
        val intent = Intent()
        intent.putExtra("selected_contact_name", contact.name)
        intent.putExtra("selected_contact_phone", contact.phone)
        setResult(Activity.RESULT_OK, intent)
        finish() // 结束 ContactActivity 并返回结果
    }


}