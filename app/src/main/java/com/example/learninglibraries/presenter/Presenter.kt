package com.example.learninglibraries.presenter

import com.example.learninglibraries.R
import com.example.learninglibraries.domain.CountersModel
import com.example.learninglibraries.ui.MainView

class Presenter(private val view: MainView) {
    private val countersModel = CountersModel()

    fun counterClick(id: Int) {
        when (id) {
            R.id.button_counter_1 -> {
                updateButtonText(0)
            }
            R.id.button_counter_2 -> {
                updateButtonText(1)
            }
            R.id.button_counter_3 -> {
                updateButtonText(2)
            }
        }
    }

    private fun updateButtonText(index: Int) {
        val nextValue = countersModel.next(index)
        view.setButtonText(index, nextValue.toString())
    }
}