package com.example.f1bleapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.f1bleapp.Model.SearchModel;
import com.example.f1bleapp.Model.VideoModel;
import com.example.f1bleapp.R;

import org.w3c.dom.Text;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchModel> searchList;
    private static Homehorizontallist.ClickListener mOnClickListener;

    public static final int ITEM_TYPE_ONE = 0;
    public static final int ITEM_TYPE_TWO = 1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imggridlist;
        TextView viewsText;
        public MyViewHolder(View view) {
            super(view);
           imggridlist = view.findViewById(R.id.imggridlist);
            viewsText = view.findViewById(R.id.viewsText);

        }
    }


    public class MyVerticalHolder extends RecyclerView.ViewHolder {
        public ImageView imgVerticallLst;
        public MyVerticalHolder(View view) {
            super(view);
            imgVerticallLst = view.findViewById(R.id.imgVerticallLst);
        }
    }

    public SearchAdapter(List<SearchModel> searchList) {
        this.searchList = searchList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == ITEM_TYPE_ONE){
             view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_item_horizontal, parent, false);
            return new MyViewHolder(view);
        }else if (viewType == ITEM_TYPE_TWO) {
             view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_item_vertical, parent, false);
            return new MyVerticalHolder(view);
        }else{
            return  null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (searchList.get(position).getViewtype() == 0) {
            return ITEM_TYPE_ONE;
        } else {
            return ITEM_TYPE_TWO;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);
        if (itemType == ITEM_TYPE_ONE) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            SearchModel search = searchList.get(position);
            viewHolder.imggridlist.setImageResource(search.getImage());
            viewHolder.viewsText.setText(search.getViewCount());
        }else {
            MyVerticalHolder viewHolder = (MyVerticalHolder) holder;
            SearchModel search = searchList.get(position);
            ((MyVerticalHolder) holder).imgVerticallLst.setImageResource(search.getImage());
        }


//        holder.imggridlist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOnClickListener.onItemClick(position,v);
//            }
       // });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(Homehorizontallist.ClickListener clickListener) {
        this.mOnClickListener = clickListener;
    }
}
