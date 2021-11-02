package com.example.learninglibraries.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.learninglibraries.databinding.ItemUserBinding
import com.example.learninglibraries.presenter.IGithubUserListPresenter

class GithubUsersRecyclerViewAdapter(
    private val presenter: IGithubUserListPresenter,
    private val imageLoader: IImageLoader<ImageView>
) : RecyclerView.Adapter<GithubUsersRecyclerViewAdapter.GithubUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder =
        GithubUserViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply { itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) } }

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    override fun getItemCount(): Int = presenter.getCount()

    inner class GithubUserViewHolder(private val viewBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(viewBinding.root), UserItemView {

        override var pos = -1

        override fun setLogin(text: String) = with(viewBinding) { textViewLogin.text = text }

        override fun loadAvatar(url: String) = with(viewBinding) {
            imageLoader.loadInto(url, imageViewAvatar)
        }
    }
}