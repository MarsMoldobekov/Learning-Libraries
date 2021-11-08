package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.database.Database
import com.example.learninglibraries.domain.net.IDataSource
import com.example.learninglibraries.domain.net.data.GithubUser
import com.example.learninglibraries.domain.util.Mapper
import com.example.learninglibraries.ui.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

//TODO(Pull the caching into separate RoomGithubUsersCache class)
class GithubUsersRepository(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val database: Database
) : IGithubUserRepository {

    override fun getGithubUsers(): Single<List<GithubUser>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                getGithubUserFromExternalServer()
            } else {
                getGithubUserFromLocal()
            }
        }.subscribeOn(Schedulers.io())

    private fun getGithubUserFromExternalServer(): Single<List<GithubUser>> =
        api.getGithubUsers().flatMap { users ->
            Single.fromCallable {
                database.githubUserDao.insert(users.map { Mapper.convert(it) })
                users
            }
        }

    private fun getGithubUserFromLocal(): Single<List<GithubUser>> =
        Single.fromCallable { database.githubUserDao.getAll().map { Mapper.convert(it) } }
}