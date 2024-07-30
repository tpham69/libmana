package thaiph.ph48495.libmana.daos;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thaiph.ph48495.libmana.database.DBhelper;
import thaiph.ph48495.libmana.models.Top;

public class ThongKeDAO {
    private DBhelper dBhelper;
    private SachDAO sachDAO;

    public ThongKeDAO (Context context){
        dBhelper = new DBhelper(context);
        sachDAO = new SachDAO(context);
    }

    public ArrayList<Top> getTop(){
        ArrayList<Top> list = new ArrayList<>();
        String sql = "SELECT S.tenSach, count(PH.maSach) as soLuong " +
                "FROM PhieuMuon AS PH " +
                "INNER JOIN Sach AS S ON S.maSach = PH.maSach " +
                "GROUP BY S.tenSach ORDER BY soLuong DESC LIMIT 10";
        SQLiteDatabase db = dBhelper.getReadableDatabase();

        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(sql, null);
            if(cursor.getColumnCount()>0){
                cursor.moveToFirst();
                do{
                    list.add(new Top(
                            cursor.getString(0),
                            cursor.getInt(1)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return list;
    }

    public int getDoanhThu(String tuNgay, String denNgay){
        ArrayList<Integer> list = new ArrayList<>();
        String SQL = "SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        SQLiteDatabase db = dBhelper.getReadableDatabase();

        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery(SQL, new String[]{tuNgay, denNgay});
            if(cursor.getColumnCount() > 0){
                cursor.moveToFirst();
                do{
                    list.add(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        return list.get(0);
    }
}
