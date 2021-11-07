package com.example.learninglibraries.domain.net

import com.example.learninglibraries.domain.net.data.GithubUser
import com.example.learninglibraries.domain.net.data.GithubRepository
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface IDataSource {
    @GET("/users")
    fun getGithubUsers(): Single<List<GithubUser>>

    @GET
    fun getGithubRepositories(@Url url: String): Single<List<GithubRepository>>
}