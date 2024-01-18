package com.aga.data.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.aga.data.data.model.alarmdetail.AlarmDetailEntity

@Dao
interface AlarmDetailDao {
    @Insert
    suspend fun insertAlarmDetail(alarmDetailEntity: AlarmDetailEntity)

    @Query("SELECT * FROM alarm_detail_table WHERE id = (:id)")
    suspend fun getAlarmDetailById(id: Int): AlarmDetailEntity?

    @Query("SELECT * FROM alarm_detail_table WHERE userId = (:userId)")
    suspend fun getAlarmDetailByUserId(userId: String): List<AlarmDetailEntity>

    @Query("SELECT * FROM alarm_detail_table")
    suspend fun getAllAlarmDetail(): List<AlarmDetailEntity>

    @Query("DELETE FROM alarm_detail_table WHERE id = (:id)")
    suspend fun deleteAlarmDetailById(id: Int)

    @Query("DELETE FROM alarm_detail_table")
    suspend fun deleteAllAlarmDetail()

    @Update
    suspend fun updateAlarmDetail(alarmDetailEntity: AlarmDetailEntity)
}