package com.thetonrifles.downloadfile.parser;

import java.util.ArrayList;
import java.util.List;

public class FileV1Parser extends AbstractFileParser<FileV1Item> {

    @Override
    public List<FileV1Item> parse(String content) {
        List<FileV1Item> items = new ArrayList<>();
        String[] rows = content.split("\n");
        for (String row : rows) {
            items.add(new FileV1Item(row));
        }
        return items;
    }

}
