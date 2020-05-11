package com.example.learningtool.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learningtool.R;
import com.example.learningtool.model.Products;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ButAdapter extends BaseAdapter {
    Context context;
    ArrayList<Products> arrayListBut;

    public ButAdapter(Context context, ArrayList<Products> arrayListBut) {
        this.context = context;
        this.arrayListBut = arrayListBut;
    }

    @Override
    public int getCount() {
        return arrayListBut.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListBut.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder
    {
        public TextView txttenBut,txtGiaBut,txtDiscountBut,txtMotaBut;
        public ImageView imgBut;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ButAdapter.ViewHolder viewHolder=null;
        if(convertView==null)
        {
            viewHolder=new ButAdapter.ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.row_but,null);
            viewHolder.txttenBut=convertView.findViewById(R.id.textviewBut);
            viewHolder.txtDiscountBut=convertView.findViewById(R.id.textviewgiaButDiscount);
            viewHolder.txtGiaBut=convertView.findViewById(R.id.textviewgiaBut);
            viewHolder.txtMotaBut=convertView.findViewById(R.id.textviewmotaBut);
            viewHolder.imgBut=convertView.findViewById(R.id.imgViewBut);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ButAdapter.ViewHolder) convertView.getTag();
        }
        Products products= (Products) getItem(position);
        viewHolder.txttenBut.setText(products.getName());
        viewHolder.txtMotaBut.setMaxLines(2);
        viewHolder.txtMotaBut.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaBut.setText(products.getDescription());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
        double discount=products.getDiscount();
        double price=products.getPrice();
        if(discount!=0)
        {
            viewHolder.txtDiscountBut.setText(decimalFormat.format(products.getPrice())+"Đ");
            viewHolder.txtDiscountBut.setPaintFlags(viewHolder.txtDiscountBut.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            price=price-price*(discount/100);
        }
        viewHolder.txtGiaBut.setText(decimalFormat.format(price)+"Đ");
        Picasso.with(context).load(products.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(viewHolder.imgBut);


        return convertView;
    }
}
