package com.govno228.pon;

public class CalendarElement {
    public int date;
    public boolean visible;
    public boolean dayOff = false;

    public CalendarElement(int date, boolean selected) {
        this.date = date;
        this.selected = selected;
    }

    public CalendarElement(int date, boolean visible, boolean dayOff, boolean selected) {
        this.date = date;
        this.visible = visible;
        this.dayOff = dayOff;
        this.selected = selected;
    }

    public boolean selected = false;

    public CalendarElement(int date, boolean visible, boolean dayOff) {
        this.date = date;
        this.visible = visible;
        this.dayOff = dayOff;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDayOff() {
        return dayOff;
    }

    public void setDayOff(boolean dayOff) {
        this.dayOff = dayOff;
    }
}
