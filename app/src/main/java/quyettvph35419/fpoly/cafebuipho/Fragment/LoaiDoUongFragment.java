package quyettvph35419.fpoly.cafebuipho.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import quyettvph35419.fpoly.cafebuipho.R;


public class LoaiDoUongFragment extends Fragment {

    public LoaiDoUongFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loai_do_uong, container, false);
        return v;
    }
}