package com.example.f1bleapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.f1bleapp.Model.StoryModel;
import com.example.f1bleapp.Model.VideoModel;
import com.example.f1bleapp.R;
import com.example.f1bleapp.UI.Fragments.BleDeviceFragment;

import java.util.List;


public class Homehorizontallist extends RecyclerView.Adapter<Homehorizontallist.MyView> {

    private List<StoryModel> list;
    private Context context;
    private static ClickListener mOnClickListener;


    public class MyView
            extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public MyView(View view)
        {
            super(view);
            textView = view.findViewById(R.id.textview);
            imageView = view.findViewById(R.id.storyimg);


            // initialise TextView with id
        }
    }

    public Homehorizontallist(List<StoryModel> horizontalList)
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
                .inflate(R.layout.homecustomlisthorizontal,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder,
                                 @SuppressLint("RecyclerView") final int position)
    {
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mOnClickListener.onItemClick(position,v);
             }
         });

        StoryModel storyModel = list.get(position);
        holder.textView.setText(storyModel.getTitle());
        holder.imageView.setImageResource(storyModel.getImage());

    }


    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.mOnClickListener = clickListener;
    }
}
