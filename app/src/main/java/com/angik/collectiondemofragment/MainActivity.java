package com.angik.collectiondemofragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Handler activityHandler = new Handler();

    private static final String TAG = "MainActivity";

    ProgressBar progressBar;

    DatabaseReference databaseReferenceUserStatus;

    DemoCollectionPagerAdapter demoCollectionPagerAdapter;

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        //new
        //this refers to the fact if one user is connected to the database right now
        databaseReferenceUserStatus = FirebaseDatabase.getInstance().getReference(".info/connected");
        userOnline();

        progressBar = findViewById(R.id.progressBar);

        viewPager = findViewById(R.id.pager);

        tabLayout = findViewById(R.id.tab_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();

        demoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager(), this);

        viewPager.setAdapter(demoCollectionPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.getTabAt(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    private void userOnline() {
        databaseReferenceUserStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean connected = dataSnapshot.getValue(Boolean.class);
                if (connected) {
                    //if one user is connected we are making change in other node to keep track of the active user
                    DatabaseReference con = FirebaseDatabase.getInstance().getReference("currentlyOnline").push();

                    con.onDisconnect().setValue(null);//and if disconnects we are removing that new entry

                    con.setValue(Boolean.TRUE);//or if connects we are setting the value to true
                } else {
                    Log.d(TAG, "user is offline");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        viewPager.setAdapter(null);

        tabLayout.setupWithViewPager(null);
    }
}