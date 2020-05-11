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

public class ThuocAdapter extends BaseAdapter {
    Context context;
    ArrayList<Products> arrayListThuoc;

    public ThuocAdapter(Context context, ArrayList<Products> arrayListThuoc) {
        this.context = context;
        this.arrayListThuoc = arrayListThuoc;
    }

    @Override
    public int getCount() {
        return arrayListThuoc.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListThuoc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder
    {
        public TextView txttenThuoc,txtGiaThuoc,txtDiscountThuoc,txtMotaThuoc;
        public ImageView imgThuoc;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.row_thuoc,null);
            viewHolder.txttenThuoc=convertView.findViewById(R.id.textviewThuoc);
            viewHolder.txtDiscountThuoc=convertView.findViewById(R.id.textviewgiaThuocDiscount);
            viewHolder.txtGiaThuoc=convertView.findViewById(R.id.textviewgiathuoc);
            viewHolder.txtMotaThuoc=convertView.findViewById(R.id.textviewmotaThuoc);
            viewHolder.imgThuoc=convertView.findViewById(R.id.imgViewThuoc);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Products products= (Products) getItem(position);
        viewHolder.txttenThuoc.setText(products.getName());
        viewHolder.txtMotaThuoc.setMaxLines(2);
        viewHolder.txtMotaThuoc.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaThuoc.setText(products.getDescription());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
        double discount=products.getDiscount();
        double price=products.getPrice();
        if(discount!=0)
        {
            viewHolder.txtDiscountThuoc.setText(decimalFormat.format(products.getPrice())+"Đ");
            viewHolder.txtDiscountThuoc.setPaintFlags(viewHolder.txtDiscountThuoc.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            price=price-price*(discount/100);
        }
        viewHolder.txtGiaThuoc.setText(decimalFormat.format(price)+"Đ");
        Picasso.with(context).load(products.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(viewHolder.imgThuoc);

        return convertView;
    }
}
