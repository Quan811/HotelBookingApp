package com.example.hotelbooking.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.hotelbooking.Fragment.FragmentHome;
import com.example.hotelbooking.R;

public class HotelDetailActivity extends AppCompatActivity {
    private ImageButton bt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_detail);
        initView();
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FragmentHome.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        bt_back = findViewById(R.id.bt_back);
    }

}