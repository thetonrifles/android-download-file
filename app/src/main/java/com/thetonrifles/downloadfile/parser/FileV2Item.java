package com.thetonrifles.downloadfile.parser;

public class FileV2Item extends AbstractItem {

    private static final long serialVersionUID = 1L;

    private String type;
    private String name;
    private String image;
    private String link;

    public FileV2Item() {
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
