package com.example.learningtool.Thread;

import android.os.AsyncTask;
import android.util.Log;

import com.example.learningtool.activity.MainActivity;

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

public class getIdCartUserAsynTask extends AsyncTask<String,Void,Void> {

    String data = "";
    int id;
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
            Log.e("CARTID CARTID CARTID",data);
            try {
                JSONArray jsonArray=new JSONArray(data);
                JSONObject jsonObject= jsonArray.getJSONObject(0);
                this.id=jsonObject.getInt("Id");
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
        Log.e("DATA DATA DATA",this.data);
        Log.e("ID ID DATA",String.valueOf(this.id));
        MainActivity.IdCart=this.id;
    }
}
