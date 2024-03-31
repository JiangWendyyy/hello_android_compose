package com.thoughtworks.androidtrain

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_SELECT_PHONE_NUMBER = 1
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
        val button1 = findViewById<Button>(R.id.toConstraintActivity)
        button1.setOnClickListener {
            navigateToActivity(ConstraintActivity::class.java)
        }
        val button2 = findViewById<Button>(R.id.toLoginActivity)
        button2.setOnClickListener {
            navigateToActivity(LoginActivity::class.java)
        }
        val button3 = findViewById<Button>(R.id.toContactActivity)
        button3.setOnClickListener {
            selectContact()
        }
        var button4 = findViewById<Button>(R.id.toLanguageSectionActivity)
        button4.setOnClickListener {
            navigateToActivity(LanguageSelectionActivity::class.java)
        }
        var button5 = findViewById<Button>(R.id.toTweetsActivity)
        button5.setOnClickListener {
            navigateToActivity(TweetsActivity::class.java)
        }


    }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == Activity.RESULT_OK) {
            // Get the URI and query the content provider for the contact
            val contactUri: Uri? = data?.data
            contactUri?.let {
                var name = ""
                var phoneNumber: String? = null
                val cursor = contentResolver.query(contactUri, null, null, null, null)
                cursor?.use {
                    if (cursor.moveToFirst()) {
                        val columnIndex =
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                        val hasPhoneNumber =
                            Integer.parseInt(columnIndex.let { cursor.getString(it) })
                        // If the cursor returned is valid, get the phone number.
                        if (hasPhoneNumber > 0) {
                            phoneNumber = getPhoneNumberByCursorWithId(cursor)
                        }
                        name = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME).let { cursor.getString(it) }
                    }
                }
                showToast(name, phoneNumber)
            }
        }
    }

    private fun showToast(name: String, phoneNumber: String?) {
        Toast.makeText(
            this,
            "Selected Contact name:$name, " +
                    "phone: $phoneNumber, ",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getPhoneNumberByCursorWithId(cursor: Cursor): String? {
        val contactIdIndex =
            cursor.getColumnIndex(ContactsContract.Contacts._ID)
        val contactId = contactIdIndex.let { cursor.getString(it) }
        val phoneCursor = contentResolver.query(
            Phone.CONTENT_URI,
            null,
            Phone.CONTACT_ID + "=" + contactId,
            null,
            null
        )
        phoneCursor?.use {
            while (phoneCursor.moveToNext()) {
                val phoneIndex =
                    phoneCursor.getColumnIndex(Phone.NUMBER)
                return phoneIndex.let { phoneCursor.getString(it) }
            }
        }
        return null
    }

    private fun selectContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER) // 启动ForResult
    }

    private fun navigateToActivity(activityClass: Class<out Activity>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

}