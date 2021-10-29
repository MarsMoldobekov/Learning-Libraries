package com.example.learninglibraries.ui

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}