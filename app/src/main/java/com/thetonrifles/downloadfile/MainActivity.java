package com.thetonrifles.downloadfile;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DownloadFragment.Callback {

    private static final String LOG_TAG = "Downloader";
    private static final String KEY_LIST = "key_list";

    private static final String FILE_URL = "https://dl.dropboxusercontent.com/u/44270891/file.txt";

    private ArrayList<FileContent> mContents = new ArrayList<>();
    private FileContentAdapter mFileContentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            List<FileContent> contents = (List<FileContent>) savedInstanceState.getSerializable(KEY_LIST);
            if (contents != null) {
                mContents.clear();
                mContents.addAll(contents);
            }
        }

        // building recyclerview
        RecyclerView rv = (RecyclerView) findViewById(R.id.lst_items);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // building adapter
        mFileContentAdapter = new FileContentAdapter(mContents);
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
        mContents.clear();
        // parsing file content
        String[] rows = content.split("\n");
        for (String row : rows) {
            mContents.add(new FileContent(row));
        }
        mFileContentAdapter.notifyItemRangeInserted(0, mContents.size());
    }

    @Override
    public void onDownloadFailed(Exception ex) {
        Log.e(LOG_TAG, ex.getMessage(), ex);
    }

}
