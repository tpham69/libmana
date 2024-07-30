package thaiph.ph48495.libmana.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.daos.ThuThuDAO;
import thaiph.ph48495.libmana.models.ThuThu;

public class ChangePwdFragment extends Fragment {
    SharedPreferences pref;
    TextInputEditText edtPwdOld, edtNewPwd, edtConfirmPwd;
    MaterialButton btnXacNhan, btnHuy;
    ThuThuDAO dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pwd, container, false);
        edtPwdOld = view.findViewById(R.id.edtPwdOld);
        edtNewPwd = view.findViewById(R.id.edtNewPwd);
        edtConfirmPwd = view.findViewById(R.id.edtConfirmPwd);
        btnXacNhan = view.findViewById(R.id.btnXacNhan);
        btnHuy = view.findViewById(R.id.btnHuy);

        //Nút xác nhận
        btnXacNhan.setOnClickListener(v -> {
            dao = new ThuThuDAO(getContext());
            String pwdOld = edtPwdOld.getText().toString();
            String newPwd = edtNewPwd.getText().toString();
            String conFirmPwd = edtConfirmPwd.getText().toString();


            if(!edtPwdOld.equals("") && !edtNewPwd.equals("") && !edtConfirmPwd.equals("")){

                pref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                String tk = pref.getString("tk", "");
                String mk = pref.getString("mk", "");

                if(mk.equals(pwdOld)){
                    if(newPwd.equals(conFirmPwd)){
                        ThuThu thuThu = dao.getByName(tk);
                        thuThu.setMatKhau(conFirmPwd);
                        boolean check = dao.update(thuThu);
                        if(check){
                            Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            deleteForm();
                        } else {
                            Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Mật khẩu mới không trùng nhau!", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getContext(), "Mật khẩu cũ không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(getContext(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
            }
        });


        //Nút hủy
        btnHuy.setOnClickListener(v -> {
            deleteForm();
        });

        return view;
    }

    public void deleteForm(){
        edtNewPwd.setText("");
        edtPwdOld.setText("");
        edtConfirmPwd.setText("");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}