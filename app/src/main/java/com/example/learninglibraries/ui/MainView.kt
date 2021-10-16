package com.example.learninglibraries.ui

import com.example.learninglibraries.presenter.Presenter

interface MainView {
    fun setButtonText(buttonCounter: Presenter.ButtonCounters, text: String)
}