package com.thetonrifles.downloadfile;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class FileStorage {

    private static final String LOG_TAG = "Storage";

    private static FileStorage instance;

    public static synchronized FileStorage getInstance() {
        if (instance == null) {
            instance = new FileStorage();
        }
        return instance;
    }

    public synchronized void writeFile(Context context, String url, String content) {
        File file = getTempFile(context, url);
        if (file != null) {
            FileOutputStream out = null;
            try {
                Log.d(LOG_TAG, "writing file content in cache for: " + url);
                out = new FileOutputStream(file);
                out.write(content.getBytes());
                out.close();
                writeLastUpdateTimestamp(context, new Date());
                Log.d(LOG_TAG, "written file content in cache for: " + url);
            } catch (Exception ex) {
                Log.e(LOG_TAG, ex.getMessage(), ex);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (Exception ex) {
                }
            }
        }
    }

    public synchronized String readFile(Context context, String url) {
        File file = getTempFile(context, url);
        StringBuilder sb = new StringBuilder();
        if (file != null) {
            BufferedReader br = null;
            try {
                Log.d(LOG_TAG, "reading file content from cache for: " + url);
                br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append('\n');
                }
                Log.d(LOG_TAG, "read file content from cache for: " + url);
            } catch (IOException ex) {
                Log.e(LOG_TAG, ex.getMessage(), ex);
            }
        }
        return sb.toString();
    }

    private File getTempFile(Context context, String url) {
        File file = null;
        String filename = md5(url);
        if (!TextUtils.isEmpty(filename)) {
            file = new File(context.getFilesDir(), filename);
        }
        return file;
    }

    public String md5(String s) {
        try {
            // create md5 hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            // create hex string
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
        }
        return "";
    }

    public synchronized Date readLastUpdateTimestamp(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
        long timestamp = prefs.getLong("timestamp", 0l);
        if (timestamp != 0) {
            return new Date(timestamp);
        }
        // no registered updates yet
        return null;
    }

    private synchronized void writeLastUpdateTimestamp(Context context, Date timestamp) {
        SharedPreferences prefs = context.getSharedPreferences("storage", Context.MODE_PRIVATE);
        prefs.edit().putLong("timestamp", timestamp.getTime()).apply();
    }

}
