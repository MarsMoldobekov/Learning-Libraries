package com.example.learninglibraries.ui

import android.os.Bundle
import com.example.learninglibraries.domain.GithubUser
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface IScreens {
    fun users(): Screen
    fun userPersonalScreen(githubUser: GithubUser): Screen
}

class AndroidScreens : IScreens {
    override fun users(): Screen = FragmentScreen { UsersFragment.newInstance() }

    override fun userPersonalScreen(githubUser: GithubUser): Screen = FragmentScreen {
        UserPersonalScreenFragment.newInstance(Bundle().apply {
            putParcelable(UserPersonalScreenFragment.BUNDLE_EXTRA, githubUser)
        })
    }
}