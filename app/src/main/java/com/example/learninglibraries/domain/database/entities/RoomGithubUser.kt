package com.example.learninglibraries.domain.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoomGithubUser(
    @PrimaryKey var id: Long?,
    @ColumnInfo(name = "avatar_url") var avatarUrl: String?,
    @ColumnInfo(name = "events_url") val eventsUrl: String?,
    @ColumnInfo(name = "followers_url") val followersUrl: String?,
    @ColumnInfo(name = "following_url") val followingUrl: String?,
    @ColumnInfo(name = "gists_url") val gistsUrl: String?,
    @ColumnInfo(name = "gravatar_id") val gravatarId: String?,
    @ColumnInfo(name = "html_url") val htmlUrl: String?,
    @ColumnInfo(name = "login") var login: String?,
    @ColumnInfo(name = "node_id") val nodeId: String?,
    @ColumnInfo(name = "organizations_url") val organizationsUrl: String?,
    @ColumnInfo(name = "received_events_url") val receivedEventsUrl: String?,
    @ColumnInfo(name = "repos_url") var reposUrl: String?,
    @ColumnInfo(name = "site_admin") val siteAdmin: Boolean?,
    @ColumnInfo(name = "starred_url") val starredUrl: String?,
    @ColumnInfo(name = "subscriptions_url") val subscriptionsUrl: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "url") val url: String?
)