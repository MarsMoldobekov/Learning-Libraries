package com.example.learninglibraries.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.learninglibraries.R
import com.example.learninglibraries.databinding.ActivityMainBinding
import com.example.learninglibraries.presenter.Presenter

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    private val presenter = Presenter(this)

    private val listener = View.OnClickListener {
        when (it.id) {
            R.id.button_counter_1 -> presenter.counterClick(Presenter.ButtonCounters.FIRST)
            R.id.button_counter_2 -> presenter.counterClick(Presenter.ButtonCounters.SECOND)
            R.id.button_counter_3 -> presenter.counterClick(Presenter.ButtonCounters.THIRD)
        }
    }

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

    override fun setButtonText(buttonCounter: Presenter.ButtonCounters, text: String) {
        when (buttonCounter) {
            Presenter.ButtonCounters.FIRST -> binding.buttonCounter1.text = text
            Presenter.ButtonCounters.SECOND -> binding.buttonCounter2.text = text
            Presenter.ButtonCounters.THIRD -> binding.buttonCounter3.text = text
        }
    }
}