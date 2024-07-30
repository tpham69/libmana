package thaiph.ph48495.libmana.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.adapters.LoaiSachAdapter;
import thaiph.ph48495.libmana.adapters.LoaiSachSpinerAdapter;
import thaiph.ph48495.libmana.adapters.SachAdapter;
import thaiph.ph48495.libmana.daos.LoaiSachDAO;
import thaiph.ph48495.libmana.daos.SachDAO;
import thaiph.ph48495.libmana.models.LoaiSach;
import thaiph.ph48495.libmana.models.Sach;
import thaiph.ph48495.libmana.models.ThanhVien;


public class SachFragment extends Fragment {
    String TAG = "zzzzzzzzzzzz";
    private ListView lvSach;
    private SachDAO dao;
    private ArrayList<Sach> list;
    private FloatingActionButton fabSach;
    private SachAdapter adapter;
    private LoaiSachSpinerAdapter spinnerAdapter;
    private ArrayList<LoaiSach> listSpinner;
    private LoaiSachDAO loaiSachDAO;

    private int maLoai = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach, container, false);

        //Mapping
        lvSach = view.findViewById(R.id.lvSach);
        dao = new SachDAO(getContext());
        fabSach = view.findViewById(R.id.fabSach);
        loaiSachDAO = new LoaiSachDAO(getContext());

        //Set data
        list = dao.read();
        listSpinner = loaiSachDAO.read();

        //Set adapter
        adapter = new SachAdapter(getContext(), list);
        lvSach.setAdapter(adapter);

        //Handling event
        fabSach.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            AlertDialog alertDialog = builder.create();
            View view1 = getLayoutInflater().inflate(R.layout.custom_dialog_sach, null);
            alertDialog.setView(view1);

            //mapping
            EditText edtTenSach = view1.findViewById(R.id.edtTenSach);
            EditText edtGiaThue = view1.findViewById(R.id.edtGiaThue);
            Spinner spinLoaiSach = view1.findViewById(R.id.spinLoaiSach);
            Button btnThem = view1.findViewById(R.id.btnThemLS);
            Button btnHuy = view1.findViewById(R.id.btnHuyLS);


            //set adapter spinner
            spinnerAdapter = new LoaiSachSpinerAdapter(getContext(), listSpinner);
            spinLoaiSach.setAdapter(spinnerAdapter);

            //Xử lý sự kiện
            //Spinner listener

            spinLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    maLoai = listSpinner.get(position).getMaLoai();
                    Toast.makeText(getContext(), "Chọn "+listSpinner.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Thêm button
            btnThem.setOnClickListener(v1 -> {
                String tenSach = edtTenSach.getText().toString();
                String giaThue = edtGiaThue.getText().toString();

                if(!tenSach.equals("") && !giaThue.equals("")){
                    Sach sach = new Sach(0, tenSach, Integer.parseInt(giaThue), maLoai);
                    Log.d(TAG, "onCreateView: maLoai: "+maLoai);
                    boolean check = dao.create(sach);
                    if(check){
                        list = dao.read();
                        adapter.refreshList(list);
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "Thêm sách thành công!", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Không để trống!", Toast.LENGTH_SHORT).show();
                }
            });

            btnHuy.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });

            alertDialog.show();
        }); //fab

        //Update
        lvSach.setOnItemLongClickListener((parent, view12, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            AlertDialog alertDialog = builder.create();
            View view1 = getLayoutInflater().inflate(R.layout.custom_dialog_sach, null);
            alertDialog.setView(view1);

            //mapping
            EditText edtTenSach = view1.findViewById(R.id.edtTenSach);
            EditText edtGiaThue = view1.findViewById(R.id.edtGiaThue);
            Spinner spinLoaiSach = view1.findViewById(R.id.spinLoaiSach);
            Button btnThem = view1.findViewById(R.id.btnThemLS);
            btnThem.setText("Cập nhật");
            Button btnHuy = view1.findViewById(R.id.btnHuyLS);
            TextView tvTitleSach = view1.findViewById(R.id.tvTitleSach);
            tvTitleSach.setText("Cập nhật sách");

            //set adapter spinner
            spinnerAdapter = new LoaiSachSpinerAdapter(getContext(), listSpinner);
            spinLoaiSach.setAdapter(spinnerAdapter);

            //fill data to edittext
            edtTenSach.setText(list.get(position).getTenSach());
            edtGiaThue.setText(list.get(position).getGiaThue()+"");
            int loaiSach = list.get(position).getMaLoai();
            int viTri = 0;
            for(LoaiSach ls : listSpinner){
                if(ls.getMaLoai() == loaiSach){
                    viTri = listSpinner.indexOf(ls);
                    break;
                }
            }
            spinLoaiSach.setSelection(viTri);


            //Spinner
            spinLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    maLoai = listSpinner.get(position).getMaLoai();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Cập nhật
            btnThem.setOnClickListener(v -> {
                String tenSach = edtTenSach.getText().toString();
                String giaThue = edtGiaThue.getText().toString();
                int maSach = list.get(position).getMaSach();

                if(!tenSach.equals("") && !giaThue.equals("")){
                    Sach sach = new Sach(maSach, tenSach, Integer.parseInt(giaThue), maLoai);


                    boolean check = dao.update(sach);
                    if(check){
                        list = dao.read();
                        adapter.refreshList(list);
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "Cật nhật sách thành công!", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getContext(), "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Không để trống!", Toast.LENGTH_SHORT).show();
                }
            });
            //Hủy
            btnHuy.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });
            alertDialog.show();
            return true;
        });
        return view;
    }
}