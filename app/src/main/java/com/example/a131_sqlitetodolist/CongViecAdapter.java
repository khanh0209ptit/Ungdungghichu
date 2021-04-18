package com.example.a131_sqlitetodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<CongViec> congViecList;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView tvTen;
        ImageView imgEdit, imgDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            //anh xa
            viewHolder = new ViewHolder();
            viewHolder.tvTen = convertView.findViewById(R.id.idTen);
            viewHolder.imgEdit = convertView.findViewById(R.id.imgEdit);
            viewHolder.imgDelete = convertView.findViewById(R.id.imgDelete);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CongViec congViec = congViecList.get(position);
        viewHolder.tvTen.setText(congViec.getTenCV());

        //bat su kien xoa va sua
        viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaCV(congViec.getTenCV(), congViec.getIdCV());
            }
        });

        //xoa cong viec tren ds
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoa(congViec.getTenCV(),congViec.getIdCV());
            }
        });

        return convertView;
    }
}
