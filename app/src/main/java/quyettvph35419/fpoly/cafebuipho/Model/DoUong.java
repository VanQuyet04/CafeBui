package quyettvph35419.fpoly.cafebuipho.Model;

public class DoUong {
    private int maDoUong;
    private int gia;
    private int maBan;
    private int maLoai;

    public DoUong() {
    }

    public DoUong(int maDoUong, int gia, int maBan, int maLoai) {
        this.maDoUong = maDoUong;
        this.gia = gia;
        this.maBan = maBan;
        this.maLoai = maLoai;
    }

    public int getMaDoUong() {
        return maDoUong;
    }

    public void setMaDoUong(int maDoUong) {
        this.maDoUong = maDoUong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }
}