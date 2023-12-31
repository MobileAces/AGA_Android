package com.aga.presentation.setting

object DateFormatUtil {

    fun formatGroupCreateDate(originalDate: String): String{
        return "${originalDate.substring(0,4 )}년 ${originalDate.substring(5, 7)}월 ${originalDate.substring(8, 10)}일"
    }
}