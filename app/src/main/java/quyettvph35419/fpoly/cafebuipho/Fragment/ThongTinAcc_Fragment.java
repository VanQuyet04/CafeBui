package quyettvph35419.fpoly.cafebuipho.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import quyettvph35419.fpoly.cafebuipho.Dao.KhachHangDao;
import quyettvph35419.fpoly.cafebuipho.Model.KhachHang;
import quyettvph35419.fpoly.cafebuipho.R;


public class ThongTinAcc_Fragment extends Fragment {
    TextView tvtendaydu, tvtentk, tvsdt, tvdiachi, tvemail;
    KhachHangDao khachHangDao;
    KhachHang khachHang;

    public ThongTinAcc_Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thong_tin_acc_, container, false);

        tvtendaydu = v.findViewById(R.id.tvtendaydu);
        tvemail = v.findViewById(R.id.tvemali);
        tvdiachi = v.findViewById(R.id.tvdiachi);
        tvsdt = v.findViewById(R.id.tvsdt);
        tvtentk = v.findViewById(R.id.tvTentk);

        khachHangDao = new KhachHangDao(getContext());
        khachHang = new KhachHang();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String user = bundle.getString("user");

            // Lấy thông tin từ CSDL và gán vào quanLy
            khachHang = khachHangDao.getID(user);

            // Gán giá trị từ quanLy vào TextView
            tvtentk.setText("Tên đăng nhập: " + khachHang.getmaKH());
            tvtendaydu.setText("Họ tên: " + khachHang.getHoTen());
            tvsdt.setText("Số điện thoại: " + khachHang.getSdt());
            tvdiachi.setText("Địa chỉ: " + khachHang.getDiaChi());
            tvemail.setText("Email: " + khachHang.getEmail());
        }



        return v;
    }
}