package quyettvph35419.fpoly.cafebuipho.DatHang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import quyettvph35419.fpoly.cafebuipho.Account.Login;
import quyettvph35419.fpoly.cafebuipho.Adapter.DoUongAdapter_Xndathang_GioHang;
import quyettvph35419.fpoly.cafebuipho.Dao.DoUongDao;
import quyettvph35419.fpoly.cafebuipho.Dao.DonHangChiTietDao;
import quyettvph35419.fpoly.cafebuipho.Dao.DonHangDao;
import quyettvph35419.fpoly.cafebuipho.Dao.GioHangDao;
import quyettvph35419.fpoly.cafebuipho.Dao.KhachHangDao;
import quyettvph35419.fpoly.cafebuipho.Model.DoUong;
import quyettvph35419.fpoly.cafebuipho.Model.DonHang;
import quyettvph35419.fpoly.cafebuipho.Model.DonHangChiTiet;
import quyettvph35419.fpoly.cafebuipho.Model.GioHang;
import quyettvph35419.fpoly.cafebuipho.Model.KhachHang;
import quyettvph35419.fpoly.cafebuipho.QLGioHang;
import quyettvph35419.fpoly.cafebuipho.R;

public class XacNhanDatHang_GioHang extends AppCompatActivity {
    private Toolbar tlbarxndathang;
    private TextView tvHoten, tvSdt, tvDiaChi, tvTongTien;
    private Button btndathangGH;

    private RadioGroup radioGrThanhToan;
    private RadioButton rdoBanking, rdoCard;
    private RecyclerView rclDouongGH;
    private GioHangDao gioHangDao;
    private DoUongDao doUongDao;
    private List<DoUong> doUongList;

    private List<GioHang> gioHangList;
    private DoUongAdapter_Xndathang_GioHang gioHangAdapter;
    private KhachHang khachHang;
    private KhachHangDao khachHangDao;
    private DonHang donHang;
    private DonHangDao donHangDao;
    private DonHangChiTiet donHangChiTiet;
    private DonHangChiTietDao donHangChiTietDao;
    private Login login;
    private int tongTien = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_dat_hang_gio_hang);

        anhXa();

        Intent intent = getIntent();
        String makh = intent.getStringExtra("makh");

        setSupportActionBar(tlbarxndathang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tlbarxndathang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rclDouongGH = findViewById(R.id.rclViewDoUong_Xndathang_giohang);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rclDouongGH.setLayoutManager(layoutManager);

        gioHangDao = new GioHangDao(this);
        gioHangList = gioHangDao.getAll();
        gioHangAdapter = new DoUongAdapter_Xndathang_GioHang(gioHangList, this);
        rclDouongGH.setAdapter(gioHangAdapter);

        for (GioHang gioHang : gioHangList) {
            int gia = gioHang.getTongTien(); // Lấy giá từ sản phẩm
            tongTien += gia;// Tính tổng giá tiền
        }

        tvTongTien.setText(String.valueOf(tongTien) + " vnđ");

        khachHang = khachHangDao.getID(makh);
        tvHoten.setText(khachHang.getHoTen());
        tvSdt.setText(khachHang.getSdt());
        tvDiaChi.setText(khachHang.getDiaChi());

        btndathangGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadioButtonId = radioGrThanhToan.getCheckedRadioButtonId();

                if (selectedRadioButtonId == -1) {
                    Toast.makeText(XacNhanDatHang_GioHang.this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    String date = getCurrentDateTime();

                    // Tạo đơn hàng
                    DonHang donHang = new DonHang();
                    DonHangDao donHangDao = new DonHangDao(getApplicationContext());

                    donHang.setMaKH(makh);
                    donHang.setGia(tongTien);
                    donHang.setSoLuong(gioHangDao.getCount());// số lượng sản phẩm có trong đơn
                    donHang.setTrangThai(1);
                    donHang.setNgay(date);

                    // Thêm đơn hàng vào cơ sở dữ liệu và lấy ID
                    long donHangID = donHangDao.insert(donHang);

                    if (donHangID > 0) {
                        // Duyệt qua từng sản phẩm trong giỏ hàng
                        for (GioHang gioHang : gioHangList) {
                            // Tạo đơn hàng chi tiết
                            DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
                            DonHangChiTietDao donHangChiTietDao = new DonHangChiTietDao(getApplicationContext());

                            int size = gioHang.getMaSize();

                            // Thiết lập thông tin cho đơn hàng chi tiết
                            donHangChiTiet.setMaDH((int) donHangID);
                            donHangChiTiet.setMaDoUong(gioHang.getMaDoUong());
                            donHangChiTiet.setMaSize(size);
                            donHangChiTiet.setSoLuong(gioHang.getSoLuong());
                            donHangChiTiet.setNgay(getCurrentDateTime());
                            donHangChiTiet.setThanhToan(selectedRadioButton.getText().toString());
                            donHangChiTiet.setTongTien(gioHang.getTongTien());
                            donHangChiTiet.setTrangthaidanhgia(0);

                            // Thêm đơn hàng chi tiết vào cơ sở dữ liệu
                            if (donHangChiTietDao.insert(donHangChiTiet) > 0) {
                                // Cập nhật số lượng tồn kho của đồ uống
                                DoUong doUong = doUongDao.getID(String.valueOf(gioHang.getMaDoUong()));
                                int sltonkho = doUong.getTonKho();
                                int soluongdat = gioHang.getSoLuong();
                                if (sltonkho >= soluongdat) {
                                    doUong.setTonKho(sltonkho - soluongdat);
                                    doUongDao.updatetonkho(doUong);
                                }
                            } else {
                                showAlertDialog("Oh! Đã xảy ra lỗi", "Rất tiếc vì hình như đã xảy ra điều gì đó, bạn hãy đăng xuất và thử mua lại nhé !");
                            }
                        }
                        // Xóa giỏ hàng sau khi đã đặt hàng
                        gioHangDao.deleteAll();
                        showAlertDialog("Đặt hàng thành công", "Cảm ơn bạn đã ủng hộ shop chúng tôi !");
                    }
                }
            }
        });
        Button btnSuaThongTin = findViewById(R.id.btnSuaThongTin_gh);
        btnSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });
    }

    private String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(XacNhanDatHang_GioHang.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(XacNhanDatHang_GioHang.this, QLGioHang.class);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void anhXa() {
        tlbarxndathang = findViewById(R.id.toolbarxndathang);

        tvTongTien = findViewById(R.id.tvTongTien_xndathang_gh);

        tvHoten = findViewById(R.id.tvhoten_xndathang_gh);
        tvSdt = findViewById(R.id.tvsdt_xndathang_gh);
        tvDiaChi = findViewById(R.id.tvdiachi_xndathang_gh);

        khachHang = new KhachHang();
        khachHangDao = new KhachHangDao(this);

        doUongDao = new DoUongDao(this);
        btndathangGH = findViewById(R.id.btndathang_xndathang_gh);
        radioGrThanhToan = findViewById(R.id.radioGrthanhtoan_gh);
        rdoBanking = findViewById(R.id.rdo_banking_gh);
        rdoCard = findViewById(R.id.rdo_card_gh);

        login = new Login();
    }

    private boolean kiemTraTonKho() {
        for (GioHang gioHang : gioHangList) {
            DoUong doUong = doUongDao.getID(String.valueOf(gioHang.getMaDoUong()));
            int sltonkho = doUong.getTonKho();
            int soluongdat = gioHang.getSoLuong();
            if (sltonkho < soluongdat) {
                // Số lượng đặt hàng lớn hơn số lượng tồn kho
                return false;
            }
        }
        return true;
    }

    private void capNhatTonKho() {
        for (GioHang gioHang : gioHangList) {
            DoUong doUong = doUongDao.getID(String.valueOf(gioHang.getMaDoUong()));
            int sltonkho = doUong.getTonKho();
            int soluongdat = gioHang.getSoLuong();
            if (sltonkho >= soluongdat) {
                doUong.setTonKho(sltonkho - soluongdat);
                doUongDao.updatetonkho(doUong);
            }
        }
    }

    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa thông tin");

        // Inflate layout của dialog
        View view = getLayoutInflater().inflate(R.layout.dialog_thongtin_dathang, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        // Ánh xạ các view trong dialog
        TextInputEditText edtHoTen = view.findViewById(R.id.edtHoTen);
        TextInputEditText edtSoDienThoai = view.findViewById(R.id.edtSoDienThoai);
        TextInputEditText edtDiaChi = view.findViewById(R.id.edtDiaChi);
        Button btnSua = view.findViewById(R.id.btnSua);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        String hoTenHienTai = tvHoten.getText().toString();
        String soDienThoaiHienTai = tvSdt.getText().toString();
        String diaChiHienTai = tvDiaChi.getText().toString();

        edtHoTen.setText(hoTenHienTai);
        edtSoDienThoai.setText(soDienThoaiHienTai);
        edtDiaChi.setText(diaChiHienTai);


        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hoTenMoi = edtHoTen.getText().toString();
                String soDienThoaiMoi = edtSoDienThoai.getText().toString();
                String diaChiMoi = edtDiaChi.getText().toString();

                if (TextUtils.isEmpty(hoTenMoi) || TextUtils.isEmpty(soDienThoaiMoi) || TextUtils.isEmpty(diaChiMoi)) {
                    Toast.makeText(XacNhanDatHang_GioHang.this, "Sửa chưa thành công, hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!soDienThoaiMoi.matches("\\d+")) {
                    Toast.makeText(XacNhanDatHang_GioHang.this, "Số điện thoại chỉ được chứa số", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    tvHoten.setText(hoTenMoi);
                    tvSdt.setText(soDienThoaiMoi);
                    tvDiaChi.setText(diaChiMoi);

                    khachHang.setHoTen(hoTenMoi);
                    khachHang.setDiaChi(diaChiMoi);
                    khachHang.setSdt(soDienThoaiMoi);
                    khachHangDao.updatePass(khachHang);
                    Toast.makeText(XacNhanDatHang_GioHang.this, "Cập nhật xong", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


    }


}