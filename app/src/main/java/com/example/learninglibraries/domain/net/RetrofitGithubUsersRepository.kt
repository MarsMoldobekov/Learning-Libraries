package com.example.learninglibraries.domain.net

import com.example.learninglibraries.domain.net.data.GithubUser
import com.example.learninglibraries.domain.net.data.GithubUserRepos
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepository(private val api: IDataSource) : IGithubUserRepository {
    override fun getGithubUsers(): Single<List<GithubUser>> =
        api.getGithubUsers().subscribeOn(Schedulers.io())

    override fun getGithubUserRepos(url: String): Single<List<GithubUserRepos>> =
        api.getGithubUserRepos(url).subscribeOn(Schedulers.io())
}