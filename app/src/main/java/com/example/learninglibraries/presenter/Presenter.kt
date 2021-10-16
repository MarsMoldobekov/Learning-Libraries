package com.example.learninglibraries.presenter

import com.example.learninglibraries.domain.CountersModel
import com.example.learninglibraries.ui.MainView

class Presenter(private val view: MainView) {
    enum class ButtonCounters(val index: Int) {
        FIRST(0), SECOND(1), THIRD(2)
    }

    private val countersModel = CountersModel()

    fun counterClick(buttonCounter: ButtonCounters) {
        val nextValue = countersModel.next(buttonCounter.index)
        view.setButtonText(buttonCounter, nextValue.toString())
    }
}