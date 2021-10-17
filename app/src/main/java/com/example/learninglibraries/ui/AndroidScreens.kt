package com.example.learninglibraries.ui

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface IScreens {
    fun users(): Screen
}

class AndroidScreens : IScreens {
    override fun users(): Screen = FragmentScreen { UsersFragment.newInstance() }
}