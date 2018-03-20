package com.project.source.models

import android.content.Context
import android.net.ConnectivityManager
import com.project.source.core.LocalStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorageImpl @Inject constructor(private val context: Context) : LocalStorage {
    override fun internetConnected() = (context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager).activeNetworkInfo?.isConnectedOrConnecting == true
}
