package thaiph.ph48495.libmana.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.daos.ThuThuDAO;
import thaiph.ph48495.libmana.models.ThuThu;

public class LoginActivity extends AppCompatActivity {

    String TAG = "zzzzzzzzz";
    TextInputLayout tilTaiKhoan, tilMatKhau;
    TextInputEditText edtTaiKhoan, edtMatKhau;
    MaterialButton btnDangNhap, btnHuy;
    CheckBox chkGhiNho;

    ArrayList<ThuThu> list;

    String taiKhoan, matKhau;

    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Ánh xạ
        tilTaiKhoan = findViewById(R.id.tilTaiKhoan);
        tilMatKhau = findViewById(R.id.tilMatKhau);
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnHuy = findViewById(R.id.btnHuy);
        chkGhiNho = findViewById(R.id.chkGhiNho);

        //Lấy dữ liêu từ SQL
        ThuThuDAO thuThuDAO = new ThuThuDAO(LoginActivity.this);

        list = thuThuDAO.read();


        //Lấy dự liệu từ SharePreferences và điền lên EditText
        checkRemember();

        //Sự kiện đăng nhập
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taiKhoanEDT = edtTaiKhoan.getText().toString();
                String matKhauEDT = edtMatKhau.getText().toString();
                boolean ghiNho = chkGhiNho.isChecked();
                boolean check = false;
                String maTT = "";

                if(!taiKhoanEDT.equals("") && !matKhauEDT.equals("")){
                    for (ThuThu tt : list) {
                        if(tt.getHoTen().equals(taiKhoanEDT) && tt.getMatKhau().equals(matKhauEDT)){
                            check = true;
                            maTT = tt.getTT();
                            break;
                        }
                    }

                    if(check){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        remember(maTT, taiKhoanEDT, matKhauEDT, ghiNho);
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        i.putExtra("username", taiKhoanEDT);
                        startActivity(i);
                    } else{
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Sự kiện nút hủy
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTaiKhoan.setText("");
                edtMatKhau.setText("");
                chkGhiNho.setChecked(false);
            }
        });


        //set null when user click logout
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            edtTaiKhoan.setText("");
            edtMatKhau.setText("");
            chkGhiNho.setChecked(false);
        }

    }


    public void checkRemember(){
        pref = getSharedPreferences("user", MODE_PRIVATE);
        taiKhoan = pref.getString("tk", "");
        matKhau = pref.getString("mk", "");
        boolean chkGhiNho1 = pref.getBoolean("ghinho", false);
        chkGhiNho.setChecked(chkGhiNho1);
        if(chkGhiNho.isChecked()){
            edtTaiKhoan.setText(taiKhoan);
            edtMatKhau.setText(matKhau);
        }
    }

    public void remember(String maTT, String tk, String mk, boolean chkGhiNho){
        pref = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("maTT", maTT);
        editor.putString("tk",tk);
        editor.putString("mk",mk);
        editor.putBoolean("ghinho", chkGhiNho);
        editor.apply();
    }
}