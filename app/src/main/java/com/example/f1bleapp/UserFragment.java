package com.example.f1bleapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.f1bleapp.Adapter.GridAdapter;
import com.example.f1bleapp.Adapter.Homehorizontallist;
import com.example.f1bleapp.Adapter.SearchAdapter;
import com.example.f1bleapp.Adapter.Userhorizontallist;
import com.example.f1bleapp.Model.CourseModel;
import com.example.f1bleapp.Model.SearchModel;
import com.example.f1bleapp.Model.StoryModel;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {

    RecyclerView cardsrecyclearitemuser;
    RecyclerView verticallist;
    ArrayList<StoryModel> source;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    Userhorizontallist adapter;
    LinearLayoutManager HorizontalLayout;
    View ChildView;
    RecyclerView gridlistitems;
    int RecyclerViewItemPosition;
    private SearchAdapter mAdapter;
    private List<SearchModel> searchList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);

        cardsrecyclearitemuser =view.findViewById(R.id.cardsrecyclearitemuser);
        verticallist =view.findViewById(R.id.verticallist);
        gridlistitems =view.findViewById(R.id.gridlistitems);

        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        cardsrecyclearitemuser.setLayoutManager(RecyclerViewLayoutManager);
        AddItemsToRecyclerViewArrayList();
        adapter = new Userhorizontallist(source);
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cardsrecyclearitemuser.setLayoutManager(HorizontalLayout);
        cardsrecyclearitemuser.setAdapter(adapter);

        //grid




//        GridAdapter adapter = new GridAdapter(getContext(), courseModelArrayList);
//        gridlistitems.setAdapter(adapter);
        mAdapter = new SearchAdapter(searchList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager =  new GridLayoutManager(getActivity(),2);
        gridlistitems.setLayoutManager(gridLayoutManager);
        gridlistitems.setItemAnimator(new DefaultItemAnimator());
        gridlistitems.setAdapter(mAdapter);
        perpareData();
        return view;
    }
    public void AddItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
        source = new ArrayList<>();
        StoryModel storyModel = new StoryModel("Pic 1",R.drawable.car);
        source.add(storyModel);

        storyModel =  new StoryModel("Pic 2",R.drawable.storycar1);
        source.add(storyModel);

        storyModel =  new StoryModel("Pic 3",R.drawable.storycar2);
        source.add(storyModel);

        storyModel =  new StoryModel("Pic 4",R.drawable.storycar3);
        source.add(storyModel);

        storyModel =  new StoryModel("Pic 5",R.drawable.storycar4);
        source.add(storyModel);
    }

    public void perpareData(){
        SearchModel search = new SearchModel("", R.drawable.car, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("", R.drawable.usercar1, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("", R.drawable.usercar2, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("", R.drawable.usercar3, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("", R.drawable.usercar4, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("", R.drawable.car, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("", R.drawable.usercar3, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("", R.drawable.car, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);


    }
}