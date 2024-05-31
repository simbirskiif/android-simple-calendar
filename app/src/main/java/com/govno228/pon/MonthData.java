package com.govno228.pon;


import android.icu.util.Calendar;

public class MonthData {
    public int countDays;
    public int beginWeekInMonth;
    public int endMonth;
    public Calendar calendar;

    public MonthData(int countDays, int beginWeekInMonth, int endMonth, Calendar calendar) {
        this.countDays = countDays;
        this.beginWeekInMonth = beginWeekInMonth;
        this.endMonth = endMonth;
        this.calendar = calendar;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public MonthData(int countDays, int beginWeekInMonth, int endMonth) {
        this.countDays = countDays;
        this.beginWeekInMonth = beginWeekInMonth;
        this.endMonth = endMonth;
    }

    public MonthData() {
    }

    public int getCountDays() {
        return countDays;
    }

    public void setCountDays(int countDays) {
        this.countDays = countDays;
    }

    public int getBeginWeekInMonth() {
        return beginWeekInMonth;
    }

    public void setBeginWeekInMonth(int beginWeekInMonth) {
        this.beginWeekInMonth = beginWeekInMonth;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }
}
