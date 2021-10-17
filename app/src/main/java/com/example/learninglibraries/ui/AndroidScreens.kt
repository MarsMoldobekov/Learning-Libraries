package com.example.learninglibraries.ui

import android.os.Bundle
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

interface IScreens {
    fun users(): Screen
    fun userPersonalScreen(bundle: Bundle): Screen
}

class AndroidScreens : IScreens {
    override fun users(): Screen = FragmentScreen { UsersFragment.newInstance() }

    override fun userPersonalScreen(bundle: Bundle): Screen =
        FragmentScreen { UserPersonalScreenFragment.newInstance(bundle) }
}