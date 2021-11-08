package com.example.learninglibraries.domain.net.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepository(
    @Expose val id: Long?,
    @Expose val description: String?,
    @Expose val forksCount: Int?,
    @Expose val fullName: String?,
    @Expose val name: String?
) : Parcelable