package com.example.learninglibraries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learninglibraries.App
import com.example.learninglibraries.databinding.FragmentUserPersonalScreenBinding
import com.example.learninglibraries.domain.ApiHolder
import com.example.learninglibraries.domain.RetrofitGithubUsersRepository
import com.example.learninglibraries.presenter.GithubUserPersonalScreenPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class GithubUserPersonalScreenFragment : MvpAppCompatFragment(), GithubUserView, BackButtonListener {
    companion object {
        const val BUNDLE_EXTRA = "user_personal_screen"

        fun newInstance(bundle: Bundle) = GithubUserPersonalScreenFragment().apply { arguments = bundle }
    }

    private val presenter by moxyPresenter {
        GithubUserPersonalScreenPresenter(
            AndroidSchedulers.mainThread(),
            RetrofitGithubUsersRepository(ApiHolder.api),
            App.instance.router,
            arguments?.getParcelable(BUNDLE_EXTRA))
    }
    private var adapter: GithubUserReposRecyclerViewAdapter? = null
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
        binding?.login?.text = login
    }

    override fun loadAvatar(url: String) {
        binding?.avatar?.let { GlideImageLoader().loadInto(url, it) }
    }

    override fun init() {
        binding?.repositories?.layoutManager = LinearLayoutManager(context)
        adapter = GithubUserReposRecyclerViewAdapter(presenter.githubUserReposListPresenter)
        binding?.repositories?.adapter = adapter
    }

    override fun updateList(count: Int) {
        adapter?.notifyItemRangeInserted(0, count)
    }
}