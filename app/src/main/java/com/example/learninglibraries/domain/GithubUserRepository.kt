package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.data.GithubUser

class GithubUserRepository {
    private val repositories: List<GithubUser> = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

    fun getUsers(): List<GithubUser> = repositories
}