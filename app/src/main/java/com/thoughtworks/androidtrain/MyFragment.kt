package com.thoughtworks.androidtrain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class MyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.language_selection_layout, container, false)

        view.findViewById<Button>(R.id.Android).setOnClickListener {
            // 显示 "Android" 文本在右上方

        }

        view.findViewById<Button>(R.id.buttonJava).setOnClickListener {
            // 显示 "Java" 文本在右下方
            showText("Java", it, R.id.buttonJava)
        }

        return view
    }

    private fun showText(text: String, button: View, @IdRes buttonId: Int) {
        val textView = view?.findViewById<TextView>(R.id.textView)
        textView?.text = text
        textView?.visibility = View.VISIBLE

        // 根据点击的按钮，设置文本的位置
        when (buttonId) {
            R.id.buttonAndroid -> {
                textView?.layout_gravity = Gravity.END or Gravity.TOP
            }
            R.id.buttonJava -> {
                textView?.layout_gravity = Gravity.END or Gravity.BOTTOM
            }
        }
    }
}
