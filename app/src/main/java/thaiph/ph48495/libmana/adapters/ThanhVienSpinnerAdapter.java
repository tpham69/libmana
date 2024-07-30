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
import thaiph.ph48495.libmana.models.Sach;
import thaiph.ph48495.libmana.models.ThanhVien;

public class ThanhVienSpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ThanhVien> list;

    public ThanhVienSpinnerAdapter(Context context, ArrayList<ThanhVien> list) {
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
        convertView = inflater.inflate(R.layout.spinner_item_thanhvien, parent, false);

        //Mapping
        TextView tvMaTVSpin = convertView.findViewById(R.id.tvMaTVSpin);
        TextView tvTenTVSpin = convertView.findViewById(R.id.tvTenTVSpin);

        //Set data
        tvMaTVSpin.setText(list.get(position).getMaTV()+". ");
        tvTenTVSpin.setText(list.get(position).getHoTen());

        return convertView;
    }
}
