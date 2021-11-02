package com.example.learninglibraries.presenter

import com.example.learninglibraries.domain.IGithubUserRepository
import com.example.learninglibraries.domain.data.GithubUser
import com.example.learninglibraries.domain.data.GithubUserRepos
import com.example.learninglibraries.ui.GithubUserRepoItemView
import com.example.learninglibraries.ui.GithubUserView
import com.example.learninglibraries.ui.IScreens
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import timber.log.Timber

class GithubUserPersonalScreenPresenter private constructor(
    private val uiScheduler: Scheduler?,
    private val githubUserRepository: IGithubUserRepository?,
    private val router: Router?,
    private val screens: IScreens?,
    private val user: GithubUser?
) : MvpPresenter<GithubUserView>() {

    private constructor(builder: Builder) : this(
        builder.uiScheduler,
        builder.githubUserRepository,
        builder.router,
        builder.screens,
        builder.user
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var uiScheduler: Scheduler? = null
        var githubUserRepository: IGithubUserRepository? = null
        var router: Router? = null
        var screens: IScreens? = null
        var user: GithubUser? = null

        fun build() = GithubUserPersonalScreenPresenter(this)
    }

    class GithubUserReposListPresenter : IGithubUserReposPresenter {
        private val repos = mutableListOf<GithubUserRepos>()

        override var itemClickListener: ((GithubUserRepoItemView) -> Unit)? = null

        override fun bindView(view: GithubUserRepoItemView) {
            with(repos[view.pos]) {
                fullName?.let { view.setRepoName(it) }
                description?.let { view.setRepoDescription(it) }
            }
        }

        override fun getCount(): Int = repos.size

        fun addUsers(listOfRepos: List<GithubUserRepos>) {
            repos.addAll(listOfRepos)
        }

        fun getRepoByPosition(pos: Int): GithubUserRepos = repos[pos]

        fun clearData(): Unit = repos.clear()
    }

    val githubUserReposListPresenter = GithubUserReposListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        user?.login?.let { viewState.setLogin(it) }
        user?.avatarUrl?.let { viewState.loadAvatar(it) }
        githubUserReposListPresenter.itemClickListener = { itemView ->
            screens?.repository(githubUserReposListPresenter.getRepoByPosition(itemView.pos))?.let {
                router?.navigateTo(it)
            }
        }
    }

    fun backPressed(): Boolean {
        router?.exit()
        return true
    }

    private fun loadData() {
        if (user?.reposUrl != null && uiScheduler != null) {
            githubUserRepository?.getGithubUserRepos(user.reposUrl)
                ?.observeOn(uiScheduler)
                ?.subscribe({ repos ->
                    githubUserReposListPresenter.clearData()
                    githubUserReposListPresenter.addUsers(repos)
                    viewState.updateList(repos.size)
                }, { throwable -> Timber.d("Error: ${throwable.message}") })
        }
    }
}