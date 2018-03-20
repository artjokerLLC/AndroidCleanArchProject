package com.project.source.ui

import android.support.annotation.StringRes

interface BaseListener {
    fun showProgress(@StringRes description: Int)
    fun hideProgress()
    fun onError(e: Throwable)
}

interface ToolbarListener : BaseListener {
    fun setToolbarConfig(titleStr: String, subTitleStr: String? = null, v: Boolean = true)
}

interface BackButtonListener {
    fun onBackPressed(): Boolean
}

interface RetryRequestListener {
    fun onRetryPressed()
}

interface TicketsListener {
    fun onTicketsCountChange()
}
