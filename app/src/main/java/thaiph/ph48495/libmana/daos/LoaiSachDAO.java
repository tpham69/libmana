package thaiph.ph48495.libmana.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

import java.util.ArrayList;

import thaiph.ph48495.libmana.database.DBhelper;
import thaiph.ph48495.libmana.models.LoaiSach;
import thaiph.ph48495.libmana.models.ThanhVien;

public class LoaiSachDAO {
    private final DBhelper dBhelper;

    public LoaiSachDAO(Context context){
        dBhelper = new DBhelper(context);
    }

    public ArrayList<LoaiSach> read(){
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase database = dBhelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM LoaiSach", null);
            if(cursor.getColumnCount() > 0){
                //Nếu cursor lớn hơn 0 di chuyển con trỏ lên đầu
                cursor.moveToFirst();
                //Khởi tạo vòng lặp để lấy dữ liệu
                do {
                    list.add(new LoaiSach(
                            cursor.getInt(0),
                            cursor.getString(1)
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

    public boolean create(LoaiSach ls){
        SQLiteDatabase database = dBhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tenLoai", ls.getTenLoai());

        long check = database.insert("LoaiSach", null, values);
        return check != -1;
    }

    public boolean delete(int maLoai) {
        SQLiteDatabase database = dBhelper.getWritableDatabase();
        int rowsDeleted = database.delete("LoaiSach", "maLoai = ?", new String[]{String.valueOf(maLoai)});
        return rowsDeleted > 0;
    }

    public boolean update(LoaiSach ls){
        SQLiteDatabase database = dBhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tenLoai", ls.getTenLoai());

        long rows = database.update("LoaiSach", values, "maLoai = ?", new String[]{String.valueOf(ls.getMaLoai())});
        return rows != -1;
    }

    public String getTenLoaiByMaLoai(int maLoai) {
        SQLiteDatabase database = dBhelper.getReadableDatabase();
        String tenLoai = null;

        String query = "SELECT tenLoai FROM LoaiSach WHERE maLoai = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(maLoai)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                tenLoai = cursor.getString(cursor.getColumnIndexOrThrow("tenLoai"));
            }
            cursor.close();
        }
        return tenLoai;
    }
}
