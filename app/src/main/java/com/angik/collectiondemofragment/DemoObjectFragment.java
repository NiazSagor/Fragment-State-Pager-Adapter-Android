package com.angik.collectiondemofragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DemoObjectFragment extends Fragment implements DatabaseCallback {

    private static String DB_ADDRESS = "https://collectiondemofragment.firebaseio.com/";

    private static final String TAG = "DemoObjectFragment";

    Bundle bundle;

    DatabaseReference databaseReference;

    ChildEventListener childEventListener;

    public final static String ARG_OBJECT = "TAB_NAME";

    private RecyclerView recyclerView;

    private MyAdapter adapter;

    private List<RestaurantDetailClass> collectionRes;

    private ProgressBar middleProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        bundle = getArguments();

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(DB_ADDRESS).child("Bellpepper").child(bundle.getString(ARG_OBJECT));

        middleProgressBar = view.findViewById(R.id.progressBarMiddle);

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        //new LoadData().execute();

        new MyTask(new ItemDetailCallback() {
            @Override
            public void onItemDetailCallback(final List<ResItemDetailClass> itemCollection) {
                adapter = new MyAdapter(itemCollection);
                recyclerView.setAdapter(adapter);

                middleProgressBar.setVisibility(View.GONE);

            }
        }, bundle.getString(ARG_OBJECT)).execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        detachListener();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void attachListener(final ResDetailCallback callback) {

        collectionRes = new ArrayList<>();

        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    RestaurantDetailClass detailClass = dataSnapshot.getValue(RestaurantDetailClass.class);
                    collectionRes.add(detailClass);

                    callback.onResDetailCallback(collectionRes);

                    middleProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            databaseReference.addChildEventListener(childEventListener);
        }
    }

    private void detachListener() {
        if (childEventListener != null) {
            databaseReference.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    @Override
    public void onCallback(long childrenCount) {

    }

    @Override
    public void onCallBackNames(List<String> tabNamesFromDatabase) {
    }
}
