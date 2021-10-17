package com.example.learninglibraries.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learninglibraries.App
import com.example.learninglibraries.databinding.FragmentUsersBinding
import com.example.learninglibraries.domain.GithubUserRepository
import com.example.learninglibraries.presenter.UsersPresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {
    companion object {
        fun newInstance() = UsersFragment()
    }

    private val presenter by moxyPresenter {
        UsersPresenter(GithubUserRepository(), App.instance.router)
    }
    private var binding: FragmentUsersBinding? = null
    private var adapter: UserRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUsersBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun init() {
        binding?.recyclerviewUsers?.layoutManager = LinearLayoutManager(context)
        adapter = UserRecyclerViewAdapter(presenter.usersListPresenter)
        binding?.recyclerviewUsers?.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}