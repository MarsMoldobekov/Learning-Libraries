package com.example.learninglibraries.domain.net.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class License(
    @Expose val key: String?,
    @Expose val name: String?,
    @Expose val nodeId: String?,
    @Expose val spdxId: String?,
    @Expose val url: String?
) : Parcelable