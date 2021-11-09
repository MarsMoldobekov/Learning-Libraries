package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.database.Database
import com.example.learninglibraries.domain.net.data.GithubUser
import com.example.learninglibraries.domain.util.Mapper
import io.reactivex.rxjava3.core.Single

class RoomGithubUsersCache(private val database: Database) : IRoomGithubUsersCache {
    override fun insert(listOfGithubUser: List<GithubUser>): Single<List<GithubUser>> =
        Single.fromCallable {
            database.githubUserDao.insert(listOfGithubUser.map { Mapper.convert(it) })
            listOfGithubUser
        }

    override fun getAll(): Single<List<GithubUser>> =
        Single.fromCallable { database.githubUserDao.getAll().map { Mapper.convert(it) } }
}