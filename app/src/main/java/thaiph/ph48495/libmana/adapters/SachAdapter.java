package thaiph.ph48495.libmana.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.daos.LoaiSachDAO;
import thaiph.ph48495.libmana.daos.SachDAO;
import thaiph.ph48495.libmana.models.LoaiSach;
import thaiph.ph48495.libmana.models.Sach;

public class SachAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Sach> list;

    private SachDAO dao;
    private LoaiSachDAO loaiSachDAO;

    public SachAdapter(Context context, ArrayList<Sach> list) {
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
        convertView = inflater.inflate(R.layout.sach_item, parent, false);

        //mapping
        TextView tvMaSach = convertView.findViewById(R.id.tvMaSach);
        TextView tvTenSach = convertView.findViewById(R.id.tvTenSach);
        TextView tvGiaThue = convertView.findViewById(R.id.tvGiaThue);
        TextView tvLoaiSach = convertView.findViewById(R.id.tvLoaiSach);
        ImageView btnXoa = convertView.findViewById(R.id.btnXoa);

        dao = new SachDAO(context);
        //set data
        tvMaSach.setText("Mã sách: "+list.get(position).getMaSach());
        tvTenSach.setText("Tên sách: "+list.get(position).getTenSach());
        tvGiaThue.setText("Giá thuê: "+list.get(position).getGiaThue());
        loaiSachDAO = new LoaiSachDAO(context);
        int maLoai = list.get(position).getMaLoai();
        String tenLoai = loaiSachDAO.getTenLoaiByMaLoai(maLoai);
        tvLoaiSach.setText("Loại sách: "+maLoai+". "+tenLoai);

        //Xóa
        btnXoa.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cảnh báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
            builder.setCancelable(true);

            builder.setPositiveButton("Đồng ý", ((dialog, which) -> {
                boolean check = dao.delete(list.get(position).getMaSach());

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

    public void refreshList(ArrayList<Sach> list1){
        list.clear();
        list = list1;
        notifyDataSetChanged();
    }
}
