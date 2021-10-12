package com.example.learninglibraries.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.learninglibraries.databinding.ActivityMainBinding
import com.example.learninglibraries.presenter.Presenter

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    private val presenter = Presenter(this)
    private val listener = View.OnClickListener { presenter.counterClick(it.id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonCounter1.setOnClickListener(listener)
            buttonCounter2.setOnClickListener(listener)
            buttonCounter3.setOnClickListener(listener)
        }
    }

    override fun setButtonText(index: Int, text: String) {
        when(index) {
            1 -> binding.buttonCounter1.text =  text
            2 -> binding.buttonCounter2.text =  text
            3 -> binding.buttonCounter3.text =  text
        }
    }
}