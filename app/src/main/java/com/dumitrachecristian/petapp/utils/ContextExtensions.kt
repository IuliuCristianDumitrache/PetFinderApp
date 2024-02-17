package com.dumitrachecristian.petapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openGoogleMaps(address: String) {
    try {
        val uri = Uri.parse("geo:0,0?q=$address")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    } catch (exception: Exception) {

    }
}

fun Context.openEmail(email: String) {
    try {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            putExtra(Intent.EXTRA_SUBJECT, "")
        }
        startActivity(emailIntent)
    } catch (exception: Exception) {

    }
}

fun Context.openPhone(phone: String) {
    try {
        val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        startActivity(phoneIntent)
    } catch (exception: Exception) {

    }
}