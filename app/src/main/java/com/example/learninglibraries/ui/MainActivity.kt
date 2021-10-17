package com.example.learninglibraries.ui

import android.os.Bundle
import com.example.learninglibraries.databinding.ActivityMainBinding
import com.example.learninglibraries.domain.CountersModel
import com.example.learninglibraries.presenter.MainPresenter
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    private val presenter by moxyPresenter { MainPresenter(CountersModel()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            buttonCounter1.setOnClickListener { presenter.counterOneClick() }
            buttonCounter2.setOnClickListener { presenter.counterTwoClick() }
            buttonCounter3.setOnClickListener { presenter.counterThreeClick() }
        }
    }

    override fun setButtonOneText(text: String) {
        binding.buttonCounter1.text = text
    }

    override fun setButtonTwoText(text: String) {
        binding.buttonCounter2.text = text
    }

    override fun setButtonThreeText(text: String) {
        binding.buttonCounter3.text = text
    }
}