package com.govno228.pon;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarManager {
    static Context context;

    public static MonthData getMonthData(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int countDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int startWeekInMonth;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(time);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        startWeekInMonth = calendar1.get(Calendar.DAY_OF_WEEK);
        startWeekInMonth = convertToInternationalWeek(startWeekInMonth);

        MonthData data = new MonthData(countDays, startWeekInMonth, -1, calendar);
        return data;
    }

    public static int convertToInternationalWeek(int americanWeek) {
        int i = americanWeek - 1;
        if (i == 0) i = 7;
        if (americanWeek == 7) i = 6;
        return i;

    }

    public static ArrayList<CalendarElement> getCalendar(long time) {
        ArrayList<CalendarElement> list = new ArrayList<>();
        MonthData data = getMonthData(time);
        for (int i = 1; i < data.beginWeekInMonth; i++) {
            list.add(new CalendarElement(-1, false, false));
        }
        for (int i = 1; i <= data.countDays; i++) {
            list.add(new CalendarElement(i, true, false));
        }
        return getSelections(list);
    }

    public static ArrayList<CalendarElement> getSelections(ArrayList<CalendarElement> list) {
        if (SelectionStaticData.isSelected) {
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            Calendar calendar3 = Calendar.getInstance();
            if (SelectionStaticData.selection == Selections.Multiply) {
                calendar1.setTimeInMillis(SelectionStaticData.firstSelection.getTime());
                calendar2.setTimeInMillis(SelectionStaticData.secondSelection.getTime());
            } else {
                calendar3.setTimeInMillis(SelectionStaticData.SingleSelection.getTime());
            }
            for (CalendarElement element : list) {
                if (SelectionStaticData.selection == Selections.Multiply) {
                    if ((element.date >= calendar1.get(java.util.Calendar.DAY_OF_MONTH)) && (element.date <= calendar2.get(java.util.Calendar.DAY_OF_MONTH))) {
                        element.selected = true;
                    } else element.selected = false;
                } else {
                    if (element.date == calendar3.get(Calendar.DAY_OF_MONTH)) {
                        element.selected = true;
                    } else element.selected = false;
                }
            }
        }
        return list;
    }

    public static ArrayList<CalendarElement> getCalendarPresentDay() {
        return getCalendar(System.currentTimeMillis());
    }

    public static String getMonthAndYear(Date date) {
        long mill = date.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(context.getResources().getString(R.string.MonthAndYearStringFormat));
        String result = simpleDateFormat.format(date);
        return result;
    }

    public static String getMonthAndYearAuto() {
        long mill = CalendarStaticData.date.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(context.getResources().getString(R.string.MonthAndYearStringFormat));
        String result = simpleDateFormat.format(CalendarStaticData.date);
        return result;
    }

    public static String getWeekDay(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(context.getResources().getString(R.string.DayWeekStringFormat));
        String result = simpleDateFormat.format(date);
        return result;
    }

    public static String getTitleYear(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
        String result = simpleDateFormat.format(date);
        return result;
    }

    public static String getMonthAndDay(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM");
        String result = simpleDateFormat.format(date);
        return result;
    }

    public static String getWeekDayShort(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
        String result = simpleDateFormat.format(date);
        return result;
    }
}
