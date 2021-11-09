package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.net.data.GithubRepository
import io.reactivex.rxjava3.core.Single

interface IRoomGithubRepositoriesCache {
    fun insert(
        listOfGithubUser: List<GithubRepository>,
        repositoryUrl: String,
        userLogin: String
    ): Single<List<GithubRepository>>

    fun getAll(userLogin: String): Single<List<GithubRepository>>
}