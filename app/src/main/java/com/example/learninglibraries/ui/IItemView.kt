package com.example.learninglibraries.ui

interface IItemView {
    var pos: Int
}

interface UserItemView : IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}

interface GithubUserRepoItemView : IItemView {
    fun setRepoName(name: String)
    fun setRepoDescription(description: String)
}