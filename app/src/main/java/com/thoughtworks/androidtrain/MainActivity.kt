package com.thoughtworks.androidtrain

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.KeyEvent
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    companion object {
        internal const val PICK_CONTACT_REQUEST = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var button1 = findViewById<Button>(R.id.toConstraintActivity)
        button1.setOnClickListener {
            navigateToActivity(ContactActivity::class.java)
        }
        var button2 = findViewById<Button>(R.id.toLoginActivity)
        button2.setOnClickListener {
            navigateToActivity(LoginActivity::class.java)
        }
        var button3 = findViewById<Button>(R.id.toContactActivity)
        button3.setOnClickListener {
            navigateToActivity(ContactActivity::class.java)
        }


    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            // When the user center presses, let them pick a contact.
            startActivityForResult(
                Intent(Intent.ACTION_PICK, Uri.parse("content://contacts")),
                PICK_CONTACT_REQUEST
            )
            return true
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        when (requestCode) {
            PICK_CONTACT_REQUEST ->
                if (resultCode == RESULT_OK) {
                    // A contact was picked. Display it to the user.
                    startActivity(Intent(Intent.ACTION_VIEW, intent?.data))
                    var data = intent?.data
                    val selectedContactName = data.getStringExtra("selected_contact_name")
                    val selectedContactPhone = data.getStringExtra("selected_contact_phone")
                    Toast.makeText(this, "Selected Contact: $selectedContactName, Phone: $selectedContactPhone", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun navigateToActivity(activityClass: Class<out Activity>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

}