package com.thetonrifles.downloadfile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FileContentAdapter extends RecyclerView.Adapter<FileContentAdapter.ItemViewHolder> {

    protected class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView txt_content;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txt_content = (TextView) itemView.findViewById(R.id.txt_content);
        }

    }

    private List<FileContent> mContents;

    public FileContentAdapter(List<FileContent> contents) {
        mContents = contents;
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_content_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.txt_content.setText(mContents.get(position).getContent());
    }

}
