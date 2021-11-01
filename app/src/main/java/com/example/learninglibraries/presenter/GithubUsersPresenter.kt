package com.example.learninglibraries.presenter

import com.example.learninglibraries.domain.data.GithubUser
import com.example.learninglibraries.domain.IGithubUserRepository
import com.example.learninglibraries.ui.IScreens
import com.example.learninglibraries.ui.UserItemView
import com.example.learninglibraries.ui.GithubUsersView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import timber.log.Timber

class GithubUsersPresenter(
    private val uiScheduler: Scheduler,
    private val githubUserRepository: IGithubUserRepository,
    private val router: Router,
    private val screens: IScreens
) : MvpPresenter<GithubUsersView>() {

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
            router.navigateTo(
                screens.githubUserPersonalScreen(usersListPresenter.getUserByPosition(itemView.pos))
            )
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    private fun loadData() {
        githubUserRepository.getGithubUsers()
            .observeOn(uiScheduler)
            .subscribe({ listOfGithubUsers ->
                usersListPresenter.clearData()
                usersListPresenter.addUsers(listOfGithubUsers)
                viewState.updateList(listOfGithubUsers.size)
            }, { Timber.d("Error: ${it.message}") })
    }
}