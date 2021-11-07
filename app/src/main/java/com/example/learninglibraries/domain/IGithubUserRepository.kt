package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.net.data.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubUserRepository {
    fun getGithubUsers(): Single<List<GithubUser>>
}