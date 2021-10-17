package com.example.learninglibraries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learninglibraries.databinding.FragmentUserPersonalScreenBinding
import com.example.learninglibraries.domain.GithubUser
import moxy.MvpAppCompatFragment

class UserPersonalScreenFragment : MvpAppCompatFragment(), BackButtonListener {
    companion object {
        const val BUNDLE_EXTRA = "user_personal_screen"

        fun newInstance(bundle: Bundle) = UserPersonalScreenFragment().apply { arguments = bundle }
    }

    private var binding: FragmentUserPersonalScreenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUserPersonalScreenBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<GithubUser>(BUNDLE_EXTRA)?.let { setData(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setData(githubUser: GithubUser) {
        binding?.textViewUserLoginValue?.text = githubUser.login
    }

    override fun backPressed(): Boolean {
        //TODO: implement backPressed event
        return false
    }
}