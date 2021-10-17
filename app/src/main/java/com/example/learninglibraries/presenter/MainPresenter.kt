package com.example.learninglibraries.presenter

import com.example.learninglibraries.domain.GithubUserRepository
import com.example.learninglibraries.domain.data.GithubUser
import com.example.learninglibraries.ui.MainView
import com.example.learninglibraries.ui.UserItemView
import moxy.MvpPresenter

class MainPresenter(private val githubUserRepository: GithubUserRepository) : MvpPresenter<MainView>() {
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

    fun loadData() {
        usersListPresenter.addUsers(githubUserRepository.getUsers())
        viewState.updateList()
    }
}