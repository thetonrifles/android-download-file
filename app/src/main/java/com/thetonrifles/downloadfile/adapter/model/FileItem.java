package com.thetonrifles.downloadfile.adapter.model;

import java.io.Serializable;

public class FileItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private String name;
    private String image;
    private String link;

    public FileItem() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
