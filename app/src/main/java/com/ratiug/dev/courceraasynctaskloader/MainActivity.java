package com.ratiug.dev.courceraasynctaskloader;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Integer> {

    private static final String KEY_LOAD_START = "KEY_LOAD_START";
    private static final int LOADER_ID = 111;
    private static final String TAG = "DBG | MainActivity | ";
    private Button mStartBtn;
    private ProgressBar mProgressBar;
    private TextView mProgressText;
    private Loader mLoader;
    private boolean mLoadStart = false;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(KEY_LOAD_START, mLoadStart);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartBtn = findViewById(R.id.btnStart);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressText = findViewById(R.id.tvProgress);
        if (savedInstanceState != null) {
            mLoadStart = savedInstanceState.getBoolean(KEY_LOAD_START);
        }
        initLoader();
    }

    private void initLoader() {

        if (getLoaderManager().getLoader(LOADER_ID) != null && mLoadStart) {
            setLoaderIsStartedState();
        }
        mLoader = getLoaderManager().initLoader(LOADER_ID, null, this);
        Log.d(TAG, "initLoader");
    }

    private void setLoaderIsStartedState() {
        mProgressBar.setVisibility(View.VISIBLE);
        mStartBtn.setEnabled(false);
        mProgressText.setText(R.string.loading);
        Log.d(TAG, "setLoaderIsStartedState");
    }

    @Override
    protected void onResume() {
        super.onResume();

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadStart = true;
                setLoaderIsStartedState();

                if (mLoader.isStarted()) {
                    mLoader = getLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);
                }

                mLoader.forceLoad();
            }
        });
        Log.d(TAG, "onResume");
    }

    @Override
    public Loader<Integer> onCreateLoader(int id, Bundle args) {
        Loader<Integer> loader = null;

        if (id == LOADER_ID) {
            loader = new ProgressLoader(this);
        }
        Log.d(TAG, "onCreateLoader");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Integer> loader, Integer data) {
        Log.d(TAG, "onLoadFinished");
        mProgressBar.setVisibility(View.GONE);
        mStartBtn.setEnabled(true);
        mLoadStart = false;
        mProgressText.setText(R.string.ready);
    }

    @Override
    public void onLoaderReset(Loader<Integer> loader) {
        Log.d(TAG, "onLoaderReset");
//        mProgressBar.setVisibility(View.GONE);
//        mStartBtn.setEnabled(true);
//        mProgressText.setText(R.string.error);
    }
}
