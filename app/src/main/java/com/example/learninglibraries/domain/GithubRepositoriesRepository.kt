package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.database.Database
import com.example.learninglibraries.domain.database.entities.RoomGithubRepository
import com.example.learninglibraries.domain.net.IDataSource
import com.example.learninglibraries.domain.net.data.GithubRepository
import com.example.learninglibraries.domain.util.Mapper
import com.example.learninglibraries.ui.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

//TODO(Pull the caching into separate RoomGithubRepositoriesCache class)
class GithubRepositoriesRepository(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val database: Database
) : IGithubRepositoriesRepository {

    override fun getGithubRepositories(url: String, login: String): Single<List<GithubRepository>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                getGithubRepositoryFromExternalServer(url, login)
            } else {
                getGithubRepositoriesFromLocal(login)
            }
        }.subscribeOn(Schedulers.io())

    private fun getGithubRepositoryFromExternalServer(
        repositoryUrl: String,
        userLogin: String
    ): Single<List<GithubRepository>> {
        return api.getGithubRepositories(repositoryUrl).flatMap { repositories ->
            Single.fromCallable {
                val roomUser = database.githubUserDao.findByLogin(userLogin)
                    ?: throw RuntimeException("No such user in cache")

                database.repositoryDao.insert(repositories.map {
                    RoomGithubRepository(
                        it.id,
                        it.description,
                        it.forksCount,
                        it.fullName,
                        it.name,
                        roomUser.id
                    )
                })

                repositories
            }
        }
    }

    private fun getGithubRepositoriesFromLocal(userLogin: String): Single<List<GithubRepository>> =
        Single.fromCallable {
            val roomGithubUser = database.githubUserDao.findByLogin(userLogin)
            roomGithubUser?.id?.let { userId ->
                database.repositoryDao.findForUser(userId).map { Mapper.convert(it) }
            }
        }
}