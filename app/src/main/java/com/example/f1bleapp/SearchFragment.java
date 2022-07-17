package com.example.f1bleapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.f1bleapp.Adapter.GridAdapter;
import com.example.f1bleapp.Adapter.Homehorizontallist;
import com.example.f1bleapp.Adapter.SearchAdapter;
import com.example.f1bleapp.Adapter.Searchhorizontaladapter;
import com.example.f1bleapp.Adapter.VideoAdapter;
import com.example.f1bleapp.Model.CourseModel;
import com.example.f1bleapp.Model.SearchModel;
import com.example.f1bleapp.Model.VideoModel;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    AutoCompleteTextView autoCompleteTextView;
    String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};

    RecyclerView cardsrecyclearitemsearch;
    RecyclerView verticallist;
    ArrayList<String> source;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    Searchhorizontaladapter searchhorizontaladapter;
    LinearLayoutManager HorizontalLayout;
    View ChildView;
    int RecyclerViewItemPosition;
    private SearchAdapter mAdapter;
    private List<SearchModel> searchList = new ArrayList<>();

    RecyclerView gridlistitems;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);

        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.select_dialog_item, fruits);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);

        //hori
        cardsrecyclearitemsearch = view.findViewById(R.id.cardsrecyclearitemsearch);
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        cardsrecyclearitemsearch.setLayoutManager(RecyclerViewLayoutManager);
        AddItemsToRecyclerViewArrayList();
        searchhorizontaladapter = new Searchhorizontaladapter(source);
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        cardsrecyclearitemsearch.setLayoutManager(HorizontalLayout);
        cardsrecyclearitemsearch.setAdapter(searchhorizontaladapter);


        autoCompleteTextView.setTextColor(Color.WHITE);
        autoCompleteTextView.setHintTextColor(Color.LTGRAY);
        autoCompleteTextView.setHighlightColor(Color.WHITE);
        autoCompleteTextView.setLinkTextColor(Color.WHITE);

        //grid
        gridlistitems =view.findViewById(R.id.gridlistitems);
        ArrayList<CourseModel> courseModelArrayList = new ArrayList<CourseModel>();
        for (int i= 0 ; i <= 20; i++){
            courseModelArrayList.add(new CourseModel( R.drawable.car));
        }

        GridAdapter gridadapter = new GridAdapter(getContext(), courseModelArrayList);

        //gridlistitems.setAdapter(gridadapter);
        mAdapter = new SearchAdapter(searchList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        GridLayoutManager gridLayoutManager =  new GridLayoutManager(getActivity(),2);
        GridLayoutManager glm = new GridLayoutManager(getContext(), 6);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (searchList.get(position).getViewtype() == 0){
                    return 3;
                }else{
                    return 2;
                }
            }
        });
        gridlistitems.setLayoutManager(glm);
        gridlistitems.setItemAnimator(new DefaultItemAnimator());
        gridlistitems.setAdapter(mAdapter);

        prepareMovieData();
        return view;
    }

    private void prepareMovieData() {

        SearchModel search = new SearchModel("1.6M Views", R.drawable.car1, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("1.6M Views", R.drawable.car2, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("1.6M Views", R.drawable.car1, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("1.6M Views", R.drawable.car2, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("1.6M Views", R.drawable.car4, "kjhabbkbdbcdablabcjdsacdasc",1);
        searchList.add(search);

        search = new SearchModel("1.6M Views", R.drawable.car5, "kjhabbkbdbcdablabcjdsacdasc",1);
        searchList.add(search);

        search = new SearchModel("1.6M Views", R.drawable.car6, "kjhabbkbdbcdablabcjdsacdasc",1);
        searchList.add(search);

        search = new SearchModel("1.6M Views", R.drawable.car1, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("1.6M Views", R.drawable.car2, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("1.6M Views", R.drawable.car1, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("1.6M Views", R.drawable.car2, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("1.6M Views", R.drawable.car1, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);

        search = new SearchModel("1.6M Views", R.drawable.car2, "kjhabbkbdbcdablabcjdsacdasc",0);
        searchList.add(search);
    }

    public void AddItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
        source = new ArrayList<>();
        source.add("WRC");
        source.add("FORMULA 1");
        source.add("MOTO GP");
        source.add("SUPERCAR");
    }
}