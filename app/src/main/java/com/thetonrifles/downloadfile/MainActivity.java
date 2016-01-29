package com.thetonrifles.downloadfile;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.thetonrifles.downloadfile.adapter.AbstractFileAdapter;
import com.thetonrifles.downloadfile.adapter.FileV2Adapter;
import com.thetonrifles.downloadfile.parser.FileV2Item;
import com.thetonrifles.downloadfile.parser.FileV2Parser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DownloadFragment.Callback {

    private static final Object _LOCK = new Object();

    private static final String LOG_TAG = "Downloader";
    private static final String KEY_LIST = "key_list";

    private static final String FILE_URL = "https://dl.dropboxusercontent.com/u/44270891/data.txt";
    //private static final String FILE_URL = "https://dl.dropboxusercontent.com/u/44270891/file.txt";

    private ArrayList<FileV2Item> mContents = new ArrayList<>();
    private AbstractFileAdapter mFileContentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            List<FileV2Item> contents = (List<FileV2Item>) savedInstanceState.getSerializable(KEY_LIST);
            if (contents != null) {
                mContents.clear();
                mContents.addAll(contents);
            }
        }

        // building recyclerview
        RecyclerView rv = (RecyclerView) findViewById(R.id.lst_items);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // building adapter
        mFileContentAdapter = new FileV2Adapter(mContents);
        rv.setAdapter(mFileContentAdapter);

        FragmentManager fm = getSupportFragmentManager();
        DownloadFragment fragment = (DownloadFragment) fm.findFragmentByTag("download");
        if (fragment == null) {
            fragment = DownloadFragment.newInstance();
            fm.beginTransaction().add(fragment, "download").commit();
            fragment.executeDownload(FILE_URL);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sync:
                FragmentManager fm = getSupportFragmentManager();
                DownloadFragment fragment = (DownloadFragment) fm.findFragmentByTag("download");
                fragment.executeDownload(FILE_URL);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_LIST, mContents);
    }

    @Override
    public void onPrepare() {
        Log.d(LOG_TAG, "preparing file download: " + FILE_URL);
    }

    @Override
    public void onProgress(int percentage) {
        Log.d(LOG_TAG, "completed " + percentage + "%");
    }

    @Override
    public void onDownloadCompleted(String content) {
        Log.d(LOG_TAG, "download completed!");
        Log.d(LOG_TAG, content);
        synchronized (_LOCK) {
            int oldSize = mContents.size();
            mContents.clear();
            // parsing file content
            List<FileV2Item> items = (new FileV2Parser()).parse(content);
            // updating collection
            mContents.addAll(items);
            // and finally layout
            int newSize = mContents.size();
            if (oldSize == newSize) {
                mFileContentAdapter.notifyItemRangeChanged(0, newSize);
            } else if (oldSize < newSize) {
                mFileContentAdapter.notifyItemRangeChanged(0, oldSize);
                mFileContentAdapter.notifyItemRangeInserted(oldSize, newSize - oldSize);
            } else {
                mFileContentAdapter.notifyItemRangeChanged(0, newSize);
                mFileContentAdapter.notifyItemRangeInserted(newSize, oldSize - newSize);
            }
        }
    }

    @Override
    public void onDownloadFailed(Exception ex) {
        Log.e(LOG_TAG, ex.getMessage(), ex);
    }

}
