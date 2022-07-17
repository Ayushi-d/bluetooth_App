package com.example.f1bleapp.Model;

public class VideoModel {

    private String title;
    private int image, headerimg;

    public VideoModel() {
    }

    public VideoModel(String title, int image, int headerimg) {
        this.title = title;
        this.image = image;
        this.headerimg = headerimg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getHeaderimg() {
        return headerimg;
    }

    public void setHeaderimg(int headerimg) {
        this.headerimg = headerimg;
    }
}
