package thaiph.ph48495.libmana.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import thaiph.ph48495.libmana.R;
import thaiph.ph48495.libmana.fragments.ChangePwdFragment;
import thaiph.ph48495.libmana.fragments.DoanhThuFragment;
import thaiph.ph48495.libmana.fragments.LoaiSachFragment;
import thaiph.ph48495.libmana.fragments.PhieuMuonFragment;
import thaiph.ph48495.libmana.fragments.SachFragment;
import thaiph.ph48495.libmana.fragments.ThanhVienFragment;
import thaiph.ph48495.libmana.fragments.ThemNguoiDungFragment;
import thaiph.ph48495.libmana.fragments.Top10Fragment;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;
    View headerView;
    TextView txtUser;
    NavigationView nvView;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Ánh xạ
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        nvView = findViewById(R.id.nvView);

        //Set toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_24);
        getSupportActionBar().setTitle("Quản lý Phiếu mượn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Navigation View
        headerView = nvView.getHeaderView(0);
        txtUser = headerView.findViewById(R.id.txtUser);
        Intent i = getIntent();
        String username = i.getStringExtra("username");
        txtUser.setText("Xin chào "+username+"!");

        //Navigation
        fragmentManager = getSupportFragmentManager();
        setTitleAndFragment("Quản lý phiếu mượn", new PhieuMuonFragment()); //Home screen
        nvView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_PhieuMuon) {
                    setTitleAndFragment("Quản lý Phiếu mượn", new PhieuMuonFragment());
                } else if (item.getItemId() == R.id.nav_LoaiSach) {
                    setTitleAndFragment("Quản lý loại sách", new LoaiSachFragment());
                } else if (item.getItemId() == R.id.nav_Sach) {
                    setTitleAndFragment("Quản lý sách", new SachFragment());
                } else if (item.getItemId() == R.id.nav_Thanhvien) {
                    setTitleAndFragment("Quản lý Thành viên", new ThanhVienFragment());
                } else if (item.getItemId() == R.id.sub_Top) {
                    setTitleAndFragment("Top 10 sách cho mượn", new Top10Fragment());
                } else if (item.getItemId() == R.id.sub_Doanhthu) {
                    setTitleAndFragment("Doanh thu", new DoanhThuFragment());
                } else if (item.getItemId() == R.id.sub_Pass) {
                    setTitleAndFragment("Đổi mật khẩu", new ChangePwdFragment());
                } else if (item.getItemId() == R.id.sub_addUser) {
                    setTitleAndFragment("Thêm người dùng", new ThemNguoiDungFragment());
                } else if (item.getItemId() == R.id.sub_Logout) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("stt", "logout");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }

                drawer.closeDrawers(); //when clicked then close
                return true;
            }
        });

        if (username.equalsIgnoreCase("admin")) {
            nvView.getMenu().findItem(R.id.sub_addUser).setVisible(true);
        }

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() == android.R.id.home){
                drawer.openDrawer(GravityCompat.START);
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setTitleAndFragment(String tile, Fragment fragment){
        getSupportActionBar().setTitle(tile);
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

}