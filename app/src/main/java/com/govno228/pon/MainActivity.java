package com.govno228.pon;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TooManyListenersException;

public class MainActivity extends AppCompatActivity {
    ArrayList<CalendarElement> calendarElements;
    ArrayAdapter<CalendarElement> arrayAdapter;
    ArrayList<Date> dates;
    GridView calendar;
    TextView calendarTitle;
    TextView appTitle;
    ListView listView;
    DayViewAdapter dayViewAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CalendarManager.context = this;
        init();
        context = this;
    }

//    void test() {
//        SelectionStaticData.firstSelection = new Date();
//        SelectionStaticData.firstSelection.setTime(1712836524000L);
//        SelectionStaticData.secondSelection = new Date();
//        SelectionStaticData.secondSelection.setTime(1713182124000L);
//        SelectionStaticData.isSelected = true;
//    }

    public void updateCalendar() {
        if (SelectionStaticData.isSelected && SelectionStaticData.firstSelection != null && SelectionStaticData.secondSelection != null) {
            if (SelectionStaticData.firstSelection.getTime() > SelectionStaticData.secondSelection.getTime()) {
                long a = SelectionStaticData.secondSelection.getTime();
                SelectionStaticData.secondSelection = SelectionStaticData.firstSelection;
                SelectionStaticData.firstSelection = new Date(a);
            }
        }
        calendarElements = CalendarManager.getCalendar(CalendarStaticData.date.getTime());
        appTitle.setText(CalendarManager.getTitleYear(CalendarStaticData.date));
        setAdapter();
        updateListView();
    }

    public void init() {
        GridView layout = findViewById(R.id.DayWeek);
        DayWeekAdapter adapter = new DayWeekAdapter(this, R.layout.day_week_element, DayWeekStaticStorage.get());
        layout.setAdapter(adapter);
        Data.packageName = getPackageName();
        Data.context = this;
//        if(CheckLocationAccess.checkPermissions(this)){
//            AccessStaticData.isAccessGeolocation = true;
//        }else{
//            DialogNoLocationAccess access = new DialogNoLocationAccess();
//            access.show(getSupportFragmentManager(), "noLoc");
//        }
        //test();
        initListView();
        updateListView();
        calendarTitle = findViewById(R.id.calendarTitle);
        appTitle = findViewById(R.id.appTitle);
        if (CalendarStaticData.date == null) {
            CalendarStaticData.date = new Date(System.currentTimeMillis());
            calendarElements = CalendarManager.getCalendarPresentDay();
        } else {
            calendarElements = CalendarManager.getCalendar(CalendarStaticData.date.getTime());
        }
        calendar = findViewById(R.id.calendar);
        calendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MonthData data = CalendarManager.getMonthData(CalendarStaticData.date.getTime());
                Calendar cActual = Calendar.getInstance();
                cActual.setTimeInMillis(CalendarStaticData.date.getTime());
                int skipDays = data.beginWeekInMonth;
                int countDays = data.countDays;
                if (SelectionStaticData.selection == Selections.Multiply) {
                    if (position + 1 >= skipDays && position + 1 <= countDays + skipDays) {
                        if (SelectionStaticData.isSelected) {
                            resetSelection();
                            updateListView();
//                            SelectionStaticData.isSelected = false;
//                            SelectionStaticData.firstSelection = null;
//                            SelectionStaticData.secondSelection = null;
//                            updateCalendar();
                        } else if (!SelectionStaticData.isSelected && SelectionStaticData.firstSelection == null && SelectionStaticData.secondSelection == null) {
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.YEAR, cActual.get(Calendar.YEAR));
                            calendar1.set(Calendar.MONTH, cActual.get(Calendar.MONTH));
                            calendar1.set(Calendar.DAY_OF_MONTH, position - skipDays + 2);
                            SelectionStaticData.firstSelection = new Date(calendar1.getTimeInMillis());
                        } else if (!SelectionStaticData.isSelected && SelectionStaticData.firstSelection != null && SelectionStaticData.secondSelection == null) {
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.YEAR, cActual.get(Calendar.YEAR));
                            calendar1.set(Calendar.MONTH, cActual.get(Calendar.MONTH));
                            calendar1.set(Calendar.DAY_OF_MONTH, position - skipDays + 2);
                            SelectionStaticData.secondSelection = new Date(calendar1.getTimeInMillis());
                            SelectionStaticData.isSelected = true;
                            updateCalendar();
                            updateListView();
                        }
                    }
                } else {
                    //Single
                    if (position + 1 >= skipDays && position + 1 <= countDays + skipDays) {
                        if (!SelectionStaticData.isSelected) {
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.YEAR, cActual.get(Calendar.YEAR));
                            calendar1.set(Calendar.MONTH, cActual.get(Calendar.MONTH));
                            calendar1.set(Calendar.DAY_OF_MONTH, position - skipDays + 2);
                            SelectionStaticData.SingleSelection = new Date(calendar1.getTimeInMillis());
                            SelectionStaticData.isSelected = true;
                            updateCalendar();
                            updateListView();
                        } else {
                            resetSelection();
                            updateListView();
                        }
                    }
                }
            }
        });
        setAdapter();
        arrayAdapter.notifyDataSetChanged();
        AutoFormatCalendarTitle();
        initToggleButtons();
        setTodayInSelection();
    }

    public void resetSelection() {
        SelectionStaticData.isSelected = false;
        SelectionStaticData.firstSelection = null;
        SelectionStaticData.secondSelection = null;
        SelectionStaticData.selectionData = null;
        updateCalendar();
    }

    void initToggleButtons() {
        MaterialButtonToggleGroup toggleGroup = findViewById(R.id.toggleGroup);
        Button b1 = findViewById(R.id.toggleButton1);
        Button b2 = findViewById(R.id.toggleButton2);
        Context context = this;
        if (SelectionStaticData.selection == Selections.Single) {
            b2.setSelected(false);
            toggleGroup.check(R.id.toggleButton1);
            toggleGroup.uncheck(R.id.toggleButton2);
        } else {
            b1.setSelected(false);
            toggleGroup.check(R.id.toggleButton2);
            toggleGroup.uncheck(R.id.toggleButton1);
        }
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, int i, boolean b) {
                if (b) {
                    resetSelection();
                    if (i == R.id.toggleButton1) {
                        b2.setSelected(false);
                        materialButtonToggleGroup.check(R.id.toggleButton1);
                        materialButtonToggleGroup.uncheck(R.id.toggleButton2);
                        SelectionStaticData.selection = Selections.Single;
                    } else if (i == R.id.toggleButton2) {
                        b1.setSelected(false);
                        materialButtonToggleGroup.check(R.id.toggleButton2);
                        materialButtonToggleGroup.uncheck(R.id.toggleButton1);
                        SelectionStaticData.selection = Selections.Multiply;
                    }
                }
            }
        });
    }

    public void AutoFormatCalendarTitle() {
        calendarTitle.setText(CalendarManager.getMonthAndYearAuto());
        appTitle.setText(CalendarManager.getTitleYear(CalendarStaticData.date));
    }

    public void setTodayInSelection() {
        if (!SelectionStaticData.isSelected) {
            if (SelectionStaticData.selection == Selections.Single) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTimeInMillis(System.currentTimeMillis());
                SelectionStaticData.SingleSelection = new Date(calendar1.getTimeInMillis());
                SelectionStaticData.isSelected = true;
                updateCalendar();
                updateListView();
            } else if (SelectionStaticData.selection == Selections.Multiply) {
                SelectionStaticData.selection = Selections.Single;
                setTodayInSelection();
            }
        }
    }

    public void setHardTodayInSelection() {
        SelectionStaticData.selection = Selections.Single;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        SelectionStaticData.SingleSelection = new Date(calendar1.getTimeInMillis());
        SelectionStaticData.isSelected = true;
        updateCalendar();
        updateListView();
    }

    public void updateListView() {
        if (SelectionStaticData.isSelected) {
            if (SelectionStaticData.selection == Selections.Multiply) {
                if (SelectionStaticData.firstSelection != null && SelectionStaticData.secondSelection != null) {
                    ArrayList<Date> dates1 = new ArrayList<>();
                    Calendar cActual = Calendar.getInstance();
                    cActual.setTimeInMillis(CalendarStaticData.date.getTime());
                    Calendar calendar1 = Calendar.getInstance();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar1.setTimeInMillis(SelectionStaticData.firstSelection.getTime());
                    calendar1.set(Calendar.YEAR, cActual.get(Calendar.YEAR));
                    calendar1.set(Calendar.MONTH, cActual.get(Calendar.MONTH));
                    calendar2.setTimeInMillis(SelectionStaticData.secondSelection.getTime());
                    calendar2.set(Calendar.YEAR, cActual.get(Calendar.YEAR));
                    calendar2.set(Calendar.MONTH, cActual.get(Calendar.MONTH));
                    Date date = SelectionStaticData.firstSelection;
                    dates1.add(SelectionStaticData.firstSelection);
                    while (true) {
                        Calendar calendar3 = Calendar.getInstance();
                        calendar3.setTimeInMillis(date.getTime());
                        calendar3.add(Calendar.DAY_OF_MONTH, 1);
                        Calendar calendar4 = Calendar.getInstance();
                        calendar4.setTimeInMillis(SelectionStaticData.secondSelection.getTime());
                        if (calendar3.get(Calendar.DAY_OF_MONTH) >= calendar4.get(Calendar.DAY_OF_MONTH)) {
                            date = new Date(calendar3.getTimeInMillis());
                            dates1.add(date);
                            dates = dates1;
                            break;
                        } else {
                            date = new Date(calendar3.getTimeInMillis());
                            dates1.add(date);
                        }
                    }
                    dayViewAdapter = new DayViewAdapter(this, R.layout.activity_date_view_element, dates);
                    listView.setAdapter(dayViewAdapter);
                }
            } else if (SelectionStaticData.selection == Selections.Single) {
                if (SelectionStaticData.SingleSelection != null) {
                    ArrayList<Date> dates1 = new ArrayList<>();
                    dates1.add(SelectionStaticData.SingleSelection);
                    dates = dates1;
                } else dates = new ArrayList<Date>();
                dayViewAdapter = new DayViewAdapter(this, R.layout.activity_date_view_element, dates);
                listView.setAdapter(dayViewAdapter);
            }
        } else {
            dates = new ArrayList<>();
            dayViewAdapter = new DayViewAdapter(this, R.layout.activity_date_view_element, dates);
            listView.setAdapter(dayViewAdapter);
        }
    }

    public void initListView() {
        if (listView == null) {
            listView = findViewById(R.id.dayViewListView);
        } else if (dates != null) {
            dayViewAdapter = new DayViewAdapter(this, R.layout.activity_date_view_element, dates);
            listView.setAdapter(dayViewAdapter);
        }
    }

    public void setAdapter() {
        arrayAdapter = new CalendarArrayAdapter(this, R.layout.calendar_element, calendarElements);
        calendar.setAdapter(arrayAdapter);
    }

    public void nextMonthButton(View view) {
        resetSelection();
        Calendar calendarT = Calendar.getInstance();
        calendarT.setTimeInMillis(CalendarStaticData.date.getTime());
        calendarT.add(Calendar.MONTH, 1);
        Date date = new Date(calendarT.getTimeInMillis());
        CalendarStaticData.date = date;
        calendarElements = CalendarManager.getCalendar(date.getTime());
        appTitle.setText(CalendarManager.getTitleYear(CalendarStaticData.date));
        setAdapter();
        AutoFormatCalendarTitle();
    }

    public void previousMonthButton(View view) {
        resetSelection();
        Calendar calendarT = Calendar.getInstance();
        calendarT.setTimeInMillis(CalendarStaticData.date.getTime());
        calendarT.add(Calendar.MONTH, -1);
        CalendarStaticData.date = new Date(calendarT.getTimeInMillis());
        Date date = new Date(calendarT.getTimeInMillis());
        CalendarStaticData.date = date;
        calendarElements = CalendarManager.getCalendar(date.getTime());
        appTitle.setText(CalendarManager.getTitleYear(CalendarStaticData.date));
        setAdapter();
        AutoFormatCalendarTitle();
    }
}