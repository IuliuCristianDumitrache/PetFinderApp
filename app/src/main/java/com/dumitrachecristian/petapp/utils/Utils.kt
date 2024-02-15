package com.dumitrachecristian.petapp.utils


import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

class Utils @Inject constructor(@ApplicationContext private val context: Context) {
    fun getDayFromTimeSeconds(time: Long?): String {
        if (time == null) {
            return ""
        }
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        val date = Date()
        date.time = time * 1000
        return sdf.format(date)
    }

    fun getTimeFromTimeSeconds(time: Long?): String {
        if (time == null) {
            return ""
        }
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = Date()
        date.time = time * 1000
        return sdf.format(date)
    }

    fun getDateTimeFromTimeSeconds(time: Long?): String {
        if (time == null) {
            return ""
        }
        val sdf = SimpleDateFormat("MM-dd-HH:mm", Locale.getDefault())
        val date = Date()
        date.time = time * 1000
        return sdf.format(date)
    }

    suspend fun getAddress(latitude: Double?, longitude: Double?) : Address? {
        if (latitude == null) {
            return null
        }
        if (longitude == null) {
            return null
        }
        return suspendCancellableCoroutine { cancellableContinuation ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Geocoder(context).getFromLocation(
                    latitude, longitude, 1
                ) { list -> // Geocoder.GeocodeListener
                    list.firstOrNull()?.let { address ->
                        cancellableContinuation.resume(
                            address
                        )
                    }
                }
            } else {
                val address = Geocoder(context).getFromLocation(latitude, longitude, 1)
                cancellableContinuation.resume(address?.firstOrNull())
            }
        }
    }

    companion object {
        const val CURRENT_LOCATION = "CURRENT_LOCATION"

        fun getLocationPermissions(): Array<String> {
            return arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }
}