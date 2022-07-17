package com.example.f1bleapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.f1bleapp.Adapter.Homehorizontallist;
import com.example.f1bleapp.Adapter.VideoAdapter;
import com.example.f1bleapp.Model.StoryModel;
import com.example.f1bleapp.Model.VideoModel;
import com.example.f1bleapp.UI.Fragments.BleDeviceFragment;
import com.example.f1bleapp.UI.Fragments.VideoFragment;
import com.example.f1bleapp.UI.VideoActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView cardsrecyclearitemhome;
    RecyclerView verticallist;
    ArrayList<StoryModel> source;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    Homehorizontallist adapter;
    LinearLayoutManager HorizontalLayout;
    View ChildView;
    int RecyclerViewItemPosition;
    SharedPreferences sharedPreferences;


    //verticl
    private List<VideoModel> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private VideoAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        cardsrecyclearitemhome =view.findViewById(R.id.cardsrecyclearitemhome);
        verticallist =view.findViewById(R.id.verticallist);
        sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        cardsrecyclearitemhome.setLayoutManager(RecyclerViewLayoutManager);
        AddItemsToRecyclerViewArrayList();
        adapter = new Homehorizontallist(source);
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cardsrecyclearitemhome.setLayoutManager(HorizontalLayout);
        cardsrecyclearitemhome.setAdapter(adapter);
        adapter.setOnItemClickListener(new Homehorizontallist.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("SOME_BUNDLE_KEY","").isEmpty()){
                    Toast.makeText(getActivity(), "Error: Please Select Excel File first", Toast.LENGTH_SHORT).show();
                }else if(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("CONNECTED_DEVICE","").isEmpty()){
                    Toast.makeText(getActivity(), "Error: Please Connect Bluetooth Device  first", Toast.LENGTH_SHORT).show();
                }else if(sharedPreferences.getString("uri","").isEmpty()){
                    Toast.makeText(getActivity(), "Error: Please Select Any Video File", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    startActivity(intent);
                }

            }
        });
        //veritcal
        recyclerView = (RecyclerView) view.findViewById(R.id.verticallist);

        mAdapter = new VideoAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new Homehorizontallist.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("SOME_BUNDLE_KEY","").isEmpty()){
                    Toast.makeText(getActivity(), "Error: Please Select Excel File first", Toast.LENGTH_SHORT).show();
                }else if(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("CONNECTED_DEVICE","").isEmpty()){
                    Toast.makeText(getActivity(), "Error: Please Connect Bluetooth Device  first", Toast.LENGTH_SHORT).show();
                }else if(sharedPreferences.getString("uri","").isEmpty()){
                    Toast.makeText(getActivity(), "Error: Please Select Any Video File", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    startActivity(intent);
                }

            }
        });
        prepareMovieData();
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

    private void prepareMovieData() {
        VideoModel movie = new VideoModel("mercedeessamgf1", R.drawable.roundmer, R.drawable.headerimage);
        movieList.add(movie);

        movie = new VideoModel("ferrari", R.drawable.profilepic , R.drawable.headerimage);
        movieList.add(movie);

    }
    }