package com.example.learninglibraries.ui

import androidx.recyclerview.widget.DiffUtil
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface GithubUserView : MvpView {
    fun setLogin(login: String)
    fun loadAvatar(url: String)
    fun init()
    fun updateList(diffResult: DiffUtil.DiffResult)
}