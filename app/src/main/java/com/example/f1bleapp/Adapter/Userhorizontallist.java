package com.example.f1bleapp.Adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.f1bleapp.Model.StoryModel;
import com.example.f1bleapp.R;

import java.util.List;


public class Userhorizontallist extends RecyclerView.Adapter<Userhorizontallist.MyView> {

    private List<StoryModel> list;


    public class MyView
            extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public MyView(View view)
        {
            super(view);
            imageView = view.findViewById(R.id.img_list);
            // initialise TextView with id

        }
    }

    public Userhorizontallist(List<StoryModel> horizontalList)
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
                .inflate(R.layout.horizontaluserlist,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder,
                                 final int position)
    {

        StoryModel storyModel = list.get(position);
        holder.imageView.setImageResource(storyModel.getImage());

    }


    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
