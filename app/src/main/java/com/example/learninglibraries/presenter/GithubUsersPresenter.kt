package com.example.learninglibraries.presenter

import androidx.recyclerview.widget.DiffUtil
import com.example.learninglibraries.domain.net.IGithubUserRepository
import com.example.learninglibraries.domain.net.data.GithubUser
import com.example.learninglibraries.ui.GithubUsersView
import com.example.learninglibraries.ui.IScreens
import com.example.learninglibraries.ui.UserItemView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import timber.log.Timber

class GithubUsersPresenter private constructor(
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

        fun addUsers(listOfGithubUsers: List<GithubUser>): DiffUtil.DiffResult {
            val diffResult = DiffUtil.calculateDiff(UsersDiffUtilCallback(users, listOfGithubUsers))
            with(users) {
                clear()
                addAll(listOfGithubUsers)
            }
            return diffResult
        }

        fun getUserByPosition(pos: Int): GithubUser = users[pos]
    }

    class UsersDiffUtilCallback(
        private val oldList: List<GithubUser>,
        private val newList: List<GithubUser>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
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
                    viewState.updateList(usersListPresenter.addUsers(listOfGithubUsers))
                }, { Timber.d("Error: ${it.message}") })
        }
    }
}