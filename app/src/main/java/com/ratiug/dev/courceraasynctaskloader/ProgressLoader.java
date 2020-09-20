package com.ratiug.dev.courceraasynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProgressLoader extends AsyncTaskLoader<Integer> {
    private static final String TAG = "DBG | ProgressLoad | ";
    public ProgressLoader(@NonNull Context context) {
        super(context);
        Log.d(TAG, "ProgressLoader");
    }

    @Override
    protected void onStopLoading() {
        Log.d(TAG, "onStopLoading");
        super.onStopLoading();
    }



    @Override
    protected void onStartLoading() {
        Log.d(TAG, "onStartLoading");
        super.onStartLoading();
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        Log.d(TAG, "loadInBackground");
        for (int i = 1; i < 6; i++) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, "loadInBackground: sleep " + i);
        }
        Log.d(TAG, "loadInBackground: return result");
        return 1;
    }
}