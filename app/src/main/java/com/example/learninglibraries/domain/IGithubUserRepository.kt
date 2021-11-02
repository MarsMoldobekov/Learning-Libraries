package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.data.GithubUser
import com.example.learninglibraries.domain.data.GithubUserRepos
import io.reactivex.rxjava3.core.Single

interface IGithubUserRepository {
    fun getGithubUsers(): Single<List<GithubUser>>
    fun getGithubUserRepos(url: String): Single<List<GithubUserRepos>>
}