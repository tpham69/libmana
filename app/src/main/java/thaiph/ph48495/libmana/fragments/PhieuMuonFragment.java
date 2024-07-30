package thaiph.ph48495.libmana.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.adapters.PhieuMuonAdapter;
import thaiph.ph48495.libmana.adapters.SachSpinnerAdapter;
import thaiph.ph48495.libmana.adapters.ThanhVienSpinnerAdapter;
import thaiph.ph48495.libmana.daos.PhieuMuonDAO;
import thaiph.ph48495.libmana.daos.SachDAO;
import thaiph.ph48495.libmana.daos.ThanhVienDAO;
import thaiph.ph48495.libmana.models.LoaiSach;
import thaiph.ph48495.libmana.models.PhieuMuon;
import thaiph.ph48495.libmana.models.Sach;
import thaiph.ph48495.libmana.models.ThanhVien;


public class PhieuMuonFragment extends Fragment {
    private PhieuMuonAdapter adapter;
    private ArrayList<PhieuMuon> list;
    private ListView lvPhieuMuon;
    private PhieuMuonDAO dao;
    private ThanhVienDAO thanhVienDAO;
    private SachDAO sachDAO;

    private SachSpinnerAdapter sachSpinnerAdapter;
    private ArrayList<Sach> listSach;

    private ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    private ArrayList<ThanhVien> listThanhVien;


    FloatingActionButton fab;
    Spinner spinThanhVien, spinSach;
    int maTV = 0, maSach = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);

        //Mapping
        fab = view.findViewById(R.id.fabPM);
        lvPhieuMuon = view.findViewById(R.id.lvPhieuMuon);
        dao = new PhieuMuonDAO(getContext());
        thanhVienDAO = new ThanhVienDAO(getContext());
        sachDAO = new SachDAO(getContext());


        //get data
        list = dao.read();
        listThanhVien = thanhVienDAO.read();
        listSach = sachDAO.read();

        //set adapter
        adapter = new PhieuMuonAdapter(getContext(), list);
        lvPhieuMuon.setAdapter(adapter);

        //FAB
        fab.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            AlertDialog alertDialog = builder.create();
            View view1 = getLayoutInflater().inflate(R.layout.custom_dialog_phieumuon, null);
            alertDialog.setView(view1);

            //mapping
            EditText edtNgayThue = view1.findViewById(R.id.edtNgayThue);
            EditText edtTienThuePM = view1.findViewById(R.id.edtTienThuePM);
            CheckBox chkTrangThai = view1.findViewById(R.id.chkTrangThai);
            Button btnThem = view1.findViewById(R.id.btnThemPM);
            Button btnHuy = view1.findViewById(R.id.btnHuyPM);
            spinThanhVien = view1.findViewById(R.id.spinThanhVien);
            spinSach = view1.findViewById(R.id.spinSach);


            //Xử lý spinner
            thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(getContext(), listThanhVien);
            spinThanhVien.setAdapter(thanhVienSpinnerAdapter);
            sachSpinnerAdapter = new SachSpinnerAdapter(getContext(), listSach);
            spinSach.setAdapter(sachSpinnerAdapter);

            //Listen spinner
            spinThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    maTV = listThanhVien.get(position).getMaTV();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    maSach = listSach.get(position).getMaSach();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Regex
            String regex = "^\\d{4}-([1-9]|1[0-2])-([1-9]|[12][0-9]|3[01])$";

            //Xử lý sự kiện
            btnThem.setOnClickListener(v1 -> {
                String ngayThue = edtNgayThue.getText().toString();
                String tienThue = edtTienThuePM.getText().toString();
                String maTT = getMaTT();
                int trangThai = chkTrangThai.isChecked() ? 1 : 0;

                if(!ngayThue.equals("") && !tienThue.equals("")){
                    if(ngayThue.matches(regex)){
                        PhieuMuon pm = new PhieuMuon(0, maTT, maTV, maSach, Integer.parseInt(tienThue), ngayThue, trangThai);
                        boolean check = dao.create(pm);
                        if (check){
                            Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            list = dao.read();
                            adapter.refreshList(list);
                            alertDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(getContext(), "Sai định dạng năm - tháng - ngày. Vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                        edtNgayThue.setText("");
                    }
                } else{
                    Toast.makeText(getContext(), "Không để trống!", Toast.LENGTH_SHORT).show();
                }
            });

            btnHuy.setOnClickListener(v1 -> {
                alertDialog.dismiss();
            });

            alertDialog.show();
        }); //fab


        //Update
        lvPhieuMuon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                updateOnDialog(position);
                return true;
            }
        });

        return view;
    }

    public String getMaTT(){
        SharedPreferences pref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        return pref.getString("maTT", "");
    }

    public void updateOnDialog(int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog alertDialog = builder.create();
        View view1 = getLayoutInflater().inflate(R.layout.custom_dialog_phieumuon, null);
        alertDialog.setView(view1);

        //mapping
        TextView tvTitlePM = view1.findViewById(R.id.tvTitlePM);
        EditText edtNgayThue = view1.findViewById(R.id.edtNgayThue);
        EditText edtTienThuePM = view1.findViewById(R.id.edtTienThuePM);
        CheckBox chkTrangThai = view1.findViewById(R.id.chkTrangThai);
        Button btnThem = view1.findViewById(R.id.btnThemPM);
        btnThem.setText("Cập nhật");
        Button btnHuy = view1.findViewById(R.id.btnHuyPM);
        spinThanhVien = view1.findViewById(R.id.spinThanhVien);
        spinSach = view1.findViewById(R.id.spinSach);

        //set adapter spinner
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(getContext(), listThanhVien);
        spinThanhVien.setAdapter(thanhVienSpinnerAdapter);
        sachSpinnerAdapter = new SachSpinnerAdapter(getContext(), listSach);
        spinSach.setAdapter(sachSpinnerAdapter);

        //Fill to edittext
        tvTitlePM.setText("Cập nhật Phiếu mượn");
        edtNgayThue.setText(list.get(pos).getNgayString());
        edtTienThuePM.setText(list.get(pos).getTienThue()+"");
        boolean trangThaiCB = list.get(pos).getTraSach() == 1? true : false;
        chkTrangThai.setChecked(trangThaiCB);

        //Fill spinner data to dialog
        int indexSach = 0;
        for (int i = 0; i < listSach.size(); i++) {
            if (listSach.get(i).getMaSach() == list.get(pos).getMaSach()) {
                indexSach = i;
                break;
            }
        }
        spinSach.setSelection(indexSach);

        int indexTV = 0;
        for (int i = 0; i < listThanhVien.size(); i++) {
            if (listThanhVien.get(i).getMaTV() == list.get(pos).getMaTV()) {
                indexTV = i;
                break;
            }
        }
        spinThanhVien.setSelection(indexTV);


        //Listen spinner
        spinThanhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTV = listThanhVien.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //------------------------------------------------------------------------------------------
        //Regex
        String regex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

        //Xử lý sự kiện
        btnThem.setOnClickListener(v1 -> {
            String ngayThue = edtNgayThue.getText().toString();
            String tienThue = edtTienThuePM.getText().toString();
            int maPM = list.get(pos).getMaPM();
            String maTT = getMaTT();
            int trangThai = chkTrangThai.isChecked() ? 1 : 0;

            if(!ngayThue.equals("") && !tienThue.equals("")){
                if(ngayThue.matches(regex)){
                    PhieuMuon pm = new PhieuMuon(maPM, maTT, maTV, maSach, Integer.parseInt(tienThue), ngayThue, trangThai);
                    boolean check = dao.update(pm);
                    if (check){
                        Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        list = dao.read();
                        adapter.refreshList(list);
                        alertDialog.dismiss();
                    } else{
                        Toast.makeText(getContext(), "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Sai định dạng năm - tháng - ngày. Vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                    edtNgayThue.setText("");
                }
            } else{
                Toast.makeText(getContext(), "Không để trống!", Toast.LENGTH_SHORT).show();
            }
        });

        btnHuy.setOnClickListener(v1 -> {
            alertDialog.dismiss();
        });

        alertDialog.show();
    }
}