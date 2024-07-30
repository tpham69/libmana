package thaiph.ph48495.libmana.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.daos.ThuThuDAO;
import thaiph.ph48495.libmana.models.ThanhVien;
import thaiph.ph48495.libmana.models.ThuThu;


public class ThemNguoiDungFragment extends Fragment {
    Button btnDangKy, btnQuayLai;
    TextInputLayout tilTaiKhoan, tilMatKhau, tilXacNhanMK;
    TextInputEditText tiedtTaiKhoan, tiedtMatKhau, tiedtXacNhanMK;
    String xnmk = "";
    ThuThuDAO dao;
    ArrayList<ThuThu> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_nguoi_dung, container, false);

        //Mapping
        btnDangKy = view.findViewById(R.id.btnDangKy);
        btnQuayLai = view.findViewById(R.id.btnQuayLai);
        tilTaiKhoan = view.findViewById(R.id.tilTaiKhoan);
        tilMatKhau = view.findViewById(R.id.tilMatKhau);
        tilXacNhanMK = view.findViewById(R.id.tilXacNhanMK);
        tiedtTaiKhoan = view.findViewById(R.id.tiedtTaiKhoan);
        tiedtMatKhau = view.findViewById(R.id.tiedtMatKhau);
        tiedtXacNhanMK = view.findViewById(R.id.tiedtXacNhanMK);
        dao = new ThuThuDAO(getActivity());

        //get data from ThuThu
        list = dao.read();

        //Handling event
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taiKhoan = tiedtTaiKhoan.getText().toString();
                String matKhau = tiedtMatKhau.getText().toString();
                String xacNhanMK = tiedtXacNhanMK.getText().toString();
                boolean check = true;

                if(!taiKhoan.equals("") && !matKhau.equals("") && !xacNhanMK.equals("") && !xacNhanMK.equals("")){
                    for (ThuThu tt : list){
                        if(tt.getHoTen().equals(taiKhoan)){
                            Toast.makeText(getActivity(), "Tài khoản này đã tồn tại!", Toast.LENGTH_SHORT).show();
                            check = false;
                            tiedtMatKhau.setText("");
                            tiedtXacNhanMK.setText("");
                            break;
                        }
                    }

                    if(!matKhau.equals(xacNhanMK)){
                        check = false;
                        Toast.makeText(getActivity(), "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                    }

                    if(check){
                        Toast.makeText(getActivity(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        ThuThu tt = new ThuThu("", taiKhoan, xacNhanMK);
                        dao.create(tt);
                        tiedtTaiKhoan.setText("");
                        tiedtMatKhau.setText("");
                        tiedtXacNhanMK.setText("");
                    }
                } else {
                    Toast.makeText(getActivity(), "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiedtTaiKhoan.setText("");
                tiedtMatKhau.setText("");
                tiedtXacNhanMK.setText("");
            }
        });

        return view;
    }
}