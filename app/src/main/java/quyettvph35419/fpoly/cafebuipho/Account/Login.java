package quyettvph35419.fpoly.cafebuipho.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;


import java.util.Date;

import quyettvph35419.fpoly.cafebuipho.SerVice.ConfigNotification;
import quyettvph35419.fpoly.cafebuipho.Dao.KhachHangDao;
import quyettvph35419.fpoly.cafebuipho.MainActivity;
import quyettvph35419.fpoly.cafebuipho.R;


public class Login extends AppCompatActivity {
    private TextInputEditText edUserName, edPassword;
    private Button btnLogin, btnCancel;
    private CheckBox chkRememberPass;
    private KhachHangDao khachHangDao;
    private String strUser, strPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textView = findViewById(R.id.txtsignu);
        String text = "Don't have an account? Sign up";
        SpannableString spannableString = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Xử lý sự kiện khi chữ "Sign up" được nhấp vào
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // Đặt các thuộc tính cho chữ in đậm
                ds.setFakeBoldText(true);
            }
        };
// Tìm vị trí của chữ "Sign up" trong đoạn text
        int startIndex = text.indexOf("Sign up");
        int endIndex = startIndex + "Sign up".length();
// Đặt ClickableSpan cho đoạn chữ "Sign up"
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);


        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        khachHangDao = new KhachHangDao(this);

        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");

        Boolean rem = pref.getBoolean("REMEMBER", false);
        edUserName.setText(user);
        edPassword.setText(pass);

        chkRememberPass.setChecked(rem);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUserName.setText("");
                edPassword.setText("");
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });


    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            // xoa trang thai luu truoc do
            edit.clear();
        } else {
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
        }
        // luu lai toan bo du lieu
        edit.commit();
    }

    public void checkLogin() {
        strUser = edUserName.getText().toString();
        strPass = edPassword.getText().toString();
        if (strUser.trim().isEmpty() || strPass.trim().isEmpty()) {
            Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        } else {
            if (khachHangDao.checkLogin(strUser, strPass) > 0) {
                thongBao("Đăng nhập thành công",getApplicationContext());
                rememberUser(strUser, strPass, chkRememberPass.isChecked());

                Bundle bundle = new Bundle();
                bundle.putString("user", strUser);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user", strUser);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void thongBao(String title, Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ConfigNotification.CHANNEL_ID)
                .setSmallIcon(R.drawable.tb)
                .setContentTitle("Thông báo")
                .setContentText(title);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            managerCompat.notify((int) new Date().getTime(), builder.build());
        }
    }


}