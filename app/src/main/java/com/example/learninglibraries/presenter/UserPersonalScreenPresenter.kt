package com.example.learninglibraries.presenter

import com.example.learninglibraries.domain.GithubUser
import com.example.learninglibraries.ui.UserView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class UserPersonalScreenPresenter(private val router: Router, private val user: GithubUser?) :
    MvpPresenter<UserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        user?.login?.let { viewState.setLogin(it) }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}