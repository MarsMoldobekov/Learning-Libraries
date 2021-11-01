package com.example.learninglibraries.presenter

import com.example.learninglibraries.ui.IScreens
import com.example.learninglibraries.ui.MainView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class MainPresenter(private val router: Router, private val screens: IScreens) :
    MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.githubUsers())
    }

    fun backClicked() {
        router.exit()
    }
}