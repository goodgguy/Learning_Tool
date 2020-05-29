package com.example.learningtool.model;

public class Users {
    private int Id;
    private String Name;
    private String Email;
    private String Phone;
    private String Address;
    private String Username;
    private String password;
    private String Avartar;
    private int RoleId;
    public Users()
    {

    }
    public Users(int Id ,String name, String email, String phone, String address, String username, String password, String avartar, int roleId) {
        Name = name;
        Email = email;
        Phone = phone;
        Address = address;
        Username = username;
        this.password = password;
        Avartar = avartar;
        RoleId = roleId;
        this.Id=Id;
    }

    public Users(String name, String email, String phone, String address, String username, String password, int roleId) {
        Name = name;
        Email = email;
        Phone = phone;
        Address = address;
        Username = username;
        this.password = password;
        RoleId = roleId;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvartar() {
        return Avartar;
    }

    public void setAvartar(String avartar) {
        Avartar = avartar;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int roleId) {
        RoleId = roleId;
    }
}
