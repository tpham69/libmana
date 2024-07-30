package thaiph.ph48495.libmana.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thaiph.ph48495.libmana.database.DBhelper;
import thaiph.ph48495.libmana.models.ThanhVien;
import thaiph.ph48495.libmana.models.ThuThu;

public class ThanhVienDAO {
    private final DBhelper dBhelper;

    public ThanhVienDAO(Context context){
        dBhelper = new DBhelper(context);
    }

    public ArrayList<ThanhVien> read(){
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase database = dBhelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM ThanhVien", null);
            if(cursor.getColumnCount() > 0){
                //Nếu cursor lớn hơn 0 di chuyển con trỏ lên đầu
                cursor.moveToFirst();
                //Khởi tạo vòng lặp để lấy dữ liệu
                do {
                    list.add(new ThanhVien(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2)
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

    public boolean create(ThanhVien tv){
        SQLiteDatabase database = dBhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("hoTen", tv.getHoTen());
        values.put("namSinh", tv.getNamSinh());

        long check = database.insert("ThanhVien", null, values);
        return check != -1;
    }

    public boolean delete(int maTV) {
        SQLiteDatabase database = dBhelper.getWritableDatabase();
        int rowsDeleted = database.delete("ThanhVien", "maTV = ?", new String[]{String.valueOf(maTV)});
        return rowsDeleted > 0;
    }

    public boolean update(ThanhVien tv){
        SQLiteDatabase database = dBhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("hoTen", tv.getHoTen());
        values.put("namSinh", tv.getNamSinh());

        long rows = database.update("ThanhVien", values, "maTV = ?", new String[]{String.valueOf(tv.getMaTV())});
        return rows != -1;
    }

    public String getTVbyMaTV(int maTV){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase database = dBhelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT hoTen FROM ThanhVien WHERE maTV = ?", new String[]{String.valueOf(maTV)});
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
