package com.thoughtworks.androidtrain

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
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
        var button1 = findViewById<Button>(R.id.toConstraintActivity)
        button1.setOnClickListener {
            navigateToActivity(ConstraintActivity::class.java)
        }
        var button2 = findViewById<Button>(R.id.toLoginActivity)
        button2.setOnClickListener {
            navigateToActivity(LoginActivity::class.java)
        }
        var button3 = findViewById<Button>(R.id.toContactActivity)
        button3.setOnClickListener {
            //navigateToActivity(ContactActivity::class.java)
            selectContact()
        }


    }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == Activity.RESULT_OK) {
            // Get the URI and query the content provider for the phone number.
            val contactUri: Uri? = data?.data
            contactUri?.let {
                val cursor = contentResolver.query(contactUri, null, null, null, null)
                // If the cursor returned is valid, get the phone number.
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        val columnIndex =
                            cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                        val hasPhoneNumber = Integer.parseInt(columnIndex.let { cursor.getString(it) })
                        if(hasPhoneNumber >0){
                            val id = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                            val ID = id.let { cursor.getString(it) }
                            Log.d("ID", "ID:$ID ")
                            var phoneCursor = contentResolver.query(
                                Phone.CONTENT_URI,
                                null,
                                Phone.CONTACT_ID + "=" + ID,
                                null,
                                null
                            )

                            phoneCursor?.use {
                                while (phoneCursor.moveToNext()) {
                                    var phoneIndex =
                                        phoneCursor.getColumnIndex(Phone.NUMBER)
                                    var phone = phoneIndex.let { phoneCursor.getString(it) }
                                    Toast.makeText(
                                        this,
                                        "Selected Contact: $phone, ",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                        }
/*                        val nameIndex =
                            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        val name = nameIndex.let { cursor.getString(it) }
                        var phoneIndex =
                            cursor.getColumnIndex(Phone.NUMBER)
                        var phone = phoneIndex.let { cursor.getString(it) }
                        // Do something with the phone number.
                        Toast.makeText(
                            this,
                            "Selected Contact: $name, ",
                            Toast.LENGTH_LONG
                        ).show()*/
                        cursor.close()
                    }
                }
            }
        }
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