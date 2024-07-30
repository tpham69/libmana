package thaiph.ph48495.libmana.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thaiph.ph48495.libmana.database.DBhelper;
import thaiph.ph48495.libmana.models.PhieuMuon;

public class PhieuMuonDAO {
    private final DBhelper dBhelper;

    public PhieuMuonDAO(Context context){
        dBhelper = new DBhelper(context);
    }

    public ArrayList<PhieuMuon> read(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase database = dBhelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM PhieuMuon", null);
            if(cursor.getColumnCount() > 0){

                cursor.moveToFirst();
                //Khởi tạo vòng lặp để lấy dữ liệu
                do {
                    list.add(new PhieuMuon(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getString(5),
                            cursor.getInt(6)
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

    public boolean create(PhieuMuon pm){
        SQLiteDatabase database = dBhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("maTT", pm.getMaTT());
        values.put("maTV", pm.getMaTV());
        values.put("maSach", pm.getMaSach());
        values.put("tienThue", pm.getTienThue());
        values.put("ngay", pm.getNgayString());
        values.put("traSach", pm.getTraSach());

        long check = database.insert("PhieuMuon", null, values);
        return check != -1;
    }

    public boolean delete(int maPM) {
        SQLiteDatabase database = dBhelper.getWritableDatabase();
        int rowsDeleted = database.delete("PhieuMuon", "maPM = ?", new String[]{String.valueOf(maPM)});
        return rowsDeleted > 0;
    }

    public boolean update(PhieuMuon pm){
        SQLiteDatabase database = dBhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("maTT", pm.getMaTT());
        values.put("maTV", pm.getMaTV());
        values.put("maSach", pm.getMaSach());
        values.put("tienThue", pm.getTienThue());
        values.put("ngay", pm.getNgayString());
        values.put("traSach", pm.getTraSach());

        long rows = database.update("PhieuMuon", values, "maPM = ?", new String[]{String.valueOf(pm.getMaPM())});
        return rows != -1;
    }

}
