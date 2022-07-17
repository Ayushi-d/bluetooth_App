package com.example.f1bleapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.f1bleapp.Model.CourseModel;
import com.example.f1bleapp.R;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<CourseModel> {
    public GridAdapter(@NonNull Context context, ArrayList<CourseModel> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.customcart, parent, false);
        }
        CourseModel courseModel = getItem(position);
        ImageView imggridlist = listitemView.findViewById(R.id.imggridlist);
        imggridlist.setImageResource(courseModel.getImgid());
        return listitemView;
    }
}
