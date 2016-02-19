package com.thetonrifles.downloadfile;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFragment extends Fragment {

    private String mUrl;
    private Callback mCallback;

    public static DownloadFragment newInstance() {
        return new DownloadFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            mCallback = (Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public void executeDownload(String url) {
        // download file with async task and use callback
        // for providing data to parent activity
        DownloadFileTask task = new DownloadFileTask();
        task.execute(url);
    }

    /**
     * Async task for downloading file.
     */
    private class DownloadFileTask extends AsyncTask<String,Integer,Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mCallback != null) {
                mCallback.onPrepare();
            }
        }

        @Override
        protected Object doInBackground(String... urls) {
            mUrl = urls[0];
            Object response;
            InputStream input = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(mUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                // successful response?
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    // returning negative response details
                    response = new Exception("http " + connection.getResponseCode()
                            + " " + connection.getResponseMessage());
                }
                // getting file length for publishing download progress
                int fileLength = connection.getContentLength();
                input = new BufferedInputStream(connection.getInputStream());
                // building json string from stream
                StringBuilder sb = new StringBuilder();
                long total = 0;
                int b;
                while ((b = input.read()) != -1) {
                    if (isCancelled()) {
                        input.close();
                        response = new Exception("cancelled by user");
                    }
                    total += 1;
                    sb.append((char) b);
                    // publishing progress if
                    // file length is known
                    if (fileLength > 0) {
                        int progress = (int) (total * 100 / fileLength);
                        publishProgress(progress);
                    }
                }
                response = sb.toString();
            } catch (Exception ex) {
                response = ex;
            } finally {
                try {
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progress = values[0];
            if (mCallback != null) {
                mCallback.onProgress(progress);
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (result instanceof String) {
                if (mCallback != null) {
                    String content = (String) result;
                    FileStorage.getInstance().writeFile(getContext(), mUrl, content);
                    mCallback.onDownloadCompleted(content);
                }
            } else {
                if (mCallback != null) {
                    mCallback.onDownloadFailed((Exception) result);
                }
            }
        }

    }

    public interface Callback {

        void onPrepare();

        void onProgress(int percentage);

        void onDownloadCompleted(String content);

        void onDownloadFailed(Exception ex);

    }

}
