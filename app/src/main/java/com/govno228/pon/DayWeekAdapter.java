package com.govno228.pon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class DayWeekAdapter extends ArrayAdapter {
    ArrayList<DayWeekElement> elements;
    Context context;
    int resource;

    public DayWeekAdapter(@NonNull Context context, int resource, ArrayList<DayWeekElement> elements) {
        super(context, resource);
        this.context = context;
        this.elements = elements;
        this.resource = resource;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DayWeekElement element = getItem(position);
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }
        TextView textView = view.findViewById(R.id.text1);
        assert element != null;
        textView.setText(element.text);
        if (element.isWeekend) {
            textView.setTextColor(com.google.android.material.R.color.primary_dark_material_dark);
        }
        return view;
    }

    @Nullable
    @Override
    public DayWeekElement getItem(int position) {
        return elements.get(position);
    }

    @Override
    public int getCount() {
        return elements.size();
    }
}
