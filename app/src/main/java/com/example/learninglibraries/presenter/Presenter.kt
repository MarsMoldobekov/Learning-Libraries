package com.example.learninglibraries.presenter

import com.example.learninglibraries.domain.CountersModel
import com.example.learninglibraries.ui.MainView

class Presenter(private val view: MainView) {
    private val countersModel = CountersModel()

    fun counterOneClick() {
        view.setButtonOneText(countersModel.next(0).toString())
    }

    fun counterTwoClick() {
        view.setButtonTwoText(countersModel.next(1).toString())
    }

    fun counterThreeClick() {
        view.setButtonThreeText(countersModel.next(2).toString())
    }
}