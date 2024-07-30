package thaiph.ph48495.libmana.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import thaiph.ph48495.libmana.database.DBhelper;
import thaiph.ph48495.libmana.models.ThuThu;

public class ThuThuDAO {
    private final DBhelper dBhelper;

    public ThuThuDAO(Context context){
        this.dBhelper = new DBhelper(context);
    }

    public ArrayList<ThuThu> read(){
        ArrayList<ThuThu> list = new ArrayList<>();
        SQLiteDatabase database = dBhelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM ThuThu", null);
            if(cursor.getColumnCount() > 0){
                //Nếu cursor lớn hơn 0 di chuyển con trỏ lên đầu
                cursor.moveToFirst();
                //Khởi tạo vòng lặp để lấy dữ liệu
                do {
                    list.add(new ThuThu(
                            cursor.getString(0),
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

    public boolean create(ThuThu tt){
        SQLiteDatabase database = dBhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("maTT", tt.getTT());
        values.put("hoTen", tt.getHoTen());
        values.put("matKhau", tt.getMatKhau());

        long check = database.insert("ThuThu", null, values);
        return check != -1;
    }

    public boolean update(ThuThu tt){
        SQLiteDatabase database = dBhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("hoTen", tt.getHoTen());
        values.put("matKhau", tt.getMatKhau());

        int rows = database.update("ThuThu", values, "maTT = ?", new String[]{tt.getTT()});
        return rows > 0;
    }

    public ThuThu getByName(String hoTen){
        SQLiteDatabase database = dBhelper.getReadableDatabase();
        ThuThu thuThu = null;

        Cursor cursor = database.rawQuery("SELECT * FROM ThuThu WHERE hoTen = ?", new String[]{hoTen});
        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount() > 0){
                thuThu = new ThuThu(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
            }
            cursor.close();
        }
        return thuThu;
    }



}
