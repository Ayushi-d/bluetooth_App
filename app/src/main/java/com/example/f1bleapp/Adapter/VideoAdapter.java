package com.example.f1bleapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.f1bleapp.Model.VideoModel;
import com.example.f1bleapp.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private List<VideoModel> moviesList;
    private static Homehorizontallist.ClickListener mOnClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_username;
        public ImageView img_user,videoview;

        public MyViewHolder(View view) {
            super(view);
            img_user = view.findViewById(R.id.img_user);
            txt_username = (TextView) view.findViewById(R.id.txt_username);
            videoview =  view.findViewById(R.id.videoview);
        }
    }


    public VideoAdapter(List<VideoModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_media_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VideoModel movie = moviesList.get(position);
        holder.txt_username.setText(movie.getTitle());
        holder.videoview.setImageResource(movie.getHeaderimg());
        holder.img_user.setImageResource(movie.getImage());
        holder.videoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onItemClick(position,v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(Homehorizontallist.ClickListener clickListener) {
        this.mOnClickListener = clickListener;
    }
}
