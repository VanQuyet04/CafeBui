package quyettvph35419.fpoly.cafebuipho.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import quyettvph35419.fpoly.cafebuipho.Database.DbHelper;
import quyettvph35419.fpoly.cafebuipho.Model.DoUong;
import quyettvph35419.fpoly.cafebuipho.Model.GioHang;

public class GioHangDao {

    DbHelper dbHelper;
    private SQLiteDatabase db;

    public GioHangDao(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int getCount() {
        String sql = "SELECT COUNT(*) FROM GIOHANG";
        Cursor cursor = db.rawQuery(sql, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }

    public long insert(GioHang obj) {
        ContentValues values = new ContentValues();

        values.put("MaDO", obj.getMaDoUong());
        values.put("SoLuong", obj.getSoLuong());
        values.put("MaSize", obj.getMaSize());
        values.put("TongTien", obj.getTongTien());

        return db.insert("GIOHANG", null, values);
    }

    public long updateGH(GioHang obj) {
        ContentValues values = new ContentValues();
        values.put("SoLuong", obj.getSoLuong());
        values.put("TongTien", obj.getTongTien());
        return db.update("GIOHANG", values, "MaGH = ?", new String[]{String.valueOf(obj.getMaGH())});
    }

    public long delete(String id) {
        return db.delete("GIOHANG", "MaGH = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAll() {
        db.execSQL("DELETE FROM GIOHANG");
    }

    public List<GioHang> getAll() {
        String sql = "SELECT * FROM GIOHANG";
        return getData(sql);
    }

    public GioHang getID(String id) {
        String sql = "SELECT * FROM GIOHANG WHERE MaGH=?";
        List<GioHang> list = getData(sql, id);
        return list.get(0);
    }


    @SuppressLint("Range")
    private List<GioHang> getData(String sql, String... selectionArgs) {
        List<GioHang> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            GioHang obj = new GioHang();
            obj.setMaGH(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MaGH"))));
            obj.setMaDoUong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MaDO"))));
            obj.setMaSize(Integer.parseInt(cursor.getString(cursor.getColumnIndex("MaSize"))));
            obj.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("SoLuong"))));
            obj.setTongTien(Integer.parseInt(cursor.getString(cursor.getColumnIndex("TongTien"))));

            list.add(obj);
        }
        return list;
    }


}
