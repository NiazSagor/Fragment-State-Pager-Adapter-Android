package com.angik.collectiondemofragment;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyTask extends AsyncTask<Void, Void, Void> {

    private static String DB_ADDRESS = "https://collectiondemofragment.firebaseio.com/";

    Handler handler = new Handler();

    ItemDetailCallback callback;
    String mNode;

    MyTask(){

    }

    MyTask(ItemDetailCallback back, String node){
        this.callback = back;
        this.mNode = node;
    }

    List<ResItemDetailClass> list = new ArrayList<>();

    DemoObjectFragment fragment = new DemoObjectFragment();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        handler.post(new Runnable() {
            @Override
            public void run() {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(DB_ADDRESS).child("Bellpepper").child(mNode);

                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        ResItemDetailClass detailClass = dataSnapshot.getValue(ResItemDetailClass.class);
                        list.add(detailClass);
                        callback.onItemDetailCallback(list);
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
                });
            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
