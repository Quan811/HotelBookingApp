package com.example.hotelbooking.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelbooking.Adapter.HotelClientAdapter;
import com.example.hotelbooking.Fragment.FragmentHome;
import com.example.hotelbooking.Model.Hotel;
import com.example.hotelbooking.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class HotelDetailActivity extends AppCompatActivity {
    private ImageButton bt_back;
    private TextView hotelName;
    private ImageView hotel_img;

    private FirebaseRecyclerOptions<Hotel> options;
    private HotelClientAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_detail);
        hotelName = findViewById(R.id.hotel_name);
        hotel_img = findViewById(R.id.hotel_img);

        String hotelName = getIntent().getStringExtra("hotel_name");
        options = new FirebaseRecyclerOptions.Builder<Hotel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Hotel").orderByChild("name").startAt(hotelName), Hotel.class)
                .build();
        adapter = new HotelClientAdapter(options);
        adapter.startListening();
        //chưa biết lấy dữ liệu đưa lên detail kiểu gì

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