package com.example.learninglibraries.domain.database

import com.example.learninglibraries.domain.IRoomGithubRepositoriesCache
import com.example.learninglibraries.domain.database.entities.RoomGithubRepository
import com.example.learninglibraries.domain.net.data.GithubRepository
import com.example.learninglibraries.domain.util.Mapper
import io.reactivex.rxjava3.core.Single

class RoomGithubRepositoriesCache(private val database: Database) : IRoomGithubRepositoriesCache {
    override fun insert(
        listOfGithubUser: List<GithubRepository>,
        repositoryUrl: String,
        userLogin: String
    ): Single<List<GithubRepository>> = Single.fromCallable {
        val roomUser = database.githubUserDao.findByLogin(userLogin)
            ?: throw RuntimeException("No such user in cache")

        database.repositoryDao.insert(listOfGithubUser.map {
            RoomGithubRepository(
                it.id,
                it.description,
                it.forksCount,
                it.fullName,
                it.name,
                roomUser.id
            )
        })

        listOfGithubUser
    }

    override fun getAll(userLogin: String): Single<List<GithubRepository>> =
        Single.fromCallable {
            database.githubUserDao.findByLogin(userLogin)?.id?.let { userId ->
                database.repositoryDao.findForUser(userId).map { Mapper.convert(it) }
            }
        }
}