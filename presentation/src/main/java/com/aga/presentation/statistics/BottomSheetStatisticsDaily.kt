package com.aga.presentation.statistics


import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.aga.presentation.databinding.BottomsheetStatisticsDailyBinding
import com.aga.presentation.databinding.CalendarDayLayoutBinding
import com.aga.presentation.databinding.CalendarHeaderBinding
import com.aga.presentation.R
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class BottomSheetStatisticsDaily: BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetStatisticsDailyBinding
    private val today = LocalDate.now()
    private var selectedDate: LocalDate? = null
    private val headerDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BottomsheetStatisticsDailyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(24)  // Adjust as needed
        val endMonth = currentMonth.plusMonths(0)  // Adjust as needed
        val daysOfWeek = daysOfWeek()

        binding.btnApplyFilter.isEnabled = false

        binding.calendarWeekday.root.children.forEachIndexed { index, child ->
            (child as TextView).apply {
                text = daysOfWeek[index].displayText()
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                when (index) {
                    0 -> setTextColorRes(R.color.calendar_red)
                    6 -> setTextColorRes(R.color.calendar_blue)
                    else -> setTextColorRes(R.color.black)
                }
            }
        }

        configureBinders()

        binding.calendarDaily.setup(startMonth, endMonth, daysOfWeek.first())
        binding.calendarDaily.scrollToMonth(currentMonth)


    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.calendarDaily.notifyDateChanged(it) }
            binding.calendarDaily.notifyDateChanged(date)
            binding.tvPeriodStart.text = headerDateFormatter.format(date)
        }
        binding.btnApplyFilter.isEnabled = selectedDate != null
    }

    private fun configureBinders() {

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = CalendarDayLayoutBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate && !day.date.isAfter(today)) {
                        selectDate(day.date)
                    }
                }
            }
        }

        binding.calendarDaily.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val textView = container.binding.tvDay
                textView.text = data.date.dayOfMonth.toString()

                if (data.date.isAfter(today))
                    textView.setTextColorRes(R.color.divider_gray)
                else if (data.position == DayPosition.MonthDate) {
                    when (data.date) {
                        selectedDate -> {
                            textView.setTextColorRes(R.color.white)
                            textView.setBackgroundResource(R.drawable.calendar_single_selected_bg)

                        }
                        today -> {
                            textView.setTextColorRes(R.color.blue_deep_sea)
                            textView.setBackgroundResource(R.drawable.calendar_today_bg)
                        }

                        else -> {
                            textView.setTextColorRes(R.color.black)
                            textView.background = null
                        }
                    }
                }
                else
                    textView.setTextColorRes(R.color.divider_gray)
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = CalendarHeaderBinding.bind(view).tvHeader
        }
        binding.calendarDaily.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    container.textView.text = data.yearMonth.displayText()
                }
            }
    }
}