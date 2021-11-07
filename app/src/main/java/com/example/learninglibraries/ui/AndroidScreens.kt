package com.example.learninglibraries.ui

import android.os.Bundle
import com.example.learninglibraries.domain.net.data.GithubUser
import com.example.learninglibraries.domain.net.data.GithubRepository
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface IScreens {
    fun githubUsers(): Screen
    fun githubUserPersonalScreen(githubUser: GithubUser): Screen
    fun repository(githubRepository: GithubRepository): Screen
}

class AndroidScreens : IScreens {
    override fun githubUsers(): Screen = FragmentScreen { GithubUsersFragment.newInstance() }

    override fun githubUserPersonalScreen(githubUser: GithubUser): Screen = FragmentScreen {
        GithubUserPersonalScreenFragment.newInstance(Bundle().apply {
            putParcelable(GithubUserPersonalScreenFragment.BUNDLE_EXTRA_USER_PERSONAL_SCREEN, githubUser)
        })
    }

    override fun repository(githubRepository: GithubRepository): Screen = FragmentScreen {
        RepositoryFragment.newInstance(Bundle().apply {
            putParcelable(RepositoryFragment.BUNDLE_EXTRA_REPOSITORY, githubRepository)
        })
    }
}