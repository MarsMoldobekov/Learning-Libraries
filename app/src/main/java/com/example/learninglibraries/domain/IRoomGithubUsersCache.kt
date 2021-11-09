package com.example.learninglibraries.domain

import com.example.learninglibraries.domain.net.data.GithubUser
import io.reactivex.rxjava3.core.Single

interface IRoomGithubUsersCache {
    fun insert(listOfGithubUser: List<GithubUser>): Single<List<GithubUser>>
    fun getAll(): Single<List<GithubUser>>
}