package com.example.learningtool.model;

public class GioHang {
    private int Id;
    private String Name;
    private double Price;
    private String Image;
    private int soluong;

    public GioHang(int id, String name, double price, String image, int soluong) {
        Id = id;
        Name = name;
        Price = price;
        Image = image;
        this.soluong = soluong;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
