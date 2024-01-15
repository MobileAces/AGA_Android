package com.aga.app

import android.app.Application
import com.aga.presentation.base.AgaAlarmManager
import com.aga.presentation.base.PrefManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AgaApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        PrefManager.init(applicationContext)
        AgaAlarmManager.init(applicationContext)
    }
}