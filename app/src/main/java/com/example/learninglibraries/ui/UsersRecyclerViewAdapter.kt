package com.example.learninglibraries.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.learninglibraries.databinding.ItemUserBinding
import com.example.learninglibraries.presenter.IUserListPresenter

class UsersRecyclerViewAdapter(
    private val presenter: IUserListPresenter,
    private val imageLoader: IImageLoader<ImageView>
) : RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply { itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) } }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    override fun getItemCount(): Int = presenter.getCount()

    inner class ViewHolder(private val viewBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(viewBinding.root), UserItemView {

        override var pos = -1

        override fun setLogin(text: String) = with(viewBinding) { textViewLogin.text = text }

        override fun loadAvatar(url: String) = with(viewBinding) {
            imageLoader.loadInto(url, imageViewAvatar)
        }
    }
}