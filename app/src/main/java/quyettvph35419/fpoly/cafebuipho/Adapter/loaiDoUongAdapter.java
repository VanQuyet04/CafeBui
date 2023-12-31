package quyettvph35419.fpoly.cafebuipho.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import quyettvph35419.fpoly.cafebuipho.Fragment.QLLoaiDoUongFragment;
import quyettvph35419.fpoly.cafebuipho.Model.LoaiDoUong;
import quyettvph35419.fpoly.cafebuipho.R;

public class loaiDoUongAdapter extends ArrayAdapter<LoaiDoUong> {
    private Context context;
    private ArrayList<LoaiDoUong> list;
    private QLLoaiDoUongFragment fragment;
    TextView tvmaLoai, tvtenLoai;
    ImageView imgxoa;


    public loaiDoUongAdapter(@NonNull Context context, QLLoaiDoUongFragment fragment, ArrayList<LoaiDoUong> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_loaidouong_admin, null);
        }
        final LoaiDoUong item = list.get(position);
        if (item != null) {
            tvmaLoai = v.findViewById(R.id.tvmaloai_loaiDoUong);
            tvmaLoai.setText("Mã Loại: " + item.getMaLoai());
            tvtenLoai = v.findViewById(R.id.tvtenloai_loaiDoUong);
            tvtenLoai.setText("Tên Loại: " + item.getTenLoai());

            imgxoa = v.findViewById(R.id.btnxoa_loaiDoUong);
        }
        imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//           fragment.xoa(String.valueOf(item.getMaLoai()));
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("Xóa loại đồ uống");
                builder.setMessage("Bạn chắc muốn xóa chứ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list.remove(position);
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });


        return v;
    }
}
