package com.example.learninglibraries.ui

interface IItemView {
    var pos: Int
}

interface UserItemView : IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}