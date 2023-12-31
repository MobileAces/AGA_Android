package com.aga.presentation.base

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.aga.presentation.base.Constants.PREF_NAME

object PrefManager {

    private var pref: SharedPreferences? = null

    fun init(context: Context) {
        if (pref == null) pref =
            context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE)
    }

    // String
    fun read(key: String?, defValue: String?): String? {
        return pref!!.getString(key, defValue)
    }

    fun write(key: String?, value: String?) {
        val prefsEditor = pref!!.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }


    // Boolean
    fun read(key: String?, defValue: Boolean): Boolean {
        return pref!!.getBoolean(key, defValue)
    }

    fun write(key: String?, value: Boolean) {
        val prefsEditor = pref!!.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.apply()
    }


    // Int
    fun read(key: String?, defValue: Int): Int {
        return pref!!.getInt(key, defValue)
    }

    fun write(key: String?, value: Int?) {
        val prefsEditor = pref!!.edit()
        prefsEditor.putInt(key, value!!).apply()
    }

    fun clear(){
        pref!!.edit().clear().commit()
    }
}