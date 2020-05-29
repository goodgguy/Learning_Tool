package com.example.learningtool.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.learningtool.R;
import com.example.learningtool.model.GioHang;
import com.example.learningtool.model.Products;
import com.example.learningtool.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class DetailProduct extends AppCompatActivity {

    Toolbar toolbarDetail;
    ImageView imgDetail;
    TextView txtten,txtgia,txtmota,txtDiscount;
    Spinner spinner;
    Button btnDatmua;
    private static final String TAG = "DetailProduct";
    //Product
    int Id;
    String Name;
    double Price;
    int Discount;
    String Information;
    String Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        Log.e("DETAIL PRODUCT",String.valueOf(MainActivity.IdCart));
        AnhXa();
        ActionToolbar();
        GetInfomation();
        CatchEvenSpinner();
        EvenButton();
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

    private void EvenButton() {
        btnDatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.gioHangArrayList.size()>0)
                {
                    int sl=Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exist=false;
                    for(int i=0;i<MainActivity.gioHangArrayList.size();i++)
                    {
                        if(MainActivity.gioHangArrayList.get(i).getId()==Id)
                        {
                            MainActivity.gioHangArrayList.get(i).setSoluong(MainActivity.gioHangArrayList.get(i).getSoluong()+sl);
                            if(MainActivity.gioHangArrayList.get(i).getSoluong()>=10)
                            {
                                MainActivity.gioHangArrayList.get(i).setSoluong(10);
                            }
                            MainActivity.gioHangArrayList.get(i).setPrice(Price*MainActivity.gioHangArrayList.get(i).getSoluong());
                            exist=true;
                        }
                    }
                    if(exist==false)
                    {
                        int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                        double giamoi=soluong*Price;
                        GioHang gioHang=new GioHang(Id,Name,giamoi,Image,soluong);
                        MainActivity.gioHangArrayList.add(gioHang);
                        AddProductToServer(gioHang);

                    }
                }else
                {
                    int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                    double giamoi=soluong*Price;
                    GioHang gioHang=new GioHang(Id,Name,giamoi,Image,soluong);
                    MainActivity.gioHangArrayList.add(gioHang);
                    AddProductToServer(gioHang);

                }
                Intent intent=new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }
    //===============ADDDDDDDDDDDDD====================================
    private void AddProductToServer(GioHang gioHang)
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Quantity",gioHang.getSoluong());
            jsonBody.put("UnitPrice",gioHang.getPrice());
            jsonBody.put("ProId",gioHang.getId());
            jsonBody.put("CartId",MainActivity.IdCart);

            final String mRequestBody = jsonBody.toString();
            Log.e("CartItem CartItem",mRequestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.postCartItem, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

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

    private void CatchEvenSpinner() {
        Integer[] soluong=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInfomation() {
        Products products= (Products) getIntent().getSerializableExtra("thongtin");
        Id=products.getId();
        Name=products.getName();
        Price=products.getPrice();
        Discount=products.getDiscount();
        Information=products.getInformation();
        Image=products.getImage();
        txtten.setText(Name);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
        double discount=products.getDiscount();
        double price=products.getPrice();
        if(discount!=0)
        {
            txtDiscount.setText(decimalFormat.format(products.getPrice())+"Đ");
            txtDiscount.setPaintFlags(txtDiscount.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            price=price-price*(discount/100);
        }
        txtgia.setText(decimalFormat.format(price)+"Đ");
        Price=price;
        txtmota.setText(products.getInformation());
        Picasso.with(getApplicationContext()).load(Image)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(imgDetail);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbarDetail=findViewById(R.id.toolbarDetailProduct);
        imgDetail=findViewById(R.id.imageDetailProduct);
        txtten=findViewById(R.id.textviewNameDetailProduct);
        txtgia=findViewById(R.id.textviewgiaDetailProduct);
        txtmota=findViewById(R.id.textviewMotaDetailProduct);
        spinner=findViewById(R.id.spinnerDetailProduct);
        btnDatmua=findViewById(R.id.btnGioHangDetailProduct);
        txtDiscount=findViewById(R.id.textviewDiscountDetailProduct);
    }
}
