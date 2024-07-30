package thaiph.ph48495.libmana.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import thaiph.ph48495.libmana.daos.ThanhVienDAO;
import thaiph.ph48495.libmana.fragments.ThanhVienFragment;
import thaiph.ph48495.libmana.models.ThanhVien;

public class ThanhVienAdapter extends BaseAdapter {
    String TAG = "zzzzzzzz";

    private Context context;
    private ArrayList<ThanhVien> list;

    private ThanhVienDAO dao;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.thanhvien_item, parent, false);

        //Mapping
        TextView tvMaTv = convertView.findViewById(R.id.tvMaTV);
        TextView tvHoTen = convertView.findViewById(R.id.tvHoten);
        TextView tvNamSinh = convertView.findViewById(R.id.tvNamSinh);
        ImageView btnXoa = convertView.findViewById(R.id.btnXoa);

        //Set data
        tvMaTv.setText("Mã Thành viên: "+list.get(position).getMaTV());
        tvHoTen.setText("Họ tên: "+list.get(position).getHoTen());
        tvNamSinh.setText("Năm sinh: "+list.get(position).getNamSinh());

        //event delete
        dao = new ThanhVienDAO(context);
        btnXoa.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cảnh báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
            builder.setCancelable(true);

            builder.setPositiveButton("Đồng ý", ((dialog, which) -> {
                boolean check = dao.delete(list.get(position).getMaTV());

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

    public void refreshList(ArrayList<ThanhVien> list1){
        list.clear();
        list = list1;
        notifyDataSetChanged();
    }
}
