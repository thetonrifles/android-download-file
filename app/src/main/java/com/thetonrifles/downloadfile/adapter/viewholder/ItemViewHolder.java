package com.thetonrifles.downloadfile.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.thetonrifles.downloadfile.R;
import com.thetonrifles.downloadfile.adapter.model.FileItem;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    TextView txt_name;
    TextView txt_image;
    TextView txt_link;

    public ItemViewHolder(View itemView) {
        super(itemView);
        txt_name = (TextView) itemView.findViewById(R.id.txt_name);
        txt_image = (TextView) itemView.findViewById(R.id.txt_image);
        txt_link = (TextView) itemView.findViewById(R.id.txt_link);
    }

    public void bind(FileItem item) {
        txt_name.setText(item.getName() + " [" + item.getType() + "]");
        txt_image.setText(item.getImage());
        txt_link.setText(item.getLink());
    }

}