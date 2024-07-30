package thaiph.ph48495.libmana.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.PhantomReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.daos.LoaiSachDAO;
import thaiph.ph48495.libmana.daos.PhieuMuonDAO;
import thaiph.ph48495.libmana.daos.SachDAO;
import thaiph.ph48495.libmana.daos.ThanhVienDAO;
import thaiph.ph48495.libmana.models.PhieuMuon;
import thaiph.ph48495.libmana.models.ThanhVien;

public class PhieuMuonAdapter extends BaseAdapter {
    String TAG = "zzzzzzzzzz";
    private Context context;
    private ArrayList<PhieuMuon> list;

    private PhieuMuonDAO dao;

    private SachDAO sachDAO;

    private ThanhVienDAO thanhVienDAO;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        dao = new PhieuMuonDAO(context);
        sachDAO = new SachDAO(context);
        thanhVienDAO = new ThanhVienDAO(context);
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
        convertView = inflater.inflate(R.layout.phieumuon_item, parent, false);

        //Mapping
        TextView tvMaPH = convertView.findViewById(R.id.tvMaPH);
        TextView tvThanhVien = convertView.findViewById(R.id.tvThanhVien);
        TextView tvTenSachPM = convertView.findViewById(R.id.tvTenSachPM);
        TextView tvGiaThue = convertView.findViewById(R.id.tvTienThuePM);
        TextView tvTrangThai = convertView.findViewById(R.id.tvTrangThai);
        TextView tvNgayThue = convertView.findViewById(R.id.tvNgayThue);
        ImageView btnXoa = convertView.findViewById(R.id.btnXoa);

        //Set data
        tvMaPH.setText("Mã Phiếu mượn: "+list.get(position).getMaPM());

        String tenTV = thanhVienDAO.getTVbyMaTV(list.get(position).getMaTV());
        tvThanhVien.setText("Thành viên: "+tenTV);
        String tenSach = sachDAO.getTenSachByMaSach(list.get(position).getMaSach());
        tvTenSachPM.setText("Sách: "+tenSach);
        tvGiaThue.setText("Giá thuê: "+list.get(position).getTienThue());

        String trangThai = list.get(position).getTraSach() == 1? "Đã trả sách" : "Chưa trả sách";
        int colorTrangThai = trangThai == "Đã trả sách"? Color.BLUE : Color.RED;
        tvTrangThai.setTextColor(colorTrangThai);
        tvTrangThai.setText(trangThai);
        tvNgayThue.setText("Ngày thuê: "+list.get(position).getNgayString());


        //Xóa
        btnXoa.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cảnh báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
            builder.setCancelable(true);

            builder.setPositiveButton("Đồng ý", ((dialog, which) -> {
                boolean check = dao.delete(list.get(position).getMaPM());
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

    public void refreshList(ArrayList<PhieuMuon> list1){
        list.clear();
        list = list1;
        notifyDataSetChanged();
    }
}
