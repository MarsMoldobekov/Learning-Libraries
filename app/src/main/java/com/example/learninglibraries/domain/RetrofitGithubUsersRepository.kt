package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.data.GithubUser
import com.example.learninglibraries.domain.data.GithubUserRepos
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepository(private val api: IDataSource) : IGithubUserRepository {
    override fun getGithubUsers(): Single<List<GithubUser>> =
        api.getGithubUsers().subscribeOn(Schedulers.io())

    override fun getGithubUserRepos(url: String): Single<List<GithubUserRepos>> =
        api.getGithubUserRepos(url).subscribeOn(Schedulers.io())
}