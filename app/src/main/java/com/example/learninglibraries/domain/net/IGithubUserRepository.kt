package com.example.learninglibraries.domain.net

import com.example.learninglibraries.domain.net.data.GithubUser
import com.example.learninglibraries.domain.net.data.GithubUserRepos
import io.reactivex.rxjava3.core.Single

interface IGithubUserRepository {
    fun getGithubUsers(): Single<List<GithubUser>>
    fun getGithubUserRepos(url: String): Single<List<GithubUserRepos>>
}