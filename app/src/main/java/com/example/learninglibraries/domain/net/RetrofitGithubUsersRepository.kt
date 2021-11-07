package com.example.learninglibraries.domain.net

import com.example.learninglibraries.domain.database.Database
import com.example.learninglibraries.domain.database.entities.RoomGithubUser
import com.example.learninglibraries.domain.net.data.GithubUser
import com.example.learninglibraries.domain.net.data.GithubUserRepos
import com.example.learninglibraries.ui.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepository(
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

    override fun getGithubUserRepos(url: String): Single<List<GithubUserRepos>> =
        api.getGithubUserRepos(url).subscribeOn(Schedulers.io())

    private fun getGithubUserFromExternalServer(): Single<List<GithubUser>> =
        api.getGithubUsers().flatMap { users ->
            Single.fromCallable {
                val roomUsers = users.map { user ->
                    RoomGithubUser(
                        user.id.toString(),
                        user.login ?: "",
                        user.avatarUrl ?: "",
                        user.reposUrl ?: ""
                    )
                }
                database.githubUserDao.insert(roomUsers)
                users
            }
        }

    private fun getGithubUserFromLocal(): Single<List<GithubUser>> =
        Single.fromCallable {
            database.githubUserDao.getAll().map { roomGithubUser ->
                GithubUser(
                    id = roomGithubUser.id.toInt(),
                    login = roomGithubUser.login,
                    avatarUrl = roomGithubUser.avatarUrl,
                    reposUrl = roomGithubUser.reposUrl
                )
            }
        }
}