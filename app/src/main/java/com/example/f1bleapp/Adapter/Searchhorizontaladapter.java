package com.example.f1bleapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.f1bleapp.R;

import java.util.List;


public class Searchhorizontaladapter extends RecyclerView.Adapter<Searchhorizontaladapter.MyView> {

    private List<String> list;


    public class MyView
            extends RecyclerView.ViewHolder {

        TextView txt_horizontalxcards;

        public MyView(View view)
        {
            super(view);

            // initialise TextView with id
            txt_horizontalxcards = (TextView)view
                    .findViewById(R.id.txt_horizontalxcards);
        }
    }

    public Searchhorizontaladapter(List<String> horizontalList)
    {
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.searchcustomlisthorizontal,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder,
                                 final int position)
    {


        holder.txt_horizontalxcards.setText(list.get(position));
    }


    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
