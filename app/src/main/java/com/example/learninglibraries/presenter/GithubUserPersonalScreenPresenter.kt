package com.example.learninglibraries.presenter

import androidx.recyclerview.widget.DiffUtil
import com.example.learninglibraries.domain.net.IGithubUserRepository
import com.example.learninglibraries.domain.net.data.GithubUser
import com.example.learninglibraries.domain.net.data.GithubUserRepos
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

        fun addUsers(listOfRepos: List<GithubUserRepos>): DiffUtil.DiffResult {
            val diffResult = DiffUtil.calculateDiff(RepositoriesDiffUtilCallback(repos, listOfRepos))
            with(repos) {
                clear()
                addAll(listOfRepos)
            }
            return diffResult
        }

        fun getRepoByPosition(pos: Int): GithubUserRepos = repos[pos]
    }

    class RepositoriesDiffUtilCallback(
        private val oldList: List<GithubUserRepos>,
        private val newList: List<GithubUserRepos>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
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
                    viewState.updateList(githubUserReposListPresenter.addUsers(repos))
                }, { throwable -> Timber.d("Error: ${throwable.message}") })
        }
    }
}