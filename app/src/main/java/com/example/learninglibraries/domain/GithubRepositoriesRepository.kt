package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.database.Database
import com.example.learninglibraries.domain.net.IDataSource
import com.example.learninglibraries.domain.net.data.GithubRepository
import com.example.learninglibraries.domain.util.Mapper
import com.example.learninglibraries.ui.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubRepositoriesRepository(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val database: Database
) : IGithubRepositoriesRepository {

    override fun getGithubRepositories(url: String, login: String): Single<List<GithubRepository>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                getGithubRepositoryFromExternalServer(url)
            } else {
                getGithubRepositoriesFromLocal(login)
            }
        }.subscribeOn(Schedulers.io())

    private fun getGithubRepositoryFromExternalServer(url: String): Single<List<GithubRepository>> =
        api.getGithubRepositories(url).flatMap { repositories ->
            Single.fromCallable {
                database.repositoryDao.insert(repositories.map { Mapper.convert(it) })
                repositories
            }
        }

    private fun getGithubRepositoriesFromLocal(login: String): Single<List<GithubRepository>> =
        Single.fromCallable {
            val roomGithubUser = database.githubUserDao.findByLogin(login)
            roomGithubUser?.id?.let {
                database.repositoryDao.findForUser(it).map { Mapper.convert(it) }
            }
        }
}