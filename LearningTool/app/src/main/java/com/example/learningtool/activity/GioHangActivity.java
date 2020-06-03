package com.example.learningtool.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.learningtool.R;
import com.example.learningtool.adapter.GioHangAdapter;
import com.example.learningtool.model.GioHang;
import com.example.learningtool.ultil.CheckConnection;
import com.example.learningtool.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class GioHangActivity extends AppCompatActivity {
    ListView lvGioHang;
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan,btnTiepTucMua;
    Toolbar toolbarGioHang;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        ActionToolBar();
        CheckData();
        EvenUltil();
        CatchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.gioHangArrayList.size()>0)
                {
                    DatHangThanhCong();
                    TaoNewCart();
                    MainActivity.gioHangArrayList.clear();
                    gioHangAdapter.notifyDataSetChanged();
                    MainActivity.getIdCart();
                }else
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Giỏ hàng chưa có sản phẩm");
                }
            }
        });
    }

    private void TaoNewCart() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("UserId",MainActivity.users);
            jsonBody.put("BuyDate",date);
            jsonBody.put("IsPaid",0);

            final String mRequestBody = jsonBody.toString();
            Log.e("Cart Cart Cart Cart",mRequestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.postCart, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("TUANQUEN1","Id Id Id Id Id Id");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void DatHangThanhCong()
    {
        String url= Server.putStateCart_1+MainActivity.IdCart+Server.putStateCart_2+"1";
        final JSONObject jsonObject=new JSONObject();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest putRequest =new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
    ){
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {

                try {
                    Log.i("json", jsonObject.toString());
                    return jsonObject.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        requestQueue.add(putRequest);
    }

    private void CatchOnItemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xóa Sản Phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.gioHangArrayList.size()<=0)
                        {
                            txtThongBao.setVisibility(View.VISIBLE);
                        }else
                        {
                            MainActivity.gioHangArrayList.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EvenUltil();
                            if(MainActivity.gioHangArrayList.size()<=0)
                            {
                                txtThongBao.setVisibility(View.VISIBLE);
                            }else
                            {
                                txtThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EvenUltil();
                            }
                        }
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EvenUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EvenUltil() {
        double tongtien=0;
        for (int i=0;i<MainActivity.gioHangArrayList.size();i++)
        {
            tongtien+=MainActivity.gioHangArrayList.get(i).getPrice();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
        txtTongTien.setText(decimalFormat.format(tongtien)+"Đ");
    }

    private void CheckData() {
        if(MainActivity.gioHangArrayList.size()<=0)
        {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        }else
        {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        lvGioHang=findViewById(R.id.listviewGioHang);
        txtThongBao=findViewById(R.id.txtThongBao);
        txtTongTien=findViewById(R.id.textviewTongTien);
        btnThanhToan=findViewById(R.id.btnThanhToanGioHang);
        btnTiepTucMua=findViewById(R.id.btnTiepTucMuaHang);
        toolbarGioHang=findViewById(R.id.toolbarGioHang);
        gioHangAdapter=new GioHangAdapter(GioHangActivity.this,MainActivity.gioHangArrayList);
        lvGioHang.setAdapter(gioHangAdapter);
    }
}
