package com.example.learningtool.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.learningtool.R;
import com.example.learningtool.model.Products;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailProduct extends AppCompatActivity {

    Toolbar toolbarDetail;
    ImageView imgDetail;
    TextView txtten,txtgia,txtmota,txtDiscount;
    Spinner spinner;
    Button btnDatmua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        AnhXa();
        ActionToolbar();
        GetInfomation();
        CatchEvenSpinner();
    }

    private void CatchEvenSpinner() {
        Integer[] soluong=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInfomation() {
        int Id;
        String Name;
        double Price;
        int Discount;
        String Information;
        String Image;
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
