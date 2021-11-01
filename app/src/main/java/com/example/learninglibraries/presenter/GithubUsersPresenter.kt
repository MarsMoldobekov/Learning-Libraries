package com.example.learninglibraries.presenter

import com.example.learninglibraries.domain.IGithubUserRepository
import com.example.learninglibraries.domain.data.GithubUser
import com.example.learninglibraries.ui.GithubUsersView
import com.example.learninglibraries.ui.IScreens
import com.example.learninglibraries.ui.UserItemView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import timber.log.Timber

class GithubUsersPresenter(
    private val uiScheduler: Scheduler?,
    private val githubUserRepository: IGithubUserRepository?,
    private val router: Router?,
    private val screens: IScreens?
) : MvpPresenter<GithubUsersView>() {

    private constructor(builder: Builder) : this(
        builder.uiScheduler,
        builder.githubUserRepository,
        builder.router,
        builder.screens
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var uiScheduler: Scheduler? = null
        var githubUserRepository: IGithubUserRepository? = null
        var router: Router? = null
        var screens: IScreens? = null

        fun build() = GithubUsersPresenter(this)
    }

    class UsersListPresenter : IGithubUserListPresenter {
        private val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun bindView(view: UserItemView) {
            with(users[view.pos]) {
                login?.let { view.setLogin(it) }
                avatarUrl?.let { view.loadAvatar(it) }
            }
        }

        override fun getCount(): Int = users.size

        fun addUsers(listOfGithubUsers: List<GithubUser>) {
            users.addAll(listOfGithubUsers)
        }

        fun getUserByPosition(pos: Int): GithubUser = users[pos]

        fun clearData(): Unit = users.clear()
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
            screens?.githubUserPersonalScreen(usersListPresenter.getUserByPosition(itemView.pos))
                ?.let { router?.navigateTo(it) }
        }
    }

    fun backPressed(): Boolean {
        router?.exit()
        return true
    }

    private fun loadData() {
        if (uiScheduler != null) {
            githubUserRepository?.getGithubUsers()
                ?.observeOn(uiScheduler)
                ?.subscribe({ listOfGithubUsers ->
                    usersListPresenter.clearData()
                    usersListPresenter.addUsers(listOfGithubUsers)
                    viewState.updateList(listOfGithubUsers.size)
                }, { Timber.d("Error: ${it.message}") })
        }
    }
}