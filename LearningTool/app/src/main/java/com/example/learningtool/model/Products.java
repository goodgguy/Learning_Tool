package com.example.learningtool.model;

import java.io.Serializable;

public class Products implements Serializable {
    private int Id;
    private String Name;
    private double Price;
    private int Discount;
    private int CateID;
    private String Description;
    private String Information;
    private String Image;

    public Products(int id, String name, double price, int discount, int cateID, String description, String information, String image) {
        Id = id;
        Name = name;
        Price = price;
        Discount = discount;
        CateID = cateID;
        Description = description;
        Information = information;
        Image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public int getCateID() {
        return CateID;
    }

    public void setCateID(int cateID) {
        CateID = cateID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
