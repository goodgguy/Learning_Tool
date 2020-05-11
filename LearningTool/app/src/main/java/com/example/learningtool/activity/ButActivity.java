package com.example.learningtool.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.learningtool.R;
import com.example.learningtool.adapter.ButAdapter;
import com.example.learningtool.model.Products;
import com.example.learningtool.ultil.CheckConnection;
import com.example.learningtool.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ButActivity extends AppCompatActivity {
    Toolbar toolbarBut;
    ListView listViewBut;
    ButAdapter butAdapter;
    ArrayList<Products> butarrayList;
    int idBut=0;
    int page=1;
    View footerView;
    boolean inLoading;
    boolean limitdata=false;
    mHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_but);
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            AnhXa();
            GetIDBut();
            ACtionToolBar();
            GetDataBut(page);
            LoadMoreData();
        }else
        {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra Internet");
        }
    }
    private void LoadMoreData() {
        listViewBut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),DetailProduct.class);
                intent.putExtra("thongtin",butarrayList.get(position));
                startActivity(intent);
            }
        });
        listViewBut.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount==totalItemCount&&totalItemCount!=0&&inLoading==false&&limitdata==false)
                {
                    inLoading=true;
                    ThreadData threadData=new ThreadData();
                    threadData.start();
                }
            }
        });
    }
    private void AnhXa() {
        toolbarBut=findViewById(R.id.toolbarBut);
        listViewBut=findViewById(R.id.listviewBut);
        butarrayList=new ArrayList<>();
        butAdapter=new ButAdapter(getApplicationContext(),butarrayList);
        listViewBut.setAdapter(butAdapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView=inflater.inflate(R.layout.progressbar,null);
        handler=new mHandler();
    }
    private void GetIDBut() {
        idBut=getIntent().getIntExtra("idloaiSP",-1);
    }
    private void ACtionToolBar() {
        setSupportActionBar(toolbarBut);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarBut.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void GetDataBut(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.ProductwithCategory1 + idBut + Server.ProductwithCategory2 + page, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int Id;
                String Name="";
                double Price=0;
                int Discount=0;
                int CateID=0;
                String Description="";
                String Information="";
                String Image="";
                if(response!=null&&response.length()!=2)
                {
                    listViewBut.removeFooterView(footerView);
                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            Id=jsonObject.getInt("Id");
                            Name=jsonObject.getString("Name");
                            Price=jsonObject.getDouble("Price");
                            Discount=jsonObject.getInt("Discount");
                            CateID=jsonObject.getInt("CateId");
                            Description=jsonObject.getString("Description");
                            Information=jsonObject.getString("Information");
                            Image=jsonObject.getString("Image");
                            butarrayList.add(new Products(Id,Name,Price,Discount,CateID,Description,Information,Image));
                            butAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    limitdata=true;
                    listViewBut.removeFooterView(footerView);
                    Toast.makeText(getApplicationContext(),"Đã hết dữ liệu",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public class mHandler extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what)
            {
                case 0:
                    listViewBut.addFooterView(footerView);
                    break;
                case 1:
                    GetDataBut(++page);
                    inLoading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=handler.obtainMessage(1);
            handler.sendMessage(message);
            super.run();
        }
    }
}
