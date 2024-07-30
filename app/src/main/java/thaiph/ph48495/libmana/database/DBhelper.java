package thaiph.ph48495.libmana.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    public DBhelper(Context context){
        super(context, "LibMana", null,12);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Tạo bảng Thủ thư
        db.execSQL("CREATE TABLE ThuThu(" +
                "maTT TEXT PRIMARY KEY," +
                "hoTen TEXT NOT NULL," +
                "matKhau TEXT NOT NULL)");
        db.execSQL("INSERT INTO ThuThu(maTT, hoTen, matKhau) VALUES " +
                "('TT01', 'admin', 'admin')," +
                "('TT02', 'eddy', 'eddy')");


        //Tạo bảng Thành viên
        db.execSQL("CREATE TABLE ThanhVien (" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hoTen TEXT NOT NULL," +
                "namSinh TEXT NOT NULL)");
        db.execSQL("INSERT INTO ThanhVien(hoTen, namSinh) VALUES "+
                "('Hoàng Văn Bách', '2004')," +
                "('Đặng Tiến Minh', '2005')," +
                "('Trương Văn Dũng', '2003')," +
                "('Lê Văn An', '2005')," +
                "('Đặng Thị Quỳnh', '2000')," +
                "('Nguyễn Văn Đạt', '2004')");

        //Tạo bảng loại sách
        db.execSQL("CREATE TABLE LoaiSach (" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenLoai TEXT NOT NULL"+
                ")");
        db.execSQL("INSERT INTO LoaiSach (tenLoai) VALUES " +
                "('Tiểu thuyết')," +
                "('Sách khoa học')," +
                "('Sách tự lực')," +
                "('Sách lịch sử')," +
                "('Sách kỹ năng')," +
                "('Sách thiếu nhi')");

        //Tạo bảng Sách
        db.execSQL("CREATE TABLE Sach (" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSach TEXT NOT NULL," +
                "giaThue INTEGER NOT NULL," +
                "maLoai INTEGER REFERENCES LoaiSach(maLoai)" +
                ")");
        db.execSQL("INSERT INTO Sach (tenSach, giaThue, maLoai) VALUES" +
                "('Ông già và biển cả', 2000, 1)," +
                "('Đắc nhân tâm', 1700, 5)," +
                "('Vũ trụ trong vỏ hạt dẻ', 2200, 2)," +
                "('Sử Việt - 12 Khúc Tráng Ca', 2000, 4)," +
                "('7 Thói quen của người thành đạt', 2000, 5)");

        //Tạo bảng PhieuMuon
        db.execSQL("CREATE TABLE PhieuMuon (" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maTT TEXT REFERENCES ThuThu(maTT)," +
                "maTV INTEGER REFERENCES ThanhVien(maTV)," +
                "maSach INTEGER REFERENCES Sach(maSach)," +
                "tienThue INTEGER NOT NULL," +
                "ngay DATE NOT NULL," +
                "traSach INTEGER NOT NULL" +
                ")");
        db.execSQL("INSERT INTO PhieuMuon (maTT, maTV, maSach, tienThue, ngay, traSach) VALUES " +
                "('TT01', 1, 1, 2000, '2024-2-23', 0)," +
                "('TT01', 3, 2, 1800, '2023-4-4', 1)," +
                "('TT01', 4, 2, 800, '2024-5-3', 0)," +
                "('TT01', 1, 2, 1700, '2022-1-1', 1)," +
                "('TT02', 2, 4, 500, '2024-6-7', 0)," +
                "('TT02', 1, 5, 1950, '2024-5-6', 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS ThuThu");
            db.execSQL("DROP TABLE IF EXISTS ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS LoaiSach");
            db.execSQL("DROP TABLE IF EXISTS Sach");
            db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
            onCreate(db);
        }
    }
}
