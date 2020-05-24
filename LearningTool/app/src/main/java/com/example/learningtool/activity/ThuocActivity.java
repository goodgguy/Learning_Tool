package com.example.learningtool.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.learningtool.adapter.ThuocAdapter;
import com.example.learningtool.model.Products;
import com.example.learningtool.ultil.CheckConnection;
import com.example.learningtool.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThuocActivity extends AppCompatActivity {
    Toolbar toolbarThuoc;
    ListView listViewThuoc;
    ThuocAdapter thuocAdapter;
    ArrayList<Products> thuocarrayList;
    int idThuoc=0;
    int page=1;
    View footerView;
    boolean inLoading;
    boolean limitdata=false;
    mHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuoc);
        AnhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            GetIDThuoc();
            ACtionToolBar();
            GetDataThuoc(page);
            LoadMoreData();
        }else
        {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra kết nối");
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void LoadMoreData() {
        listViewThuoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),DetailProduct.class);
                intent.putExtra("thongtin",thuocarrayList.get(position));
                startActivity(intent);
            }
        });
        listViewThuoc.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void GetDataThuoc(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.ProductwithCategory1 + idThuoc + Server.ProductwithCategory2 + page, new Response.Listener<JSONArray>() {
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
                    listViewThuoc.removeFooterView(footerView);
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
                            thuocarrayList.add(new Products(Id,Name,Price,Discount,CateID,Description,Information,Image));
                            thuocAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    limitdata=true;
                    listViewThuoc.removeFooterView(footerView);
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

    private void ACtionToolBar() {
        setSupportActionBar(toolbarThuoc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThuoc.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIDThuoc() {
        idThuoc=getIntent().getIntExtra("idloaiSP",-1);
    }

    private void AnhXa() {
        toolbarThuoc=findViewById(R.id.toolbarThuoc);
        listViewThuoc=findViewById(R.id.listviewthuoc);
        thuocarrayList=new ArrayList<>();
        thuocAdapter=new ThuocAdapter(getApplicationContext(),thuocarrayList);
        listViewThuoc.setAdapter(thuocAdapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView=inflater.inflate(R.layout.progressbar,null);
        handler=new mHandler();
    }
    public class mHandler extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what)
            {
                case 0:
                    listViewThuoc.addFooterView(footerView);
                    break;
                case 1:
                    GetDataThuoc(++page);
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
