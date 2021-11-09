package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.net.IDataSource
import com.example.learninglibraries.domain.net.data.GithubRepository
import com.example.learninglibraries.ui.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubRepositoriesRepository(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val cache: IRoomGithubRepositoriesCache
) : IGithubRepositoriesRepository {

    override fun getGithubRepositories(url: String, login: String): Single<List<GithubRepository>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                api.getGithubRepositories(url).flatMap { cache.insert(it, url, login) }
            } else {
                cache.getAll(login)
            }
        }.subscribeOn(Schedulers.io())
}