package com.example.hotelbooking.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbooking.Model.User;
import com.example.hotelbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private TextView tvdangnhap;
    private EditText email, password, user_name, phone_number;
    private Button btdangky;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initView();

        //chuyển giao diện đăng ký sang đăng nhập khi ấn vào nút đăng nhập
        tvdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //bắt sự kiện cho nút đăng ký
        btdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail, mPassword;
                mEmail = email.getText().toString();
                mPassword = password.getText().toString();
                if(TextUtils.isEmpty(mEmail)){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mPassword)){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                //tạo mới 1 đối tượng User sau khi đăng ký thành công
                mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Dang ky thanh cong thi them moi 1 doi tuong User len firebase
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    String userId = firebaseUser.getUid();

                                    // Tạo tham chiếu đến nút "User" trong cơ sở dữ liệu Firebase
                                    databaseReference = FirebaseDatabase.getInstance().getReference("User");

                                    // Tạo một đối tượng User mới
                                    String ten = user_name.getText().toString();
                                    String sdt = phone_number.getText().toString();
                                    User user = new User(ten, sdt);

                                    // Thêm User mới vào Firebase
                                    databaseReference.child(userId).setValue(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Thêm User thành công
                                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {

                                                    }
                                                }
                                            });
                                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(RegisterActivity.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }

        });
    }

    private void initView() {
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        tvdangnhap = findViewById(R.id.tvdangnhap);
        btdangky = findViewById(R.id.btdangky);
        user_name = findViewById(R.id.user_name);
        phone_number = findViewById(R.id.phone_number);
    }

}