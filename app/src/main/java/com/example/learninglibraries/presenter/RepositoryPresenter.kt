package com.example.learninglibraries.presenter

import com.example.learninglibraries.domain.net.data.GithubRepository
import com.example.learninglibraries.ui.RepositoryView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class RepositoryPresenter(private val router: Router, private val repository: GithubRepository?) :
    MvpPresenter<RepositoryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        repository?.fullName?.let { viewState.setLogin(it) }
        repository?.description?.let { viewState.setDescription(it) }
        repository?.forksCount?.let { viewState.setForksCount(it) }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}