package com.thetonrifles.downloadfile.parser;

public class FileV1Item extends AbstractItem {

    private static final long serialVersionUID = 1L;

    private String content;

    public FileV1Item(String content) {
        super();
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}