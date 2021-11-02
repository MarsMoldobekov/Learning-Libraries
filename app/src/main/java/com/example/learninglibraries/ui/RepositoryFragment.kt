package com.example.learninglibraries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learninglibraries.App
import com.example.learninglibraries.databinding.FragmentRepositoryBinding
import com.example.learninglibraries.presenter.RepositoryPresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class RepositoryFragment : MvpAppCompatFragment(), BackButtonListener, RepositoryView {
    companion object {
        const val BUNDLE_EXTRA_REPOSITORY = "repository"

        fun newInstance(bundle: Bundle) = RepositoryFragment().apply { arguments = bundle }
    }

    private val presenter by moxyPresenter {
        RepositoryPresenter(
            App.instance.router,
            arguments?.getParcelable(BUNDLE_EXTRA_REPOSITORY))
    }
    private var binding: FragmentRepositoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRepositoryBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun backPressed(): Boolean = presenter.backPressed()

    override fun setLogin(login: String) {
        binding?.repositoryName?.text = login
    }

    override fun setDescription(description: String) {
        binding?.repositoryDescriptionValue?.text = description
    }

    override fun setForksCount(forksCount: Int) {
        binding?.repositoryForksCountValue?.text = forksCount.toString()
    }
}