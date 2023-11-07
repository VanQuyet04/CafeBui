package quyettvph35419.fpoly.cafebuipho.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "CafeBui";
    public static final int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        Bảng tài khoản đăng nhập
        String createTableQuanLy = "create table QUANLY(" +
                "MAQUANLY TEXT PRIMARY KEY, " +
                "HOTEN TEXT NOT NULL, " +
                "MATKHAU TEXT NOT NULL)";
        db.execSQL(createTableQuanLy);
//--------------------------------------------------------------------------

//        bảng đồ uống
        String createTableDoUong = "create table DOUONG(MADOUONG INTEGER PRIMARY KEY AUTOINCREMENT," +
                "GIA INTEGER NOT NULL," +
                "MABAN INTEGER NOT NULL," +
                "MALOAI INTEGER REFERENCES LOAIDOUONG(MALOAI))";
        db.execSQL(createTableDoUong);
//        bảng loại đồ uống
        String createTableLoaiDoUong = "create table LOAIDOUONG(MALOAI INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TENLOAI TEXT NOT NULL)";
        db.execSQL(createTableLoaiDoUong);
//        bảng nhân viên
        String createTableNhanVien = "create table NHANVIEN(MANV INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TENNV TEXT NOT NULL," +
                "SDT TEXT NOT NULL," +
                "DIACHI TEXT NOT NULL," +
                "GIOITINH INTEGER NOT NULL )";
        db.execSQL(createTableNhanVien);
//        bảng bàn
        String createTableBan = "create table BAN(MABAN INTEGER PRIMARY KEY AUTOINCREMENT," +
                "VITRIBAN )";
        db.execSQL(createTableBan);
//        bảng hóa đơn
        String createTableHoaDon = "create table HOADON(MAHOADON INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MAQUANLY TEXT REFERENCES QUANLY(MAQUANLY)," +
                "MANV INTEGER REFERENCES NHANVIEN(MANV)," +
                "MADOUONG INTEGER REFERENCES DOUONG(MADOUONG)," +
                "MABAN INTEGER REFERENCES BAN(MABAN) ," +
                "GIA INTEGER NOT NULL," +
                "SOLUONG INTEGER NOT NULL," +
                "NGAY DATE NOT NULL," +
                "TRANGTHAI INTEGER NOT NULL)";
        db.execSQL(createTableHoaDon);
//        ----------------------------------------------------------
//        INSERT DỮ LIỆU
        db.execSQL("INSERT INTO QUANLY VALUES('admin','Admin','admin')," +
                "('trinhpk','Phạm Trưởng','123')");

        db.execSQL("INSERT INTO DOUONG VALUES(1,2000,1,1)," +
                "(2,3000,2,2)," +
                "(3,4000,3,3)");

        db.execSQL("INSERT INTO LOAIDOUONG VALUES(1,'Sinh tố')," +
                "(2,'Trà sữa')," +
                "(3,'Cafe')," +
                "(4,'Sữa')");

        db.execSQL("INSERT INTO NHANVIEN VALUES(1,'Hoài An','0983917555','Lâm Đồng',1)," +
                "(2,'Mạnh Dũng','0973917587','Lào Cai',0)," +
                "(3,'Hùng Dũng','0882917833','Bắc Ninh',0)");

        db.execSQL("INSERT INTO BAN VALUES(1,1)," +
                "(2,2)," +
                "(3,3)");

        db.execSQL("INSERT INTO HOADON VALUES(1,'admin',1,1,1,2500,1,'2021/07/13',1)," +
                "(2,'chubedan',2,2,2,3500,2,'2022/09/23',2)," +
                "(3,'cobengu',3,3,3,4500,3,'2023/02/06',3)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {

            db.execSQL("drop table if exists DOUONG");
            db.execSQL("drop table if exists LOAIDOUONG");
            db.execSQL("drop table if exists NHANVIEN");
            db.execSQL("drop table if exists HOADON");
            db.execSQL("drop table if exists BAN");
            db.execSQL("drop table if exists QUANLY");

            onCreate(db);
        }
    }
}