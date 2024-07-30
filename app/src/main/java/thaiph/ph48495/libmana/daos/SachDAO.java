package thaiph.ph48495.libmana.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thaiph.ph48495.libmana.database.DBhelper;
import thaiph.ph48495.libmana.models.LoaiSach;
import thaiph.ph48495.libmana.models.Sach;

public class SachDAO {
    private final DBhelper dBhelper;

    public SachDAO(Context context){
        dBhelper = new DBhelper(context);
    }

    public ArrayList<Sach> read(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase database = dBhelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM Sach", null);
            if(cursor.getColumnCount() > 0){
                cursor.moveToFirst();
                //Khởi tạo vòng lặp để lấy dữ liệu
                do {
                    list.add(new Sach(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3)
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }

        return list;
    }

    public boolean create(Sach s){
        SQLiteDatabase database = dBhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tenSach", s.getTenSach());
        values.put("giaThue", s.getGiaThue());
        values.put("maLoai", s.getMaLoai());

        long check = database.insert("Sach", null, values);
        return check != -1;
    }

    public boolean delete(int maSach) {
        SQLiteDatabase database = dBhelper.getWritableDatabase();
        int rowsDeleted = database.delete("Sach", "maSach = ?", new String[]{String.valueOf(maSach)});
        return rowsDeleted > 0;
    }

    public boolean update(Sach s){
        SQLiteDatabase database = dBhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tenSach", s.getTenSach());
        values.put("giaThue", s.getGiaThue());
        values.put("maLoai", s.getMaLoai());

        long rows = database.update("Sach", values, "maSach = ?", new String[]{String.valueOf(s.getMaSach())});
        return rows != -1;
    }

    public String getTenSachByMaSach(int maSach){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase database = dBhelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT tenSach FROM Sach WHERE maSach = ?", new String[]{String.valueOf(maSach)});
            if(cursor.getColumnCount() > 0){
                cursor.moveToFirst();
                do {
                    list.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }

        return list.get(0);
    }
}
