package com.aga.presentation.alarm

import android.widget.ToggleButton
import com.aga.presentation.databinding.LayoutSelectWeekdayBinding
import java.time.DayOfWeek

/**
 * 본 클래스는 res/layout/layout_select_weekday.xml 을 다루기 위한 클래스입니다.
 *
 *
 *
 * @param binding 본 클래스를 활용할 레이아웃에 포함 된 요일 선택 레이아웃
 * @param dayClickListener 각 요일을 클릭할 때 마다 실행 될 리스너
 */
class WeekDaySelectManager(
    private val binding: LayoutSelectWeekdayBinding,
    private var dayClickListener: (weekday: DayOfWeek,isOn: Boolean)->Unit = {_,_->}
) {

    private val weekdayViewList: List<ToggleButton> = listOf(
        binding.toggleMonday,
        binding.toggleTuesday,
        binding.toggleWednesday,
        binding.toggleThursday,
        binding.toggleFriday,
        binding.toggleSaturday,
        binding.toggleSunday
    )

    private val dayOfWeekList: List<DayOfWeek> = listOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY
    )

    init {
        applySetClickListener()
    }

    /**
     * 선택 된 요일 반환
     */
    fun getSelectedDay(): Set<DayOfWeek>{
        return dayOfWeekList.filterIndexed { index, dayOfWeek ->
            weekdayViewList[index].isChecked
        }.toSet()
    }

    /**
     * 선택 된 요일 설정
     */
    fun setSelectedDay(daySet: Set<DayOfWeek>){
        daySet.forEach {
            weekdayViewList[dayOfWeekList.indexOf(it)].isSelected = true
        }
    }

    /**
     * 요일 선택 리스너 설정
     */
    fun setClickListener(dayClickListener: (weekday: DayOfWeek,isOn: Boolean)->Unit = {_,_->}){
        this.dayClickListener = dayClickListener
        applySetClickListener()
    }

    fun setAllEnable(boolean: Boolean){
        weekdayViewList.forEach {
            it.isEnabled = boolean
        }
    }

    private fun applySetClickListener(){
        weekdayViewList.forEachIndexed { index, toggleButton ->
            toggleButton.setOnClickListener {
                dayClickListener(dayOfWeekList[index],it.isSelected)
            }
        }
    }
}