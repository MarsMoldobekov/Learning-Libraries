package com.example.learninglibraries.presenter

import com.example.learninglibraries.ui.IItemView
import com.example.learninglibraries.ui.UserItemView

interface IListPresenter<V: IItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}

interface IUserListPresenter : IListPresenter<UserItemView>