package com.example.learningtool.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.learningtool.Fragment.FragmentDialog_Login;
import com.example.learningtool.Interface.getIdUserInterface;
import com.example.learningtool.Interface.getUserInterface;
import com.example.learningtool.R;
import com.example.learningtool.Thread.getCartItemAsynTask;
import com.example.learningtool.Thread.getIdCartUserAsynTask;
import com.example.learningtool.adapter.CategoryAdapter;
import com.example.learningtool.adapter.ProductsAdapter;
import com.example.learningtool.model.Categories;
import com.example.learningtool.model.GioHang;
import com.example.learningtool.model.Products;
import com.example.learningtool.model.Users;
import com.example.learningtool.ultil.CheckConnection;
import com.example.learningtool.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements getUserInterface, getIdUserInterface {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewViewmanhinhcinh;
    NavigationView navigationView;
    ListView listViewManhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Categories> categoriesList;
    CategoryAdapter categoryAdapter;
    int id=0;
    String tenCategory="";
    String hinhCategory="";
    ArrayList<Products> productsArrayList;
    ProductsAdapter productsAdapter;
    public static ArrayList<GioHang> gioHangArrayList;
    public static Users users=null;
    //=======================
    public static int IdCart;
    //========================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DialogLogin();
            AnhXa();
            if(CheckConnection.haveNetworkConnection(getApplicationContext()))
            {
                ActionBar();
                ActionViewFlipper();
                GetDuLieuCategory();
                GetDataNewestProduct();
                CatchOnItemListView();
            }else {
                CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                finish();
            }
    }
    public void DialogLogin()
    {
        DialogFragment dialogFragment= FragmentDialog_Login.newInstance();
        dialogFragment.show(getSupportFragmentManager(),"tag");
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

    private void CatchOnItemListView() {
        listViewManhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent=new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                         drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent=new Intent(MainActivity.this,SoVoActivity.class);
                            intent.putExtra("idloaiSP",categoriesList.get(position).getId());
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent=new Intent(MainActivity.this,ButActivity.class);
                            intent.putExtra("idloaiSP",categoriesList.get(position).getId());
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent=new Intent(MainActivity.this,HopbutActivity.class);
                            intent.putExtra("idloaiSP",categoriesList.get(position).getId());
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent=new Intent(MainActivity.this,ThuocActivity.class);
                            intent.putExtra("idloaiSP",categoriesList.get(position).getId());
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent=new Intent(MainActivity.this,LienheActivity.class);
                            intent.putExtra("idloaiSP",categoriesList.get(position).getId());
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent=new Intent(MainActivity.this,ThongtinActivity.class);
                            intent.putExtra("idloaiSP",categoriesList.get(position).getId());
                            startActivity(intent);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDataNewestProduct() {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.DuongDanProductNewest, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null)
                {
                    int IdProduct=0;
                    String ProductName="";
                    double ProductPrice=0;
                    int ProductDiscount=0;
                    int CateID=0;
                    String Description="";
                    String Information="";
                    String ProductImg="";
                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            IdProduct=jsonObject.getInt("Id");
                            ProductName=jsonObject.getString("Name");
                            ProductDiscount=jsonObject.getInt("Discount");
                            ProductPrice=jsonObject.getDouble("Price");
                            CateID=jsonObject.getInt("CateId");
                            Description=jsonObject.getString("Description");
                            Information=jsonObject.getString("Information");
                            ProductImg=jsonObject.getString("Image");
                            productsArrayList.add(new Products(IdProduct,ProductName,ProductPrice,ProductDiscount,CateID,Description,Information,ProductImg));
                            productsAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void GetDuLieuCategory()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.DuongDanCategory, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null)
                {
                    for(int i=0;i<response.length();i++)
                    {
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            id=jsonObject.getInt("CateId");
                            tenCategory=jsonObject.getString("CateName");
                            hinhCategory=jsonObject.getString("CateImage");
                            categoriesList.add(new Categories(id,tenCategory,hinhCategory));
                            categoryAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    categoriesList.add(5,new Categories(0,"Liên Hệ","https://cdn1.iconfinder.com/data/icons/mix-color-3/502/Untitled-12-512.png"));
                    categoriesList.add(6,new Categories(0,"Thông Tin","https://cdn3.iconfinder.com/data/icons/bold-blue-glyphs-free-samples/32/Info_Circle_Symbol_Information_Letter-512.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void ActionBar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_overflow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    public void ActionViewFlipper()
    {
        ArrayList<String> mangquangcao=new ArrayList<>();
        mangquangcao.add("https://product.hstatic.net/1000189361/product/do-dung-hoc-tap_master.jpg");
        mangquangcao.add("https://img.lovepik.com/photo/50139/2024.jpg_wh860.jpg");
        mangquangcao.add("https://phunuvietnam.mediacdn.vn/media/news/f01343d174b6dbc8cd10add58111dfa7/do-dung-hoc-tap-10.jpg");
        mangquangcao.add("https://vn-live-01.slatic.net/p/e79f543cb8981fc3224aeca9cac95fcb.jpg");
        for(int i=0;i<mangquangcao.size();i++)
        {
            ImageView imageView=new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }
    public void AnhXa()
    {
        toolbar=findViewById(R.id.toolbarManhinhchinh);
        viewFlipper=findViewById(R.id.viewFlipper);
        recyclerViewViewmanhinhcinh=findViewById(R.id.recyclerView);
        navigationView=findViewById(R.id.navigationView);
        listViewManhinhchinh=findViewById(R.id.listViewManhinhchinh);
        drawerLayout=findViewById(R.id.drawerlayout);
        categoriesList=new ArrayList<>();
        categoriesList.add(0,new Categories(0,"Trang Chính","http://icons.iconarchive.com/icons/fps.hu/free-christmas-flat-circle/512/home-icon.png"));
        categoryAdapter=new CategoryAdapter(categoriesList,getApplicationContext());
        listViewManhinhchinh.setAdapter(categoryAdapter);
        productsArrayList=new ArrayList<>();
        productsAdapter=new ProductsAdapter(getApplicationContext(),productsArrayList);
        recyclerViewViewmanhinhcinh.setHasFixedSize(true);
        recyclerViewViewmanhinhcinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewViewmanhinhcinh.setAdapter(productsAdapter);
        if(gioHangArrayList!=null)
        {

        }else
        {
            gioHangArrayList=new ArrayList<>();
        }

    }

    @Override
    public void get(Users users) {
        MainActivity.users=users;
        Log.e("USER",MainActivity.users.getUsername()+" "+MainActivity.users.getPassword());
        Log.e("ID CART ID CART",String.valueOf(IdCart));
        getIdCart();
        getCartItem();
    }

    private void getCartItem() {
        String url=Server.getCartItemfromUserIDand0_0+users.getId()+Server.getCartItemfromUserIDand0_1+"0";
        getCartItemAsynTask getCartItemAsynTask=new getCartItemAsynTask();
        getCartItemAsynTask.execute(url);
    }

    public static void getIdCart()
    {
        String strurl=Server.getCartId0_0+users.getId()+Server.getCartId0_1+"0";
        Log.e("URL GET IDCART",strurl);
        Log.e("USER OUT OF FUNCTION",MainActivity.users.getUsername()+" "+MainActivity.users.getPassword()+" "+users.getId());
        getIdCartUserAsynTask getIdCartUserAsynTask=new getIdCartUserAsynTask();
        getIdCartUserAsynTask.execute(strurl);
    }

    @Override
    public void getExit(boolean check) {
        if(check==true)
        {
            finish();
        }
    }

    @Override
    public void getID(int id) {

            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("UserId",id);
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

}
