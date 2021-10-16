package com.example.learninglibraries.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learninglibraries.databinding.ActivityMainBinding
import com.example.learninglibraries.presenter.Presenter

class MainActivity : AppCompatActivity(), MainView {
    private var binding: ActivityMainBinding? = null
    private val presenter = Presenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.buttonCounter1?.setOnClickListener { presenter.counterOneClick() }
        binding?.buttonCounter2?.setOnClickListener { presenter.counterTwoClick() }
        binding?.buttonCounter3?.setOnClickListener { presenter.counterThreeClick() }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun setButtonOneText(text: String) {
        binding?.buttonCounter1?.text = text
    }

    override fun setButtonTwoText(text: String) {
        binding?.buttonCounter2?.text = text
    }

    override fun setButtonThreeText(text: String) {
        binding?.buttonCounter3?.text = text
    }
}