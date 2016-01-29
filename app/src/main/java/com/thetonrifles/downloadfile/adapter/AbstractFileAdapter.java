package com.thetonrifles.downloadfile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thetonrifles.downloadfile.parser.AbstractItem;

import java.util.List;

public abstract class AbstractFileAdapter<T extends AbstractItem> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> mContents;

    public AbstractFileAdapter(List<T> contents) {
        mContents = contents;
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return buildViewHolder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T item = mContents.get(position);
        bind(item, holder);
    }

    abstract RecyclerView.ViewHolder buildViewHolder(LayoutInflater inflater, ViewGroup parent);

    abstract void bind(T item, RecyclerView.ViewHolder holder);

}
