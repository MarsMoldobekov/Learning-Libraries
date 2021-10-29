package com.example.learninglibraries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learninglibraries.App
import com.example.learninglibraries.databinding.FragmentUserPersonalScreenBinding
import com.example.learninglibraries.presenter.UserPersonalScreenPresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserPersonalScreenFragment : MvpAppCompatFragment(), UserView, BackButtonListener {
    companion object {
        const val BUNDLE_EXTRA = "user_personal_screen"

        fun newInstance(bundle: Bundle) = UserPersonalScreenFragment().apply { arguments = bundle }
    }

    private val presenter by moxyPresenter {
        UserPersonalScreenPresenter(App.instance.router, arguments?.getParcelable(BUNDLE_EXTRA))
    }
    private var binding: FragmentUserPersonalScreenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUserPersonalScreenBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun backPressed(): Boolean = presenter.backPressed()

    override fun setLogin(login: String) {
        binding?.textViewUserLoginValue?.text = login
    }
}