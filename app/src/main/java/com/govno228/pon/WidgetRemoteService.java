package com.govno228.pon;

import static com.govno228.pon.CalendarManager.getCalendar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.govno228.pon.CalendarElement;
import com.govno228.pon.R;

import java.util.ArrayList;
import java.util.List;

public class WidgetRemoteService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CalendarWidgetFactory(this);
    }

    private class CalendarWidgetFactory implements RemoteViewsFactory {

        private Context context;
        private List<CalendarElement> calendarElementList;  // Data for the widget

        public CalendarWidgetFactory(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate() {
            // Here, you can fetch data for the calendar elements from your source (e.g., database, API)
            // This could involve similar logic to how you populate `calendarElementList` in your adapter
            calendarElementList = loadData();
        }

        @Override
        public void onDataSetChanged() {
            calendarElementList = loadData();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return calendarElementList.size();
        }

        private List<CalendarElement> loadData() {
            // Implement data loading logic here
            // (This example doesn't implement actual data loading)
            calendarElementList = getCalendar(System.currentTimeMillis());
            return calendarElementList;
        }

        // Implement other methods from RemoteViewsFactory as needed (getCount, getViewAt, etc.)

        @Override
        public RemoteViews getViewAt(int position) {
            Log.println(Log.INFO, "Pidor", "хуй");
            CalendarElement element = calendarElementList.get(position);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_calendar_element);

            if (element.visible) {
                remoteViews.setTextViewText(R.id.dateText, String.valueOf(element.date));
                Log.println(Log.INFO, "Pidor", "Абобо");
                remoteViews.setViewVisibility(R.id.frame, View.VISIBLE); // Make it gone if not selected
            } else {
                remoteViews.setViewVisibility(R.id.main, View.INVISIBLE);
            }

            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        // Implement remaining methods (getCount, getItemId, hasStableIds, onDataSetChanged, onDestroy)
    }
}