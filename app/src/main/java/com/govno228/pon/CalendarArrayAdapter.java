package com.govno228.pon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telecom.StatusHints;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ComplexColorCompat;

import java.util.List;

public class CalendarArrayAdapter extends ArrayAdapter<CalendarElement> {
    public List<CalendarElement> calendarElementList;
    int resource;
    Context context;

    public CalendarArrayAdapter(@NonNull Context context, int resource, List<CalendarElement> calendarElementList) {
        super(context, resource);
        this.calendarElementList = calendarElementList;
        this.context = context;
        this.resource = resource;
    }


    @Nullable
    @Override
    public CalendarElement getItem(int position) {
        return calendarElementList.get(position);
    }

    @Override
    public int getCount() {
        return calendarElementList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View Cview, @NonNull ViewGroup parent) {
        CalendarElement element = getItem(position);
        View view = Cview;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }
        TextView text = view.findViewById(R.id.dateText);
        if (element.visible) {
            text.setText(element.date + "");
            if (element.selected) {
                FrameLayout layout = view.findViewById(R.id.frame);
                layout.setBackground(context.getResources().getDrawable(R.drawable.drawable_selection));
            }
        } else {
            RelativeLayout layout = view.findViewById(R.id.main);
            layout.setVisibility(View.INVISIBLE);
        }
        return view;
    }
}
