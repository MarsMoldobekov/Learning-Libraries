package com.example.learninglibraries.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learninglibraries.databinding.ItemRepoBinding
import com.example.learninglibraries.presenter.IGithubUserReposPresenter

class GithubUserReposRecyclerViewAdapter(private val presenter: IGithubUserReposPresenter) :
    RecyclerView.Adapter<GithubUserReposRecyclerViewAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply { itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) } }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    override fun getItemCount(): Int = presenter.getCount()

    inner class RepoViewHolder(private val viewBinding: ItemRepoBinding) :
        RecyclerView.ViewHolder(viewBinding.root), GithubUserRepoItemView {

        override var pos: Int = -1

        override fun setRepoName(name: String) = with(viewBinding) { repoName.text = name }

        override fun setRepoDescription(description: String) =
            with(viewBinding) { repoDescription.text = description }
    }
}