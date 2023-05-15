package com.example.hotelbooking.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.hotelbooking.Adapter.HotelAdminAdapter;
import com.example.hotelbooking.Fragment.FragmentAccount;
import com.example.hotelbooking.Fragment.FragmentHome;
import com.example.hotelbooking.Fragment.FragmentMybooking;
import com.example.hotelbooking.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientHomeActivity extends AppCompatActivity {
    private HotelAdminAdapter hotelAdapter;
    private SearchView sv;
    private RecyclerView rcv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.action_home:
                    selectedFragment = new FragmentHome();
                    break;
                case R.id.action_mybooking:
                    selectedFragment = new FragmentMybooking();
                    break;
                case R.id.action_account:
                    selectedFragment = new FragmentAccount();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };

}