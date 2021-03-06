package com.example.learninglibraries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learninglibraries.App
import com.example.learninglibraries.databinding.FragmentUsersBinding
import com.example.learninglibraries.domain.database.Database
import com.example.learninglibraries.domain.net.ApiHolder
import com.example.learninglibraries.domain.GithubUsersRepository
import com.example.learninglibraries.domain.RoomGithubUsersCache
import com.example.learninglibraries.presenter.GithubUsersPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class GithubUsersFragment : MvpAppCompatFragment(), GithubUsersView, BackButtonListener {
    companion object {
        fun newInstance() = GithubUsersFragment()
    }

    private val presenter by moxyPresenter {
        GithubUsersPresenter.build {
            uiScheduler = AndroidSchedulers.mainThread()
            githubUserRepository = GithubUsersRepository(
                ApiHolder.api,
                AndroidNetworkStatus(requireContext()),
                RoomGithubUsersCache(Database.getInstance())
            )
            router = App.instance.router
            screens = AndroidScreens()
        }
    }
    private var binding: FragmentUsersBinding? = null
    private var adapter: GithubUsersRecyclerViewAdapter? = null

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
        adapter = GithubUsersRecyclerViewAdapter(presenter.usersListPresenter, GlideImageLoader())
        binding?.recyclerviewUsers?.adapter = adapter
    }

    override fun updateList(diffResult: DiffUtil.DiffResult) {
        adapter?.let { diffResult.dispatchUpdatesTo(it) }
    }

    override fun backPressed() = presenter.backPressed()
}