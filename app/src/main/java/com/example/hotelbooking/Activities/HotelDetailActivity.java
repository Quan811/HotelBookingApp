package com.example.hotelbooking.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hotelbooking.Adapter.HotelClientAdapter;
import com.example.hotelbooking.Fragment.FragmentHome;
import com.example.hotelbooking.Model.Hotel;
import com.example.hotelbooking.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HotelDetailActivity extends AppCompatActivity {
    private ImageButton bt_back;
    private TextView hotel_name;
    private ImageView hotel_img;

    private FirebaseRecyclerOptions<Hotel> options;
    private HotelClientAdapter adapter;
    DatabaseReference data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_detail);
        hotel_name = findViewById(R.id.hotel_name);
        hotel_img = findViewById(R.id.hotel_img);

        String hotelName = getIntent().getStringExtra("hotel_name");
        String hotelImg = getIntent().getStringExtra("hotel_img");
        String hotelLocation = getIntent().getStringExtra("hotel_location");
        String hotelPrice = getIntent().getStringExtra("hotel_price");
        String hotelDiscount = getIntent().getStringExtra("hotel_discount");

        hotel_name.setText(hotelName);
        Glide.with(hotel_img.getContext()).load(hotelImg).placeholder(R.drawable.hera).error(R.drawable.hera).centerCrop().into(hotel_img);
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