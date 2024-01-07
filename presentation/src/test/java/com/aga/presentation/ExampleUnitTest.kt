package com.aga.presentation

import com.aga.presentation.alarm.AlarmTime
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun alarmTimeTest() {
        val a1 = AlarmTime(0,10)
        val a2 = AlarmTime("오전",12,10)

        assertEquals("오전",a1.ampm)
        assertEquals(12,a1.hour_12)

        assertEquals(0,a2.hour_24)
    }
}