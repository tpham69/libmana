package thaiph.ph48495.libmana.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.adapters.ThanhVienAdapter;
import thaiph.ph48495.libmana.daos.ThanhVienDAO;
import thaiph.ph48495.libmana.models.ThanhVien;


public class ThanhVienFragment extends Fragment {

    private ListView lvThanhVien;
    private ThanhVienAdapter adapter;
    private ArrayList<ThanhVien> list;

    private ThanhVienDAO dao;

    private FloatingActionButton fab;

    String regex = "\\d{4}";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        lvThanhVien = view.findViewById(R.id.lvThanhVien);
        dao = new ThanhVienDAO(getContext());
        fab = view.findViewById(R.id.fab);

        //lấy dữ liệu
        list = dao.read();

        //adapter
        adapter = new ThanhVienAdapter(getContext(), list);
        lvThanhVien.setAdapter(adapter);

        //floating action button
        fab.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            AlertDialog alertDialog = builder.create();
            View view1 = getLayoutInflater().inflate(R.layout.custom_dialog_thanhvien, null);
            alertDialog.setView(view1);

            //mapping
            EditText edtHoTen = view1.findViewById(R.id.edtHoTen);
            EditText edtNamSinh = view1.findViewById(R.id.edtNamSinh);
            Button btnThemTV = view1.findViewById(R.id.btnThemTV);
            Button btnHuy = view1.findViewById(R.id.btnHuy);

            //Xử lý sự kiện
            btnThemTV.setOnClickListener(v1 -> {
                String hoTen = edtHoTen.getText().toString();
                String namSinh = edtNamSinh.getText().toString();

                if(!hoTen.equals("") && !namSinh.equals("")){
                    if(namSinh.matches(regex)){
                        ThanhVien tv = new ThanhVien(0,hoTen, namSinh);
                        boolean check = dao.create(tv);
                        if(check){
                            Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            list = dao.read();
                            adapter.refreshList(list);
                            alertDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(getContext(), "Năm sinh không hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getContext(), "Không để trống!", Toast.LENGTH_SHORT).show();
                }
            });

            btnHuy.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });

            alertDialog.show();
        });

        //Update
        lvThanhVien.setOnItemLongClickListener((parent, view12, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            AlertDialog alertDialog = builder.create();
            View view1 = getLayoutInflater().inflate(R.layout.custom_dialog_thanhvien, null);
            alertDialog.setView(view1);

            //mapping
            TextView textView = view1.findViewById(R.id.tvTitleTV);
            textView.setText("Cập nhật Thành viên");
            EditText edtHoTen = view1.findViewById(R.id.edtHoTen);
            EditText edtNamSinh = view1.findViewById(R.id.edtNamSinh);
            Button btnThemTV = view1.findViewById(R.id.btnThemTV);
            btnThemTV.setText("Cập nhật");
            Button btnHuy = view1.findViewById(R.id.btnHuy);

            //Fill to the edittext
            edtHoTen.setText(list.get(position).getHoTen());
            edtNamSinh.setText(list.get(position).getNamSinh());

            //Xử lý sự kiện
            btnThemTV.setOnClickListener(v1 -> {
                String hoTen = edtHoTen.getText().toString();
                String namSinh = edtNamSinh.getText().toString();
                int maTV = list.get(position).getMaTV();

                if(!hoTen.equals("") && !namSinh.equals("")){
                    if(namSinh.matches(regex)){
                        ThanhVien tv = new ThanhVien(maTV,hoTen, namSinh);
                        boolean check = dao.update(tv);
                        if(check){
                            Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            list = dao.read();
                            adapter.refreshList(list);
                            alertDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(getContext(), "Năm sinh không hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getContext(), "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            });

            btnHuy.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });

            alertDialog.show();
            return true;
        });

        return view;
    }

}