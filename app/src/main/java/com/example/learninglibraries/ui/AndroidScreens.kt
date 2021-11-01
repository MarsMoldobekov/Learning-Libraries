package com.example.learninglibraries.ui

import android.os.Bundle
import com.example.learninglibraries.domain.data.GithubUser
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface IScreens {
    fun githubUsers(): Screen
    fun githubUserPersonalScreen(githubUser: GithubUser): Screen
}

class AndroidScreens : IScreens {
    override fun githubUsers(): Screen = FragmentScreen { GithubUsersFragment.newInstance() }

    override fun githubUserPersonalScreen(githubUser: GithubUser): Screen = FragmentScreen {
        GithubUserPersonalScreenFragment.newInstance(Bundle().apply {
            putParcelable(GithubUserPersonalScreenFragment.BUNDLE_EXTRA, githubUser)
        })
    }
}