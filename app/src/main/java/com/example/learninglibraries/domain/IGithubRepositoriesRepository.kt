package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.net.data.GithubRepository
import io.reactivex.rxjava3.core.Single

interface IGithubRepositoriesRepository {
    fun getGithubRepositories(url: String, login: String): Single<List<GithubRepository>>
}