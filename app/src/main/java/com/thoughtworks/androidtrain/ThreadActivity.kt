package com.thoughtworks.androidtrain

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ThreadActivity : AppCompatActivity() {

    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.thread_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        button = findViewById<Button>(R.id.thread)
        button.setOnClickListener{
            button.isEnabled = false
            CounterAsyncTask().execute()
        }
    }

    private inner class CounterAsyncTask : AsyncTask<Void, Int, Void>() {
        private var counter = 0

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): Void? {
            while (counter < 10) {
                Thread.sleep(1000)
                counter++
                publishProgress(counter)
            }
            return null
        }

        @Deprecated("Deprecated in Java")
        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            button.text = values[0].toString()
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            button.isEnabled = true
        }
    }
}