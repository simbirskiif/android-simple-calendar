package com.govno228.pon;

import java.util.ArrayList;
import java.util.Date;

public class DayWeekStaticStorage {
    public static ArrayList<DayWeekElement> get() {
        ArrayList<DayWeekElement> arrayList = new ArrayList<>();
        arrayList.add(new DayWeekElement(CalendarManager.getWeekDayShort(new Date(1711938294000L)), false));
        arrayList.add(new DayWeekElement(CalendarManager.getWeekDayShort(new Date(1712024694000L)), false));
        arrayList.add(new DayWeekElement(CalendarManager.getWeekDayShort(new Date(1712111094000L)), false));
        arrayList.add(new DayWeekElement(CalendarManager.getWeekDayShort(new Date(1712197494000L)), false));
        arrayList.add(new DayWeekElement(CalendarManager.getWeekDayShort(new Date(1712283894000L)), false));
        arrayList.add(new DayWeekElement(CalendarManager.getWeekDayShort(new Date(1712370294000L)), true));
        arrayList.add(new DayWeekElement(CalendarManager.getWeekDayShort(new Date(1712456694000L)), true));
        return arrayList;
    }
}
