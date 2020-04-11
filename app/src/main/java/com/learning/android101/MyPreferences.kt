package com.learning.android101

import android.content.Context

object MyPreferences {

    val PREFERENCE_NAME = "MySharedPreference"
    val PREFERENCE_SHOWGRID = "CAL_SHOWGRID"
    val PREFERENCE_ISSUNDAYFIRST = "CAL_ISSUNDAYFIRST"

    val preferences = MyApp.instance.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    // Calendar preferences
    fun getShowGrid() : Boolean {
        return preferences.getBoolean(PREFERENCE_SHOWGRID, false)
    }

    fun setShowGrid(showGrid: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(PREFERENCE_SHOWGRID, showGrid)
        editor.apply()
    }

    fun getIsSundayFirst() : Boolean {
        return preferences.getBoolean(PREFERENCE_ISSUNDAYFIRST, true)
    }

    fun setIsSundayFirst(isSundayFirst: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(PREFERENCE_ISSUNDAYFIRST, isSundayFirst)
        editor.apply()
    }
}