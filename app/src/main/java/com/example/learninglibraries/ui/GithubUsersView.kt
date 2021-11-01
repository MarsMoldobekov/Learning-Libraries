package com.example.learninglibraries.ui

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface GithubUsersView : MvpView {
    fun init()
    fun updateList(count: Int)
}