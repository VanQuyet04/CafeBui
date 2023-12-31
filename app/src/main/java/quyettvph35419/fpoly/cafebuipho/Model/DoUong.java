package quyettvph35419.fpoly.cafebuipho.Model;

public class DoUong {
    private int maDoUong;
    private String tenDoUong;
    private int gia;
    private int imageId;
    private int maLoai;
    private int tonKho;


    public DoUong() {
    }

    public DoUong(int maDoUong, String tenDoUong, int gia, int imageId, int maLoai, int tonKho) {
        this.maDoUong = maDoUong;
        this.tenDoUong = tenDoUong;
        this.gia = gia;
        this.imageId = imageId;
        this.tonKho = tonKho;
    }


    public int getMaDoUong() {
        return maDoUong;
    }

    public void setMaDoUong(int maDoUong) {
        this.maDoUong = maDoUong;
    }

    public String getTenDoUong() {
        return tenDoUong;
    }

    public void setTenDoUong(String tenDoUong) {
        this.tenDoUong = tenDoUong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getTonKho() {
        return tonKho;
    }

    public void setTonKho(int tonKho) {
        this.tonKho = tonKho;
    }
}
