package com.example.learninglibraries.domain

import io.reactivex.rxjava3.core.Observable

class GithubUserRepository {
    private val repositories: List<GithubUser> = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

    fun getUsers(): Observable<GithubUser> = Observable.fromIterable(repositories)
}