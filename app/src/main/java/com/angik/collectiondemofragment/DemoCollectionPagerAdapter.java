package com.angik.collectiondemofragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {


    private static String DB_ADDRESS = "https://collectiondemofragment.firebaseio.com/";

    private static String TAG = "From adapter class";

    private int tabCount = 0;

    private List<String> tabNames = new ArrayList<>();

    private boolean doNotifyDataSetChangedOnce = false;

    private DatabaseReference databaseReference;

    private WeakReference<Activity> weakReference;

    DemoCollectionPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);

        weakReference = new WeakReference<>(activity);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(DB_ADDRESS).child("Bellpepper");

        new LoadData().execute();
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = new DemoObjectFragment();
        Bundle args = new Bundle();

        //args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);

        args.putString(DemoObjectFragment.ARG_OBJECT, tabNames.get(i));

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {

        if (doNotifyDataSetChangedOnce) {
            doNotifyDataSetChangedOnce = false;
            notifyDataSetChanged();
        }

        return tabCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames.get(position);
    }

    public class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            newReadFromDatabase(new DatabaseCallback() {
                @Override
                public void onCallback(long childrenCount) {
                    tabCount = (int) childrenCount;
                    doNotifyDataSetChangedOnce = true;

                    publishProgress();
                    notifyDataSetChanged();

                    final Activity activity = weakReference.get();

                    if (activity == null || activity.isFinishing()) {
                        return;
                    }

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.findViewById(R.id.progressBar).setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onCallBackNames(List<String> tabNamesFromDatabase) {
                    tabNames = tabNamesFromDatabase;
                }
            });

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            final Activity activity = weakReference.get();

            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void newReadFromDatabase(final DatabaseCallback callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot dp : dataSnapshot.getChildren()) {
                        String name = dp.getKey();
                        tabNames.add(name);
                    }

                    callback.onCallback(dataSnapshot.getChildrenCount());
                    callback.onCallBackNames(tabNames);
                } else {
                    Log.d("hello", "onDataChange: no data");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("HELLO", "onCancelled: " + databaseError.getMessage());
            }
        });
    }
}
