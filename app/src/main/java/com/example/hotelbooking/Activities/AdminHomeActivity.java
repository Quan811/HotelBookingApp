package com.example.hotelbooking.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.hotelbooking.Adapter.HotelAdminAdapter;
import com.example.hotelbooking.Model.Hotel;
import com.example.hotelbooking.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class AdminHomeActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private FloatingActionButton fl_button;
    private HotelAdminAdapter hotelAdapter;
    private SearchView sv;
    private TextView tv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
        fl_button = findViewById(R.id.fl_button);
        rcv = findViewById(R.id.rcv);
        sv = findViewById(R.id.sv);
        tv_logout = findViewById(R.id.tv_logout);

        //bat sk cho TV Dang Xuat
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        //bat sk cho floatbutton
        fl_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, AddHotelActivity.class));
            }
        });

        //set adapter cho recycle view
        rcv.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<Hotel> options =
                    new FirebaseRecyclerOptions.Builder<Hotel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Hotel"), Hotel.class)
                            .build();

        hotelAdapter = new HotelAdminAdapter(options);
        rcv.setAdapter(hotelAdapter);

        //bat su kien tim kiem searchview
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchByLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchByLocation(newText);
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        hotelAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hotelAdapter.stopListening();
    }

//    private void searchByName(String s){
//        FirebaseRecyclerOptions<Hotel> options =
//                new FirebaseRecyclerOptions.Builder<Hotel>()
//                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Hotel").orderByChild("name").startAt(s).endAt(s + "\uf8ff"), Hotel.class)
//                        .build();
//
//        hotelAdapter =  new HotelAdapter(options);
//        hotelAdapter.startListening();
//        rcv.setAdapter(hotelAdapter);
//        hotelAdapter.notifyDataSetChanged();
//
//    }

    //tim kiem theo dia chi
    private void searchByLocation(String s){
        FirebaseRecyclerOptions<Hotel> options =
                new FirebaseRecyclerOptions.Builder<Hotel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Hotel").orderByChild("location").startAt(s).endAt(s + "\uf8ff"), Hotel.class)
                        .build();

        hotelAdapter =  new HotelAdminAdapter(options);
        hotelAdapter.startListening();
        rcv.setAdapter(hotelAdapter);

    }

}