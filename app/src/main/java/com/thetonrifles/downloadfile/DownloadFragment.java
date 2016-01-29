package com.thetonrifles.downloadfile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class DownloadFragment extends Fragment {

    private Callback mCallback;

    public static DownloadFragment newInstance() {
        return new DownloadFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // download file with async task
        // and use callback for providing
        // data to parent activity
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface Callback {

        void onDownloadCompleted();

    }

}
