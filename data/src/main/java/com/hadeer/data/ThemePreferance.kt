package com.hadeer.data

import android.content.Context

object ThemePreferance {
    private const val PREF_VALUE = "theme_pref"
    private const val KEY = "theme"

    fun setCurrentThemeState(context: Context,  theme : String){
        val sharedPref = context.getSharedPreferences(PREF_VALUE, Context.MODE_PRIVATE)
        sharedPref.edit().putString(KEY, theme).apply()
    }

    fun getCurrentTheme(context: Context):String?{
        val sharedPref = context.getSharedPreferences(PREF_VALUE, Context.MODE_PRIVATE)
        return sharedPref.getString(KEY, null)
    }
}