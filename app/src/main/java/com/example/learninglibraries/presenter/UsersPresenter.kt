package com.example.learninglibraries.presenter

import com.example.learninglibraries.domain.GithubUserRepository
import com.example.learninglibraries.domain.data.GithubUser
import com.example.learninglibraries.ui.UsersView
import com.example.learninglibraries.ui.UserItemView
import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class UsersPresenter(
    private val githubUserRepository: GithubUserRepository,
    private val router: Router
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
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
            //TODO: переход на экран пользователя
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