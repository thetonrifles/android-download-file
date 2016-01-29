package com.thetonrifles.downloadfile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thetonrifles.downloadfile.R;
import com.thetonrifles.downloadfile.parser.FileV2Item;

import java.util.List;

public class FileV2Adapter extends AbstractFileAdapter<FileV2Item> {


    protected class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name;
        TextView txt_image;
        TextView txt_link;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_image = (TextView) itemView.findViewById(R.id.txt_image);
            txt_link = (TextView) itemView.findViewById(R.id.txt_link);
        }

    }

    public FileV2Adapter(List<FileV2Item> contents) {
        super(contents);
    }

    @Override
    RecyclerView.ViewHolder buildViewHolder(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_file_2_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    void bind(FileV2Item item, RecyclerView.ViewHolder holder) {
        ItemViewHolder vh = (ItemViewHolder) holder;
        vh.txt_name.setText(item.getName() + " [" + item.getType() + "]");
        vh.txt_image.setText(item.getImage());
        vh.txt_link.setText(item.getLink());
    }

}
