package com.dumitrachecristian.petapp.utils


import android.content.Context
import com.dumitrachecristian.petapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Utils @Inject constructor(@ApplicationContext private val context: Context) {

    fun getBackgroundImageBasedOnSpecies(species: String): Int {
        return if (species.contains(context.getString(R.string.cat))) {
            R.drawable.catplaceholder
        } else if (species.contains(context.getString(R.string.dog))) {
            R.drawable.dogplaceholder
        } else {
            R.drawable.animalsplaceholder
        }
    }

    companion object {
        fun getLocationPermissions(): List<String> {
            return listOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }
}