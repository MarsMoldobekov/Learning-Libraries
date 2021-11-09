package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.net.IDataSource
import com.example.learninglibraries.domain.net.data.GithubUser
import com.example.learninglibraries.ui.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubUsersRepository(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val cache: IRoomGithubUsersCache
) : IGithubUserRepository {

    override fun getGithubUsers(): Single<List<GithubUser>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                api.getGithubUsers().flatMap { cache.insert(it) }
            } else {
                cache.getAll()
            }
        }.subscribeOn(Schedulers.io())
}