package com.example.learningtool.ultil;

public class Server {
    /*public static String local_host="192.168.0.101";
    public static String port="45455";
    public static String DuongDanCategory="http://"+local_host+":"+port+"/api/Category";
    public static String DuongDanProductNewest="http://"+local_host+":"+port+"/api/Product?amount=6";
    public static String ProductwithCategory1="http://"+local_host+":"+port+"/api/Product?idCategory=";
    public static String ProductwithCategory2="&page=";*/

    public static String DuongDanCategory="http://dungcuhoctapapi.azurewebsites.net/api/Category";
    public static String DuongDanProductNewest="http://dungcuhoctapapi.azurewebsites.net/api/Product?amount=6";
    public static String ProductwithCategory1="http://dungcuhoctapapi.azurewebsites.net/api/Product?idCategory=";
    public static String ProductwithCategory2="&page=";
    public static String UserLogin="http://dungcuhoctapapi.azurewebsites.net/api/User?userName=";
    public static String UserLogin1="&passWord=";
    public static String postUser="http://dungcuhoctapapi.azurewebsites.net/api/User";
    public static String postCart="http://dungcuhoctapapi.azurewebsites.net/api/Cart";
    public static String getCartId0_0="http://dungcuhoctapapi.azurewebsites.net/api/Cart?userID1=";
    public static String getCartId0_1="&isPaid=";
    public static String postCartItem="http://dungcuhoctapapi.azurewebsites.net/api/CartItem";
    public static String getCartItemfromUserIDand0_0="http://dungcuhoctapapi.azurewebsites.net/api/CartItem?userID=";
    public static String getCartItemfromUserIDand0_1="&isPaid=";
}
