package com.aga.data.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aga.data.data.dao.AlarmDetailDao
import com.aga.data.data.model.alarmdetail.AlarmDetailEntity

@Database(
    entities = [AlarmDetailEntity::class],
    version = 1
)
abstract class AGADatabase: RoomDatabase() {
    abstract fun alarmDetailDao(): AlarmDetailDao
}