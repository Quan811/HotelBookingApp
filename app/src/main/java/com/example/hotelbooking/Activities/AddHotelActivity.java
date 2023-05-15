package com.example.hotelbooking.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotelbooking.Model.Hotel;
import com.example.hotelbooking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddHotelActivity extends AppCompatActivity {
    private EditText edtName, edtURL, edtLocation, edtPrice, edtDiscount;
    private Button bt_add, bt_back;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_hotel);
        edtName = findViewById(R.id.edtName);
        edtURL = findViewById(R.id.edtURL);
        edtLocation = findViewById(R.id.edtLocation);
        edtPrice = findViewById(R.id.edtPrice);
        edtDiscount = findViewById(R.id.edtDiscount);
        bt_add = findViewById(R.id.bt_add);
        bt_back = findViewById(R.id.bt_back);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Hotel");

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String url = edtURL.getText().toString();
                String location = edtLocation.getText().toString();
                String price = edtPrice.getText().toString();
                String discount = edtDiscount.getText().toString();
                Hotel hotel = new Hotel(name, url, location, price, discount);
                id = name;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(id).setValue(hotel);
                        Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddHotelActivity.this, AdminHomeActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
            }
        });
    }
}