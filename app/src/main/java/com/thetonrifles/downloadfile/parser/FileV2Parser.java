package com.thetonrifles.downloadfile.parser;

import java.util.ArrayList;
import java.util.List;

public class FileV2Parser extends AbstractFileParser<FileV2Item> {

    @Override
    public List<FileV2Item> parse(String content) {
        List<FileV2Item> items = new ArrayList<>();
        String[] lines = content.split("\n");
        FileV2Item item = new FileV2Item();
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
                item = new FileV2Item();
            }
        }
        return items;
    }

}
