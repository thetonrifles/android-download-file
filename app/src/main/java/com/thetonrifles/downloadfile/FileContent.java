package com.thetonrifles.downloadfile;

import java.io.Serializable;

public class FileContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String content;

    public FileContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
