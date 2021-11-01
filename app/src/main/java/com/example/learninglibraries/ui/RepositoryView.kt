package com.example.learninglibraries.ui

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RepositoryView : MvpView {
    fun setLogin(login: String)
    fun setDescription(description: String)
    fun setForksCount(forksCount: Int)
}