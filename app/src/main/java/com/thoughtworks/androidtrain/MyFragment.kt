package com.thoughtworks.androidtrain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

class MyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.language_selection_layout, container, false)
        val androidTextView = view?.findViewById<TextView>(R.id.android_text)
        val javaTextView = view?.findViewById<TextView>(R.id.java_text)
        androidTextView?.isVisible = false
        javaTextView?.isVisible = false
        view.findViewById<Button>(R.id.Android).setOnClickListener {
            // 显示 "Android" 文本
            showText(R.id.Android)
        }
        view.findViewById<Button>(R.id.Java).setOnClickListener {
            // 显示 "Android" 文本
            showText(R.id.Java)
        }

        return view
    }

    private fun showText(@IdRes buttonId: Int) {
        val androidTextView = view?.findViewById<TextView>(R.id.android_text)
        val javaTextView = view?.findViewById<TextView>(R.id.java_text)
        // 根据点击的按钮，设置文本
        when (buttonId) {
            R.id.Android -> {
                androidTextView?.isVisible = true
                javaTextView?.isVisible = false
            }
            R.id.Java -> {
                javaTextView?.isVisible = true
                androidTextView?.isVisible = false
            }
        }
    }
}
