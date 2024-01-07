package com.aga.presentation.alarm


class AlarmTime private constructor(){
    var ampm: String = ""
        private set
    var hour_24: Int = 0
        private set
    var hour_12: Int = 0
        private set
    var minute: Int = 0
        private set

    // 24시간제 시간과 분을 인자로 받는 생성자
    constructor(hour_24: Int, minute: Int) : this() {
        this.hour_24 = hour_24
        this.minute = minute
        convertTo12HourFormat()
    }

    // 오전/오후, 12시간제 시간, 분을 인자로 받는 생성자
    constructor(ampm: String, hour_12: Int, minute: Int) : this() {
        this.ampm = ampm
        this.hour_12 = hour_12
        this.minute = minute
        convertTo24HourFormat()
    }

    private fun convertTo12HourFormat() {
        ampm = if (hour_24 < 12) "오전" else "오후"
        hour_12 = if (hour_24 == 0 || hour_24 == 12) 12 else hour_24 % 12
    }

    private fun convertTo24HourFormat() {
        hour_24 = if (ampm == "오전") {
            if (hour_12 == 12) 0 else hour_12
        } else {
            if (hour_12 == 12) 12 else hour_12 + 12
        }
    }
}