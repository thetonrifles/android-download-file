package com.thetonrifles.downloadfile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thetonrifles.downloadfile.R;
import com.thetonrifles.downloadfile.adapter.model.FileItem;
import com.thetonrifles.downloadfile.adapter.viewholder.ItemViewHolder;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<FileItem> mContents;

    public FileAdapter(List<FileItem> contents) {
        mContents = contents;
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_file_2_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        FileItem item = mContents.get(position);
        holder.bind(item);
    }

}
