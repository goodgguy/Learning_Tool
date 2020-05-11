package com.example.learningtool.model;

public class Categories {
    private int id;
    private String CateName;
    private String CateImage;

    public Categories(int id, String cateName, String cateImage) {
        this.id = id;
        CateName = cateName;
        CateImage = cateImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCateName() {
        return CateName;
    }

    public void setCateName(String cateName) {
        CateName = cateName;
    }

    public String getCateImage() {
        return CateImage;
    }

    public void setCateImage(String cateImage) {
        CateImage = cateImage;
    }
}
