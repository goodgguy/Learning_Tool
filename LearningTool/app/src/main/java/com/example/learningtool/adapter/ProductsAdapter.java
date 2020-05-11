package com.example.learningtool.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningtool.R;
import com.example.learningtool.activity.DetailProduct;
import com.example.learningtool.model.Products;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ItemHolder>{
    Context context;
    ArrayList<Products> productsArrayList;

    public ProductsAdapter(Context context, ArrayList<Products> productsArrayList) {
        this.context = context;
        this.productsArrayList = productsArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_newest,null);
        ItemHolder itemHolder=new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Products products=productsArrayList.get(position);
        holder.txtNameProduct.setText(products.getName());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
        double discount=products.getDiscount();
        double price=products.getPrice();
        if(discount!=0)
        {
            holder.txtPriceDiscount.setText(decimalFormat.format(products.getPrice())+"Đ");
            holder.txtPriceDiscount.setPaintFlags(holder.txtPriceDiscount.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            price=price-price*(discount/100);
        }
        holder.txtPriceProduct.setText(decimalFormat.format(price)+"Đ");
        Picasso.with(context).load(products.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder
    {
        public ImageView imgProduct;
        public TextView txtNameProduct,txtPriceProduct,txtPriceDiscount;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgViewProduct);
            txtNameProduct=itemView.findViewById(R.id.textviewProductName);
            txtPriceProduct=itemView.findViewById(R.id.textviewProductPrice);
            txtPriceDiscount=itemView.findViewById(R.id.textviewProductPriceDiscount);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, DetailProduct.class);
                    intent.putExtra("thongtin",productsArrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
