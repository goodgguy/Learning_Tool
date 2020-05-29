package com.example.learningtool.Thread;

import android.os.AsyncTask;
import android.util.Log;

import com.example.learningtool.activity.MainActivity;
import com.example.learningtool.model.GioHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class getCartItemAsynTask extends AsyncTask<String,Void,Void> {
    String data = "";
    int id;
    ArrayList<GioHang> manggiohang;

    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL url=new URL(strings[0]);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while (line!=null)
            {
                line=bufferedReader.readLine();
                data=data+line;
            }
            Log.e("GET GIO HANG",data);
            try {
                manggiohang=new ArrayList<>();
                JSONArray jsonArray=new JSONArray(data);
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    int Id=jsonObject.getInt("Id");
                    String Name=jsonObject.getString("Name");
                    double Price=jsonObject.getDouble("Price");
                    String Image=jsonObject.getString("Image");
                    int soluong=jsonObject.getInt("Quantity");
                    GioHang gioHang=new GioHang(Id,Name,Price,Image,soluong);
                    manggiohang.add(gioHang);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.e("MAG GIO HANG",manggiohang.get(0).getName());
        MainActivity.gioHangArrayList=manggiohang;
    }
}
