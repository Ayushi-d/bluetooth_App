package com.example.f1bleapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f1bleapp.Data.Local.CsvData;
import com.example.f1bleapp.UI.Fragments.BleDeviceFragment;
import com.example.f1bleapp.UI.Fragments.CsvFragment;
import com.example.f1bleapp.UI.Fragments.VideoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@AndroidEntryPoint
public class MainActivity2 extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    ImageView imagemenu;
    Toolbar toolbar;
    DrawerLayout my_drawer_layout;
    BottomNavigationView navigation;
    int SELECT_VIDEO = 100;
    int READ_REQUEST_CODE = 123;
    TextView textureView;
    ArrayList<CsvData> messageArray = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageView hamMenu = findViewById(R.id.imagemenu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        hamMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout navDrawer = findViewById(R.id.my_drawer_layout);
                if(!navDrawer.isDrawerOpen(GravityCompat.START)) navDrawer.openDrawer(GravityCompat.START);
                else navDrawer.closeDrawer(GravityCompat.END);
            }
        });

        DrawerLayout drawer = findViewById(R.id.my_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.nav_open,
                R.string.nav_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(selectedListener);

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        HomeFragment homeFragment = new HomeFragment();
        setMyFragment(homeFragment);

        //bottom navigation

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framecon, fragment, "");
        fragmentTransaction.commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {

                        case R.id.navigation_home:
                            HomeFragment fragment = new HomeFragment();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.framecon, fragment, "");
                            fragmentTransaction.commit();
                            return true;

                        case R.id.navigation_dashboard:
                            SearchFragment fragment1 = new SearchFragment();
                            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction1.replace(R.id.framecon, fragment1);
                            fragmentTransaction1.commit();
                            return true;

                    case R.id.navigation_notifications:
                        UserFragment fragment2 = new UserFragment();
                        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.framecon, fragment2, "");
                        fragmentTransaction2.commit();
                        return true;

                    }
                    return false;
                }
            };

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.my_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.imagemenu)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.videopath)
        {
            // Home Fragment
//            VideoFragment videoFragment = new VideoFragment();
//            setMyFragment(videoFragment);
//            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent,"Choose Video"),SELECT_VIDEO);
        } else if (id == R.id.excelpath)
        {
            // Gallery Fragment
//            CsvFragment csvFragment = new CsvFragment();
//            setMyFragment(csvFragment);
            Intent intent = new Intent();
            intent.setType("text/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(intent,READ_REQUEST_CODE);
        } else if (id == R.id.bluetoothpath)
        {
             //Movies Fragment
            BleDeviceFragment bleDeviceFragment = new BleDeviceFragment();
            setMyFragment(bleDeviceFragment);
        }

        DrawerLayout drawer = findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void setMyFragment(Fragment fragment)
    {
        //get current fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //get fragment transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //set new fragment in fragment_container (FrameLayout)
        fragmentTransaction.replace(R.id.framecon, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> readCSV(Uri uri) throws IOException {
        String thisLine = null;
        InputStream csvFile = this.getContentResolver().openInputStream(uri);
        InputStreamReader isr = new InputStreamReader(csvFile);
        BufferedReader bufferedReader = new BufferedReader(isr);
        List<String> list = new ArrayList();
        while ((thisLine = bufferedReader.readLine()) != null) {
            System.out.println(thisLine);
            list.add(thisLine);
        }
        return list;
    }


    public final void setDataInArrayList(@NotNull List list, @NotNull String key) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(key, "key");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED && requestCode == SELECT_VIDEO && data.getData() != null){
            Uri videoURI;
            String[] arr = new String[]{MediaStore.Video.VideoColumns.DATA};
            if (data.getData().toString().contains("content")){
                Cursor cursor = this.getContentResolver().query(
                        data.getData(),
                        arr,
                        null,
                        null,
                        null
                );
                if (cursor.moveToFirst()) {
                    Uri myFileUri = Uri.parse(Uri.parse(cursor.getString(0)).getPath());
                    videoURI = myFileUri;


                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("uri",videoURI.toString());
                    myEdit.commit();

                    Log.d(TAG, "onActivityResult: "+sharedPreferences.getString("uri",""));
                    //playVideo(videouri)
                    cursor.close();
                }
                else {
                    videoURI = data.getData();
                }
                String selectedVideoPath = videoURI.getPath();
                File fileAttachVideo = new File(selectedVideoPath);
                Log.d(
                        "videoPath",
                        selectedVideoPath+fileAttachVideo
                );
            }
            }else if (requestCode == READ_REQUEST_CODE && resultCode == this.RESULT_OK) {

            try {
                List<String> csvData1 = readCSV(data.getData());
                for (int i =1;i<csvData1.size();i++){
                    String[] sortedReadings = csvData1.get(i).split(",");
                    if (sortedReadings.length == 13){
                        CsvData _csvdata = new CsvData(sortedReadings[0],
                                sortedReadings[1],
                                sortedReadings[2],
                                sortedReadings[3],
                                sortedReadings[4],
                                sortedReadings[5]
                                ,sortedReadings[6],
                                sortedReadings[7],
                                sortedReadings[8],
                                sortedReadings[9],
                                sortedReadings[10],
                                sortedReadings[11],
                                sortedReadings[12]);
                        messageArray.addAll(Collections.singleton(_csvdata));
                    }
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    readCSV(data.getData());
                    String as = String.join("\n",readCSV(data.getData()));
                    Log.d(
                            "csvvvvvv",as
                            );
                }
                setDataInArrayList(messageArray,"SOME_BUNDLE_KEY");


            } catch (IOException e) {
                e.printStackTrace();
                }
        }
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}


