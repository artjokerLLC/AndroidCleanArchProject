package com.project.source.ui

import android.annotation.SuppressLint
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

val Date.uiDate
    @SuppressLint("SimpleDateFormat")
    get() =
        try {
            val format = SimpleDateFormat("EEE, d MMM yyyy | HH:mm")
            format.format(this).toString().toUpperCase()
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }

val Date.eventsDate
    get() =
        try {
            val format = SimpleDateFormat("LLLL yyyy", Locale.getDefault())
            format.format(this).toString().capitalize()
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }

fun ViewGroup.inflate(@LayoutRes resource: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(this.context).inflate(resource, this, attachToRoot)

fun ImageView.loadImage(url: Any?) {
    Glide.with(context)
            .load(url)
            .apply(RequestOptions().centerCrop().placeholder(R.drawable.ic_placeholder))
            .into(this)
}

fun <T> TextView.setTextWithArg(@StringRes strRes: Int, vararg: T) {
    this.context.getString(strRes, vararg)
            .let { text = it }
}

fun String.withPdfExt(): String = "$this.pdf"
