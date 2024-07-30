package thaiph.ph48495.libmana.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.adapters.LoaiSachAdapter;
import thaiph.ph48495.libmana.daos.LoaiSachDAO;
import thaiph.ph48495.libmana.models.LoaiSach;
import thaiph.ph48495.libmana.models.ThanhVien;

public class LoaiSachFragment extends Fragment {

    private ListView lvLoaiSach;
    private LoaiSachDAO dao;
    private ArrayList<LoaiSach> list;
    private FloatingActionButton fabLS;
    private LoaiSachAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_sach, null);

        //Mapping
        lvLoaiSach = view.findViewById(R.id.lvLoaiSach);
        dao = new LoaiSachDAO(getContext());
        fabLS = view.findViewById(R.id.fabLS);

        //Set data
        list = dao.read();

        //set adapter
        adapter = new LoaiSachAdapter(getContext(), list);
        lvLoaiSach.setAdapter(adapter);

        //FAB
        fabLS.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            AlertDialog alertDialog = builder.create();
            View view1 = getLayoutInflater().inflate(R.layout.custom_dialog_loaisach, null);
            alertDialog.setView(view1);

            //mapping
            EditText edtTenLoai = view1.findViewById(R.id.edtTenLoai);
            Button btnThem = view1.findViewById(R.id.btnThemLS);
            Button btnHuy = view1.findViewById(R.id.btnHuyLS);

            //Xử lý sự kiện
            btnThem.setOnClickListener(v1 -> {
                String tenLoai = edtTenLoai.getText().toString();

                if(!tenLoai.equals("")){
                    LoaiSach tv = new LoaiSach(0,tenLoai);
                    boolean check = dao.create(tv);
                    if(check){
                        Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        list = dao.read();
                        adapter.refreshList(list);
                        alertDialog.dismiss();
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

        lvLoaiSach.setOnItemLongClickListener((parent, view12, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            AlertDialog alertDialog = builder.create();
            View view1 = getLayoutInflater().inflate(R.layout.custom_dialog_loaisach, null);
            alertDialog.setView(view1);

            //mapping
            TextView textView = view1.findViewById(R.id.tvTitleLS);
            textView.setText("Cập nhật loại sách");
            EditText edtTenLoai = view1.findViewById(R.id.edtTenLoai);
            Button btnThem = view1.findViewById(R.id.btnThemLS);
            btnThem.setText("Cập nhật");
            Button btnHuy = view1.findViewById(R.id.btnHuyLS);

            //Fill to edittext
            edtTenLoai.setText(list.get(position).getTenLoai());

            //Xử lý sự kiện
            btnThem.setOnClickListener(v1 -> {
                String tenLoai = edtTenLoai.getText().toString();
                int maLoai = list.get(position).getMaLoai();

                if(!tenLoai.equals("")){
                    LoaiSach ls = new LoaiSach(maLoai,tenLoai);
                    boolean check = dao.update(ls);
                    if(check){
                        Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        list = dao.read();
                        adapter.refreshList(list);
                        alertDialog.dismiss();
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