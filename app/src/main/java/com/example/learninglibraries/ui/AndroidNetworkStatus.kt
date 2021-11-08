package com.example.learninglibraries.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject

//TODO(listen to connectivity changes because the example below does not work)
class AndroidNetworkStatus(context: Context) : INetworkStatus {
    private val statusObject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    init {
        statusObject.onNext(false)
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .registerNetworkCallback(
                NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build(),
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        statusObject.onNext(true)
                    }

                    override fun onLost(network: Network) {
                        statusObject.onNext(false)
                    }

                    override fun onUnavailable() {
                        statusObject.onNext(false)
                    }
                }
            )
    }

    override fun isOnline(): Observable<Boolean> = statusObject

    override fun isOnlineSingle(): Single<Boolean> = statusObject.first(false)
}