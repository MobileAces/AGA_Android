package com.aga.app.module

import android.content.Context
import androidx.room.Room
import com.aga.data.data.AGADatabase
import com.aga.data.data.dao.AlarmDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AGADatabase {
        return Room.databaseBuilder(
            context,
            AGADatabase::class.java,
            "exchange_rate_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesAlarmDetailDao(
        agaDatabase: AGADatabase
    ): AlarmDetailDao {
        return agaDatabase.alarmDetailDao()
    }

}