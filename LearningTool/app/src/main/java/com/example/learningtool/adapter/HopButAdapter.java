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

public class HopButAdapter extends BaseAdapter {
    Context context;
    ArrayList<Products> arrayListHopBut;

    public HopButAdapter(Context context, ArrayList<Products> arrayListHopBut) {
        this.context = context;
        this.arrayListHopBut = arrayListHopBut;
    }

    @Override
    public int getCount() {
        return arrayListHopBut.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListHopBut.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder
    {
        public TextView txttenHopBut,txtGiaHopBut,txtDiscountHopBut,txtMotaHopBut;
        public ImageView imgHopBut;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.row_hopbut,null);
            viewHolder.txttenHopBut=convertView.findViewById(R.id.textviewHopBut);
            viewHolder.txtDiscountHopBut=convertView.findViewById(R.id.textviewgiaHopButDiscount);
            viewHolder.txtGiaHopBut=convertView.findViewById(R.id.textviewgiaHopBut);
            viewHolder.txtMotaHopBut=convertView.findViewById(R.id.textviewmotaHopBut);
            viewHolder.imgHopBut=convertView.findViewById(R.id.imgViewHopBut);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Products products= (Products) getItem(position);
        viewHolder.txttenHopBut.setText(products.getName());
        viewHolder.txtMotaHopBut.setMaxLines(2);
        viewHolder.txtMotaHopBut.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaHopBut.setText(products.getDescription());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
        double discount=products.getDiscount();
        double price=products.getPrice();
        if(discount!=0)
        {
            viewHolder.txtDiscountHopBut.setText(decimalFormat.format(products.getPrice())+"Đ");
            viewHolder.txtDiscountHopBut.setPaintFlags(viewHolder.txtDiscountHopBut.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            price=price-price*(discount/100);
        }
        viewHolder.txtGiaHopBut.setText(decimalFormat.format(price)+"Đ");
        Picasso.with(context).load(products.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(viewHolder.imgHopBut);

        return convertView;
    }
}
