package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.data.GithubUser
import com.example.learninglibraries.domain.data.GithubUserRepos
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface IDataSource {
    @GET("/users")
    fun getGithubUsers(): Single<List<GithubUser>>

    @GET
    fun getGithubUserRepos(@Url url: String): Single<List<GithubUserRepos>>
}