package com.example.learninglibraries.presenter

import android.os.Bundle
import com.example.learninglibraries.domain.GithubUserRepository
import com.example.learninglibraries.domain.GithubUser
import com.example.learninglibraries.ui.IScreens
import com.example.learninglibraries.ui.UsersView
import com.example.learninglibraries.ui.UserItemView
import com.example.learninglibraries.ui.UserPersonalScreenFragment
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class UsersPresenter(
    private val githubUserRepository: GithubUserRepository,
    private val router: Router,
    private val screens: IScreens
) : MvpPresenter<UsersView>() {

    class UsersListPresenter : IUserListPresenter {
        private val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun bindView(view: UserItemView) {
            view.setLogin(users[view.pos].login)
        }

        override fun getCount(): Int = users.size

        fun addUsers(listOfGithubUser: List<GithubUser>) {
            users.addAll(listOfGithubUser)
        }

        fun getUserByPosition(pos: Int): GithubUser = users[pos]
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
            router.navigateTo(screens.userPersonalScreen(Bundle().apply {
                putParcelable(
                    UserPersonalScreenFragment.BUNDLE_EXTRA,
                    usersListPresenter.getUserByPosition(itemView.pos)
                )
            }))
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    private fun loadData() {
        usersListPresenter.addUsers(githubUserRepository.getUsers())
        viewState.updateList()
    }
}