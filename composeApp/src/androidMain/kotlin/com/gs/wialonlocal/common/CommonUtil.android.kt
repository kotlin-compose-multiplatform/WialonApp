package com.gs.wialonlocal.common

import android.content.Intent
import android.net.Uri
import android.content.Context
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

actual fun openNavigationApp(endLatitude: Double, endLongitude: Double, context: Any?) {
    val uri = Uri.parse("google.navigation:q=$endLatitude,$endLongitude")
    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.google.android.apps.maps")
    }
    intent.resolveActivity((context as Context).packageManager)?.let {
        context.startActivity(intent)
    }
}

class AndroidUrlSharer : UrlSharer {
    override fun shareUrl(url: String, context: Any?) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }
        (context as Context).startActivity(Intent.createChooser(intent, "Share URL"))
    }
}

actual fun getUrlSharer(): UrlSharer = AndroidUrlSharer()
actual fun getDevice(): String = "android"

actual fun getVersion(): String = "1.0.0"

actual fun getStoreUrl(): String = "https://play.google.com/store/apps/details?id=com.gurtam.wialon_client&hl=en"

actual typealias CommonParcelize = Parcelize

actual typealias CommonParcelable = Parcelable