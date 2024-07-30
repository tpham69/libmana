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

public class SachSpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Sach> list;

    public SachSpinnerAdapter(Context context, ArrayList<Sach> list) {
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
        convertView = inflater.inflate(R.layout.spinner_item_sach, parent, false);

        //Mapping
        TextView tvMaSachSpin = convertView.findViewById(R.id.tvMaSachSpin);
        TextView tvTenSachSpin = convertView.findViewById(R.id.tvTenSachSpin);

        //Set data
        tvMaSachSpin.setText(list.get(position).getMaSach()+". ");
        tvTenSachSpin.setText(list.get(position).getTenSach());

        return convertView;
    }
}
