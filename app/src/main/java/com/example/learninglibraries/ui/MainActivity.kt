package com.example.learninglibraries.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learninglibraries.databinding.ActivityMainBinding
import com.example.learninglibraries.domain.GithubUserRepository
import com.example.learninglibraries.presenter.MainPresenter
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {
    private var binding: ActivityMainBinding? = null
    private val presenter by moxyPresenter { MainPresenter(GithubUserRepository()) }
    private var adapter: UserRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun init() {
        binding?.recyclerviewUsers?.layoutManager = LinearLayoutManager(this)
        adapter = UserRecyclerViewAdapter(presenter.usersListPresenter)
        binding?.recyclerviewUsers?.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }
}