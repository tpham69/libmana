package thaiph.ph48495.libmana.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhieuMuon {
    private int maPM, maTV, maSach, tienThue, traSach;
    private String maTT;

    private Date ngay;

    public PhieuMuon(int maPM, String maTT, int maTV, int maSach, int tienThue, String ngay, int traSach) {
        this.maPM = maPM;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.traSach = traSach;
        this.maTT = maTT;
        this.ngay = parseDate(ngay);
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public Date getNgay() {
        return ngay;
    }

    public String getNgayString(){
        return formatDate(ngay);
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }


    //helper
    private Date parseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (date != null) {
            return format.format(date);
        } else {
            return null;
        }
    }
}
