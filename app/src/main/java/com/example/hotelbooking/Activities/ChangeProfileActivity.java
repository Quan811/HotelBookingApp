package com.example.hotelbooking.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hotelbooking.Model.User;
import com.example.hotelbooking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class ChangeProfileActivity extends AppCompatActivity {
    private Button bt_submit;
    private DatabaseReference databaseReference;
    private EditText edt_ten, edt_sdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        bt_submit = findViewById(R.id.bt_submit);
        edt_ten = findViewById(R.id.edt_ten);
        edt_sdt = findViewById(R.id.edt_sdt);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}