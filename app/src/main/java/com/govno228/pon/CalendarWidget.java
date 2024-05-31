package com.govno228.pon;

import static com.govno228.pon.CalendarManager.getCalendar;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class CalendarWidget extends AppWidgetProvider {
    Context context;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        this.context = context;

        CharSequence widgetText = getWidgetTitle(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.calendar_widget);
        views.setTextViewText(R.id.title_text_widget, widgetText);
        init(context);
        Intent intent = new Intent(context, WidgetRemoteService.class);
        views.setRemoteAdapter(R.id.calendar, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, CalendarWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            CharSequence widgetText = getWidgetTitle(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.calendar_widget);
            views.setTextViewText(R.id.title_text_widget, widgetText);
            Intent intent = new Intent(context, WidgetRemoteService.class);
            views.setRemoteAdapter(R.id.calendar, intent);
            //init(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        // Обработка событий, если необходимо
    }

    @Override
    public void onDisabled(Context context) {
        // Действия при отключении виджета, если необходимо
    }

    public String getWidgetTitle(Context context) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(context.getResources().getString(R.string.MonthAndYearStringFormat));
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }

    public void init(Context context) {
        Log.println(Log.INFO, "Pidor", "Зутшы");
        Intent intent = new Intent(context, WidgetRemoteService.class);
        //views.setRemoteAdapter(R.id.calendar, intent);
    }
}
