package thaiph.ph48495.libmana.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.models.LoaiSach;

public class LoaiSachSpinerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<LoaiSach> list;

    public LoaiSachSpinerAdapter(Context context, ArrayList<LoaiSach> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.spinner_item_loaisach, parent, false);

        //Mapping
        TextView tvMaLoaiSpin = convertView.findViewById(R.id.tvMaLoaiSpin);
        TextView tvTenLoaiSpin = convertView.findViewById(R.id.tvTenLoaiSpin);

        //Set data
        tvMaLoaiSpin.setText(list.get(position).getMaLoai()+". ");
        tvTenLoaiSpin.setText(list.get(position).getTenLoai());

        return convertView;
    }
}
