package com.example.f1bleapp.Model;

public class SearchModel {

    private String viewCount,description;
    private int image;
    private int viewtype;

    public SearchModel() {
    }

    public SearchModel(String viewCount, int image, String description,int viewtype) {
        this.viewCount = viewCount;
        this.image = image;
        this.description = description;
        this.viewtype = viewtype;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getViewtype() {
        return viewtype;
    }

    public void setViewtype(int viewtype) {
        this.viewtype = viewtype;
    }
}
