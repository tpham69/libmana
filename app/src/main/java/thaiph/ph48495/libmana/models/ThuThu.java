package thaiph.ph48495.libmana.models;

public class ThuThu {
   private String TT, hoTen, matKhau;

    public ThuThu(String TT, String hoTen, String matKhau) {
        this.TT = TT;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
    }

    public String getTT() {
        return TT;
    }

    public void setTT(String TT) {
        this.TT = TT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
