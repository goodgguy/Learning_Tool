package com.example.learningtool.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learningtool.R;
import com.example.learningtool.activity.GioHangActivity;
import com.example.learningtool.activity.MainActivity;
import com.example.learningtool.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> gioHangArrayList;

    public GioHangAdapter(Context context, ArrayList<GioHang> gioHangArrayList) {
        this.context = context;
        this.gioHangArrayList = gioHangArrayList;
    }

    @Override
    public int getCount() {
        return gioHangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder
    {
        public TextView txttenGioHang,txtgiaGioHang;
        public ImageView imgGioHang;
        Button btntru,btnvalue,btncong;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewrow=convertView;
        if(viewrow==null)
        {
            viewrow=inflater.inflate(R.layout.row_giohang,parent,false);
            ViewHolder viewHolder=new ViewHolder();
            viewHolder.txttenGioHang=viewrow.findViewById(R.id.textviewTenGioHang);
            viewHolder.txtgiaGioHang=viewrow.findViewById(R.id.textviewgiaGioHang);
            viewHolder.imgGioHang=viewrow.findViewById(R.id.imageviewGioHang);
            viewHolder.btntru=viewrow.findViewById(R.id.btntru);
            viewHolder.btnvalue=viewrow.findViewById(R.id.btnvalue);
            viewHolder.btncong=viewrow.findViewById(R.id.btncong);
            viewrow.setTag(viewHolder);
        }
            final ViewHolder viewHolder= (ViewHolder) viewrow.getTag();
            viewHolder.txttenGioHang.setText(gioHangArrayList.get(position).getName());
            DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
            viewHolder.txtgiaGioHang.setText(decimalFormat.format(gioHangArrayList.get(position).getPrice())+"Đ");
            Picasso.with(context).load(gioHangArrayList.get(position).getImage())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(viewHolder.imgGioHang);
            viewHolder.btnvalue.setText(gioHangArrayList.get(position).getSoluong()+"");
            int sl= Integer.parseInt(viewHolder.btnvalue.getText().toString());
            if(sl>=10)
            {
                viewHolder.btncong.setVisibility(View.INVISIBLE);
                viewHolder.btntru.setVisibility(View.VISIBLE);
            }else if(sl<=1)
            {
                viewHolder.btntru.setVisibility(View.INVISIBLE);
            }else if(sl>=1)
            {
                viewHolder.btntru.setVisibility(View.VISIBLE);
                viewHolder.btncong.setVisibility(View.VISIBLE);
            }
            viewHolder.btncong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int slmoinhat=Integer.parseInt(viewHolder.btnvalue.getText().toString())+1;
                    int slht= MainActivity.gioHangArrayList.get(position).getSoluong();
                    double giaht=MainActivity.gioHangArrayList.get(position).getPrice();
                    MainActivity.gioHangArrayList.get(position).setSoluong(slmoinhat);
                    double giamoinhat=(giaht*slmoinhat)/slht;
                    MainActivity.gioHangArrayList.get(position).setPrice(giamoinhat);
                    DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
                    viewHolder.txtgiaGioHang.setText(decimalFormat.format(gioHangArrayList.get(position).getPrice())+"Đ");
                    GioHangActivity.EvenUltil();
                    if( slmoinhat>9)
                    {
                        viewHolder.btncong.setVisibility(View.INVISIBLE);
                        viewHolder.btntru.setVisibility(View.VISIBLE);
                        viewHolder.btnvalue.setText(String.valueOf(slmoinhat));
                    }else
                    {

                        viewHolder.btncong.setVisibility(View.VISIBLE);
                        viewHolder.btntru.setVisibility(View.VISIBLE);
                        viewHolder.btnvalue.setText(String.valueOf(slmoinhat));
                    }
                }
            });
            viewHolder.btntru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int slmoinhat=Integer.parseInt(viewHolder.btnvalue.getText().toString())-1;
                    int slht= MainActivity.gioHangArrayList.get(position).getSoluong();
                    double giaht=MainActivity.gioHangArrayList.get(position).getPrice();
                    MainActivity.gioHangArrayList.get(position).setSoluong(slmoinhat);
                    double giamoinhat=(giaht*slmoinhat)/slht;
                    MainActivity.gioHangArrayList.get(position).setPrice(giamoinhat);
                    DecimalFormat decimalFormat=new DecimalFormat("###,###,###.###");
                    viewHolder.txtgiaGioHang.setText(decimalFormat.format(gioHangArrayList.get(position).getPrice())+"Đ");
                    GioHangActivity.EvenUltil();
                    if( slmoinhat<2)
                    {
                        viewHolder.btntru.setVisibility(View.INVISIBLE);
                        viewHolder.btncong.setVisibility(View.VISIBLE);
                        viewHolder.btnvalue.setText(String.valueOf(slmoinhat));
                    }else
                    {

                        viewHolder.btncong.setVisibility(View.VISIBLE);
                        viewHolder.btntru.setVisibility(View.VISIBLE);
                        viewHolder.btnvalue.setText(String.valueOf(slmoinhat));
                    }
                }
            });
        return viewrow;
    }
}
