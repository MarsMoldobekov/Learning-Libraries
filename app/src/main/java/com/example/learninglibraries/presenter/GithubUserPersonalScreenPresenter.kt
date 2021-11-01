package com.example.learninglibraries.presenter

import com.example.learninglibraries.domain.IGithubUserRepository
import com.example.learninglibraries.domain.data.GithubUser
import com.example.learninglibraries.domain.data.GithubUserRepos
import com.example.learninglibraries.ui.GithubUserRepoItemView
import com.example.learninglibraries.ui.GithubUserView
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import timber.log.Timber

class GithubUserPersonalScreenPresenter(
    private val uiScheduler: Scheduler,
    private val githubUserRepository: IGithubUserRepository,
    private val router: Router,
    private val user: GithubUser?
) : MvpPresenter<GithubUserView>() {

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
            githubUserReposListPresenter.getRepoByPosition(itemView.pos)
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    private fun loadData() {
        if (user?.reposUrl != null) {
            githubUserRepository.getGithubUserRepos(user.reposUrl)
                .observeOn(uiScheduler)
                .subscribe({ repos ->
                    githubUserReposListPresenter.clearData()
                    githubUserReposListPresenter.addUsers(repos)
                    viewState.updateList(repos.size)
                }, { throwable -> Timber.d("Error: ${throwable.message}") })
        }
    }
}