package thaiph.ph48495.libmana.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Base64;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.models.Top;

public class TopAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Top> list;

    public TopAdapter(Context context, ArrayList<Top> list) {
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
        convertView = inflater.inflate(R.layout.top10_item, parent, false);

        //mapping
        TextView tvSachTop = convertView.findViewById(R.id.tvSachTop);
        TextView tvSoLuong = convertView.findViewById(R.id.tvSoLuong);

        //Set data
        tvSachTop.setText("Sách: "+list.get(position).getTenSach());
        tvSoLuong.setText(list.get(position).getSoLuong()+"");

        return convertView;
    }
}
