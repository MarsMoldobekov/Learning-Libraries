package com.example.learninglibraries.presenter

import com.example.learninglibraries.domain.CountersModel
import com.example.learninglibraries.ui.MainView
import moxy.MvpPresenter

class MainPresenter(private val countersModel: CountersModel) : MvpPresenter<MainView>() {
    fun counterOneClick() {
        viewState.setButtonOneText(countersModel.next(0).toString())
    }

    fun counterTwoClick() {
        viewState.setButtonTwoText(countersModel.next(1).toString())
    }

    fun counterThreeClick() {
        viewState.setButtonThreeText(countersModel.next(2).toString())
    }
}