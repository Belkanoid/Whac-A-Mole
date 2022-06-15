package com.belkanoid.whac_a_mole.common

import android.content.Context
import android.preference.PreferenceManager

object SharedPreferences {

    fun getStoredQuery(context: Context): Int {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getInt(Constants.score, 0)
    }

    fun setStoredQuery(context: Context, maxScore : Int) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putInt(Constants.score, maxScore)
            .apply()
    }
}