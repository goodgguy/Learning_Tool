package com.example.learningtool.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
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

public class SovoAdapter extends BaseAdapter {
    Context context;
    ArrayList<Products> arrayListSovo;

    public SovoAdapter(Context context, ArrayList<Products> arrayListSovo) {
        this.context = context;
        this.arrayListSovo = arrayListSovo;
    }

    @Override
    public int getCount() {
        return arrayListSovo.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListSovo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder
    {
        public TextView txttenSovo,txtGiasovo,txtDiscountSovo,txtMota;
        public ImageView imgSovo;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.row_sovo,null);
            viewHolder.txttenSovo=convertView.findViewById(R.id.textviewSovo);
            viewHolder.txtDiscountSovo=convertView.findViewById(R.id.textviewgiaSovoDiscount);
            viewHolder.txtGiasovo=convertView.findViewById(R.id.textviewgiaSovo);
            viewHolder.txtMota=convertView.findViewById(R.id.textviewmotaSovo);
            viewHolder.imgSovo=convertView.findViewById(R.id.imgViewSovo);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Products products= (Products) getItem(position);
        viewHolder.txttenSovo.setText(products.getName());
        viewHolder.txtMota.setMaxLines(2);
        viewHolder.txtMota.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMota.setText(products.getDescription());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
        double discount=products.getDiscount();
        double price=products.getPrice();
        if(discount!=0)
        {
            viewHolder.txtDiscountSovo.setText(decimalFormat.format(products.getPrice())+"Đ");
            viewHolder.txtDiscountSovo.setPaintFlags(viewHolder.txtDiscountSovo.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            price=price-price*(discount/100);
        }
        viewHolder.txtGiasovo.setText(decimalFormat.format(price)+"Đ");
        Picasso.with(context).load(products.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(viewHolder.imgSovo);

        return convertView;
    }
}
