package com.thetonrifles.downloadfile.adapter;

import com.thetonrifles.downloadfile.adapter.model.FileItem;

import java.util.ArrayList;
import java.util.List;

public class FileParser {

    public List<FileItem> parse(String content) {
        List<FileItem> items = new ArrayList<>();
        String[] lines = content.split("\n");
        FileItem item = new FileItem();
        for (String line : lines) {
            if (line.contains(":")) {
                int index = line.indexOf(":");
                String s = line.substring(index + 1).trim();
                if (line.contains("Type")) {
                    item.setType(s);
                }
                if (line.contains("Image")) {
                    item.setImage(s);

                }
                if (line.contains("Name")) {
                    item.setName(s);

                }
                if (line.contains("Download")) {
                    item.setLink(s);
                }
            }
            if (line.contains("-")) {
                items.add(item);
                item = new FileItem();
            }
        }
        return items;
    }

}
