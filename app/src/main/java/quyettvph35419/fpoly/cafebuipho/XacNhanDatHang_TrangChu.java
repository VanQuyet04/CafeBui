package quyettvph35419.fpoly.cafebuipho;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import quyettvph35419.fpoly.cafebuipho.Account.Login;
import quyettvph35419.fpoly.cafebuipho.Dao.DonHangChiTietDao;
import quyettvph35419.fpoly.cafebuipho.Dao.DonHangDao;
import quyettvph35419.fpoly.cafebuipho.Dao.KhachHangDao;
import quyettvph35419.fpoly.cafebuipho.Model.DonHang;
import quyettvph35419.fpoly.cafebuipho.Model.DonHangChiTiet;
import quyettvph35419.fpoly.cafebuipho.Model.KhachHang;

public class XacNhanDatHang_TrangChu extends AppCompatActivity {
    private TextView tvHoten, tvSdt, tvDiaChi, tvTenDoUong, tvSizeDoUong, tvSoLuong, tvGiaDoUong, tvTongTien;
    private RadioGroup radioGrThanhToan;
    private RadioButton rdoBanking, rdoCard;
    private Button btnDatHang;
    private Toolbar tlbarxndathang;
    private KhachHang khachHang;
    private KhachHangDao khachHangDao;
    private DonHang donHang;
    private DonHangDao donHangDao;
    private DonHangChiTiet donHangChiTiet;
    private DonHangChiTietDao donHangChiTietDao;
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_dat_hang_trang_chu);

        anhxa();
        setSupportActionBar(tlbarxndathang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlbarxndathang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();

        String makh = intent.getStringExtra("makh");
        khachHang = new KhachHang();
        khachHangDao = new KhachHangDao(this);
        khachHang = khachHangDao.getID(makh);

        String tendouong = intent.getStringExtra("tendouong");
        int madouong = intent.getIntExtra("madouong", -1);
        String size = intent.getStringExtra("size");
        String giadouong = intent.getStringExtra("giadouong");
        String soluong = intent.getStringExtra("soluong");
        String tongtien = intent.getStringExtra("tongtien");

        tvTenDoUong.setText(tendouong);
        tvSizeDoUong.setText("Size : " + size);
        tvSoLuong.setText("Số lượng : " + soluong);
        tvGiaDoUong.setText("Giá : " + giadouong);

        tvTongTien.setText(tongtien);

        tvHoten.setText("Họ tên : " + khachHang.getHoTen());
        tvSdt.setText("Số điện thoại : " + khachHang.getSdt());
        tvDiaChi.setText("Địa chỉ : " + khachHang.getDiaChi());


        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadioButtonId = radioGrThanhToan.getCheckedRadioButtonId();

                if (selectedRadioButtonId == -1) {
                    // Không có radio button nào được chọn
                    Toast.makeText(XacNhanDatHang_TrangChu.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

                    String date = getCurrentDateTime();
                    donHang = new DonHang();
                    donHangDao = new DonHangDao(getApplicationContext());
                    donHang.setMaKH(makh);
                    donHang.setMaDO(madouong);
                    donHang.setGia(Integer.parseInt(giadouong));
                    donHang.setSoLuong(Integer.parseInt(soluong));
                    donHang.setTrangThai(1);
                    // Thêm đơn hàng vào cơ sở dữ liệu và lấy ID vừa thêm
                    long donHangID = donHangDao.insert(donHang);

                    if (donHangID > 0) {
                        donHangChiTiet = new DonHangChiTiet();
                        donHangChiTietDao = new DonHangChiTietDao(getApplicationContext());

                        int masize;
                        if (size.equals("M")) {
                            masize = 1;
                        } else if (size.equals("L")) {
                            masize = 2;
                        } else {
                            masize = 3;
                        }

                        donHangChiTiet.setMaDH((int) donHangID);
                        donHangChiTiet.setMaDoUong(madouong);
                        donHangChiTiet.setMaSize(masize);
                        donHangChiTiet.setSoLuong(Integer.parseInt(soluong));
                        donHangChiTiet.setNgay(date);
                        donHangChiTiet.setThanhToan(selectedRadioButton.getText().toString());
                        donHangChiTiet.setTongTien(Integer.parseInt(tongtien));
                        donHangChiTiet.setTrangThai(1);

                        // Thêm đơn hàng chi tiết vào cơ sở dữ liệu
                        if (donHangChiTietDao.insert(donHangChiTiet) > 0) {
                            login.thongBao("Đơn hàng đã được đặt !", XacNhanDatHang_TrangChu.this);
                            showAlertDialog("Đặt hàng thành công", "Cảm ơn bạn đã ủng hộ shop chúng tôi !");
                        }
                    }
                }

            }
        });

    }
    private String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(XacNhanDatHang_TrangChu.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onBackPressed();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void anhxa() {
        tlbarxndathang = findViewById(R.id.toolbarxndathang);

        tvHoten = findViewById(R.id.tvhoten_xndathang);
        tvSdt = findViewById(R.id.tvsdt_xndathang);
        tvDiaChi = findViewById(R.id.tvdiachi_xndathang);

        tvTenDoUong = findViewById(R.id.tvtendouong_xndathang);
        tvSizeDoUong = findViewById(R.id.tvsizedouong_xndathang);
        tvSoLuong = findViewById(R.id.tvsoluong_xndathang);
        tvGiaDoUong = findViewById(R.id.tvgiadouong_xndathang);
        tvTongTien = findViewById(R.id.tvTongTien_xndathang);

        radioGrThanhToan = findViewById(R.id.radioGrthanhtoan);
        rdoBanking = findViewById(R.id.rdo_banking);
        rdoCard = findViewById(R.id.rdo_card);

        btnDatHang = findViewById(R.id.btndathang_xndathang);

        login = new Login();
    }
}