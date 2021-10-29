package com.example.learninglibraries.domain

import io.reactivex.rxjava3.core.Single

interface IGithubUserRepository {
    fun getUsers(): Single<List<GithubUser>>
}