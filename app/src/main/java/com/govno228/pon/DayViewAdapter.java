package com.govno228.pon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayViewAdapter extends ArrayAdapter {
    ArrayList<Date> dates;
    Context context;
    int resource;

    public DayViewAdapter(@NonNull Context context, int resource, ArrayList<Date> dates) {
        super(context, resource);
        this.context = context;
        this.dates = dates;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date date = getItem(position);
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        title.setText(CalendarManager.getMonthAndDay(date));
        subtitle.setText(CalendarManager.getWeekDay(date));
        return view;
    }

    @Nullable
    @Override
    public Date getItem(int position) {
        return dates.get(position);
    }

    @Override
    public int getCount() {
        return dates.size();
    }
}
