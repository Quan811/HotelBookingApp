package com.example.hotelbooking.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hotelbooking.Adapter.HotelClientAdapter;
import com.example.hotelbooking.Adapter.SlideImageAdapter;
import com.example.hotelbooking.Model.Hotel;
import com.example.hotelbooking.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentHome extends Fragment {
    private ViewPager2 viewPager2;
    private RecyclerView rcv;
    private HotelClientAdapter hotelAdapter;
    private Handler slidehandler = new Handler();
    private SearchView sv;
    private HotelClientAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //set adapter cho recycleview
        rcv = view.findViewById(R.id.rcv_client);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Hotel> options =
                new FirebaseRecyclerOptions.Builder<Hotel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Hotel"), Hotel.class)
                        .build();

        hotelAdapter = new HotelClientAdapter(options);
        rcv.setAdapter(hotelAdapter);

        //set anh cho viewpager
        viewPager2 = view.findViewById(R.id.viewpager);
        List<String> imgUrlList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Hotel");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imgUrlList.clear();
                for(DataSnapshot hotelSnapShot : snapshot.getChildren()){
                    String imgURl = hotelSnapShot.child("img_url").getValue(String.class);
                    imgUrlList.add(imgURl);
                    SlideImageAdapter adapter = new SlideImageAdapter(imgUrlList, viewPager2);
                    viewPager2.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(30));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1-Math.abs(position);
                page.setScaleY(0.75f + r*0.25f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

            }
        });
        // Tạo handler và runnable để tự động chuyển ảnh
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                int currentPosition = viewPager2.getCurrentItem();
                int newPosition = currentPosition + 1;
                if (newPosition >= imgUrlList.size()) {
                    newPosition = 0;
                }
                viewPager2.setCurrentItem(newPosition);
                handler.postDelayed(this, 3000);
            }
        };
        // Bắt đầu tự động chuyển ảnh
        handler.postDelayed(runnable, 3000);

        //bat su kien tim kiem cho SearchView
        sv = view.findViewById(R.id.sv);
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
        return view;
    }
    private void searchByLocation(String s){
        FirebaseRecyclerOptions<Hotel> options =
                new FirebaseRecyclerOptions.Builder<Hotel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Hotel").orderByChild("location").startAt(s).endAt(s + "\uf8ff"), Hotel.class)
                        .build();

        adapter =  new HotelClientAdapter(options);
        adapter.startListening();
        rcv.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        hotelAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        hotelAdapter.stopListening();
    }


}
