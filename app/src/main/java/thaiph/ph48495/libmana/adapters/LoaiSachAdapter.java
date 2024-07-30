package thaiph.ph48495.libmana.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.daos.LoaiSachDAO;
import thaiph.ph48495.libmana.models.LoaiSach;
import thaiph.ph48495.libmana.models.ThanhVien;

public class LoaiSachAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<LoaiSach> list;

    private LoaiSachDAO dao;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list) {
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
        convertView = inflater.inflate(R.layout.loaisach_item, parent, false);

        //Mapping
        TextView tvMaLSach = convertView.findViewById(R.id.tvMaLSach);
        TextView tvTenLoai = convertView.findViewById(R.id.tvTenLoai);
        ImageView btnXoa = convertView.findViewById(R.id.btnXoa);

        //Set data
        tvMaLSach.setText("Mã loại: "+list.get(position).getMaLoai());
        tvTenLoai.setText("Tên loại sách: "+list.get(position).getTenLoai());

        //handling
        dao = new LoaiSachDAO(context);

        //Xoa
        btnXoa.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cảnh báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
            builder.setCancelable(true);

            builder.setPositiveButton("Đồng ý", ((dialog, which) -> {
                boolean check = dao.delete(list.get(position).getMaLoai());

                if(check){
                    list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                }
            }));

            builder.setNegativeButton("Hủy", (dialog, which) -> {
                dialog.dismiss();
            });

            builder.show();
        });

        return convertView;
    }

    public void refreshList(ArrayList<LoaiSach> list1){
        list.clear();
        list = list1;
        notifyDataSetChanged();
    }
}
