package com.example.learninglibraries.presenter

import com.example.learninglibraries.domain.GithubUser
import com.example.learninglibraries.domain.IGithubUserRepository
import com.example.learninglibraries.ui.IScreens
import com.example.learninglibraries.ui.UserItemView
import com.example.learninglibraries.ui.UsersView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import timber.log.Timber

class UsersPresenter(
    private val uiScheduler: Scheduler,
    private val githubUserRepository: IGithubUserRepository,
    private val router: Router,
    private val screens: IScreens
) : MvpPresenter<UsersView>() {

    class UsersListPresenter : IUserListPresenter {
        private val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun bindView(view: UserItemView) {
            users[view.pos].login?.let { view.setLogin(it) }
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
                screens.userPersonalScreen(usersListPresenter.getUserByPosition(itemView.pos))
            )
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    private fun loadData() {
        githubUserRepository.getUsers()
            .observeOn(uiScheduler)
            .subscribe({ listOfGithubUsers ->
                usersListPresenter.clearData()
                usersListPresenter.addUsers(listOfGithubUsers)
                viewState.updateList()
            }, { Timber.d("Error: ${it.message}") })
    }
}