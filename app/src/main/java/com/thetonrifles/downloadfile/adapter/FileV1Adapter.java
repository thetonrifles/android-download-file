package com.thetonrifles.downloadfile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thetonrifles.downloadfile.R;
import com.thetonrifles.downloadfile.parser.FileV1Item;

import java.util.List;

public class FileV1Adapter extends AbstractFileAdapter<FileV1Item> {


    protected class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView txt_content;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txt_content = (TextView) itemView.findViewById(R.id.txt_content);
        }

    }

    public FileV1Adapter(List<FileV1Item> contents) {
        super(contents);
    }

    @Override
    RecyclerView.ViewHolder buildViewHolder(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_file_1_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    void bind(FileV1Item item, RecyclerView.ViewHolder holder) {
        ItemViewHolder vh = (ItemViewHolder) holder;
        vh.txt_content.setText(item.getContent());
    }

}
