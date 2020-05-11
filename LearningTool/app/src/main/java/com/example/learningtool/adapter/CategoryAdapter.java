package com.example.learningtool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learningtool.R;
import com.example.learningtool.model.Categories;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    ArrayList<Categories> arrayListCategory;
    Context context;

    public CategoryAdapter(ArrayList<Categories> arrayListCategory, Context context) {
        this.arrayListCategory = arrayListCategory;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView txtCate;
        ImageView imgCate;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.row_listview_category,null);
            viewHolder.txtCate=convertView.findViewById(R.id.textViewCate);
            viewHolder.imgCate=convertView.findViewById(R.id.imageViewCategory);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Categories categories= (Categories) getItem(position);
        viewHolder.txtCate.setText(categories.getCateName());
        Picasso.with(context).load(categories.getCateImage()).
                placeholder(R.drawable.pencil).
                error(R.drawable.pencil).into(viewHolder.imgCate);

        return convertView;
    }
}
