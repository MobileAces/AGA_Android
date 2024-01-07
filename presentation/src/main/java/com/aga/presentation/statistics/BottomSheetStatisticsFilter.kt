package com.aga.presentation.statistics

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import com.aga.presentation.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.aga.presentation.R
import com.aga.presentation.databinding.BottomsheetStatisticsFilterBinding
import com.aga.presentation.databinding.CalendarDayLayoutBinding
import com.aga.presentation.databinding.CalendarHeaderBinding
import com.aga.presentation.statistics.ContinuousSelectionHelper.getSelection
import com.aga.presentation.statistics.ContinuousSelectionHelper.isInDateBetweenSelection
import com.aga.presentation.statistics.ContinuousSelectionHelper.isOutDateBetweenSelection
import com.google.android.material.chip.Chip
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

private const val TAG = "BottomSheetStatisticsFi_AWSOME"
class BottomSheetStatisticsFilter: BottomSheetDialogFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var selectedMember = arrayListOf<String>()

    private lateinit var binding: BottomsheetStatisticsFilterBinding
    private val today = LocalDate.now()
    private var selection = DateSelection()
    private val headerDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetStatisticsFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(24)  // Adjust as needed
        val endMonth = currentMonth.plusMonths(0)  // Adjust as needed
        val daysOfWeek = daysOfWeek()

        mainViewModel.teamMemberList.value?.forEach{
            binding.cgMembers.addView(Chip(requireContext()).apply {
                text = it.userNickname
                isCheckable = true
                setChipBackgroundColorResource(R.drawable.chip_bg)
                setTextColorRes(R.drawable.chip_text_color)
                setOnCheckedChangeListener { compoundButton, b ->
                    if (b)
                        selectedMember.add(it.userNickname)

                    else
                        selectedMember.remove(it.userNickname)
                }
            })
        }
        
        binding.btnApplyFilter.setOnClickListener {
            if (binding.tvPeriodStart.text == "시작일" || binding.tvPeriodStart.text == "시작일" || selectedMember.size == 0){
                Toast.makeText(requireContext(), "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
            }else{
                mainViewModel.periodStatisticsSelectedMemberList = selectedMember
                mainViewModel.periodStatisticsStartDate = binding.tvPeriodStart.text.toString()
                mainViewModel.periodStatisticsEndDate = binding.tvPeriodEnd.text.toString()
                mainViewModel.periodStatisticsRequest.value = true
                this@BottomSheetStatisticsFilter.dismiss()
            }
        }

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

        binding.calendarPeriod.setup(startMonth, endMonth, daysOfWeek.first())
        binding.calendarPeriod.scrollToMonth(currentMonth)


    }

    private fun bindSummaryViews() {
        binding.tvPeriodStart.apply {
            if (selection.startDate != null) {
                text = headerDateFormatter.format(selection.startDate)
                setTextColorRes(R.color.black)
            } else {
                text = "시작일"
                setTextColor(Color.BLACK)
            }
        }

        binding.tvPeriodEnd.apply {
            if (selection.endDate != null) {
                text = headerDateFormatter.format(selection.endDate)
                setTextColorRes(R.color.black)
            } else {
                text = "종료일"
                setTextColor(Color.BLACK)
            }
        }

        binding.btnApplyFilter.isEnabled = selection.daysBetween != null
    }

    private fun configureBinders() {
        val clipLevelHalf = 5000
        val ctx = requireContext()
        val rangeStartBackground =
            ctx.getDrawableCompat(R.drawable.calendar_continuous_selected_bg_start).also {
                it.level = clipLevelHalf // Used by ClipDrawable
            }
        val rangeEndBackground =
            ctx.getDrawableCompat(R.drawable.calendar_continuous_selected_bg_end).also {
                it.level = clipLevelHalf // Used by ClipDrawable
            }
        val rangeMiddleBackground =
            ctx.getDrawableCompat(R.drawable.calendar_continuous_selected_bg_middle)
        val singleBackground = ctx.getDrawableCompat(R.drawable.calendar_single_selected_bg)
        val todayBackground = ctx.getDrawableCompat(R.drawable.calendar_today_bg)

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = CalendarDayLayoutBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate && !day.date.isAfter(today)) {
                        selection = getSelection(clickedDate = day.date, dateSelection = selection)
                        this@BottomSheetStatisticsFilter.binding.calendarPeriod.notifyCalendarChanged()
                        bindSummaryViews()
                    }
                }
            }
        }

        binding.calendarPeriod.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val textView = container.binding.tvDay
                val roundBgView = container.binding.vRoundBackground
                val continuousBgView = container.binding.vContinuousBackground

                textView.text = null
                roundBgView.makeInVisible()
                continuousBgView.makeInVisible()

                val (startDate, endDate) = selection

                when (data.position) {
                    DayPosition.MonthDate -> {
                        textView.text = data.date.dayOfMonth.toString()
                        when {
                            data.date.isAfter(today) -> {
                                textView.setTextColorRes(R.color.divider_gray)
                            }
                            startDate == data.date && endDate == null -> {
                                textView.setTextColorRes(R.color.white)
                                roundBgView.applyBackground(singleBackground)
                            }
                            data.date == startDate -> {
                                textView.setTextColorRes(R.color.white)
                                continuousBgView.applyBackground(rangeStartBackground)
                                roundBgView.applyBackground(singleBackground)
                            }
                            startDate != null && endDate != null && (data.date > startDate && data.date < endDate) -> {
                                textView.setTextColorRes(R.color.black)
                                continuousBgView.applyBackground(rangeMiddleBackground)
                            }
                            data.date == endDate -> {
                                textView.setTextColorRes(R.color.white)
                                continuousBgView.applyBackground(rangeEndBackground)
                                roundBgView.applyBackground(singleBackground)
                            }
                            data.date == today -> {
                                textView.setTextColorRes(R.color.blue_deep_sea)
                                roundBgView.applyBackground(todayBackground)
                            }
                            else -> textView.setTextColorRes(R.color.black)
                        }

                    }

                    DayPosition.InDate ->{
                        textView.text = data.date.dayOfMonth.toString()
                        textView.setTextColorRes(R.color.divider_gray)
                        if (startDate != null && endDate != null &&
                            isInDateBetweenSelection(data.date, startDate, endDate)
                        ) {
                            continuousBgView.applyBackground(rangeMiddleBackground)
                        }
                    }


                    DayPosition.OutDate -> {
                        textView.text = data.date.dayOfMonth.toString()
                        textView.setTextColorRes(R.color.divider_gray)
                        if (startDate != null && endDate != null &&
                            isOutDateBetweenSelection(data.date, startDate, endDate)
                        ) {
                            continuousBgView.applyBackground(rangeMiddleBackground)
                        }
                    }
                }
            }

            private fun View.applyBackground(drawable: Drawable) {
                makeVisible()
                background = drawable
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = CalendarHeaderBinding.bind(view).tvHeader
        }
        binding.calendarPeriod.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    container.textView.text = data.yearMonth.displayText()
                }
            }
    }
}