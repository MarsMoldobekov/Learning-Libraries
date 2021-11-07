package com.example.learninglibraries.domain.net.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    @Expose val avatarUrl: String? = "",
    @Expose val eventsUrl: String? = "",
    @Expose val followersUrl: String? = "",
    @Expose val followingUrl: String? = "",
    @Expose val gistsUrl: String? = "",
    @Expose val gravatarId: String? = "",
    @Expose val htmlUrl: String? = "",
    @Expose val id: Int? = 0,
    @Expose val login: String? = "",
    @Expose val nodeId: String? = "",
    @Expose val organizationsUrl: String? = "",
    @Expose val receivedEventsUrl: String? = "",
    @Expose val reposUrl: String? = "",
    @Expose val siteAdmin: Boolean? = false,
    @Expose val starredUrl: String? = "",
    @Expose val subscriptionsUrl: String? = "",
    @Expose val type: String? = "",
    @Expose val url: String? = ""
) : Parcelable