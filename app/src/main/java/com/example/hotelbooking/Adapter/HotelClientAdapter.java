package com.example.hotelbooking.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelbooking.Model.Hotel;
import com.example.hotelbooking.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DecimalFormat;

public class HotelClientAdapter extends FirebaseRecyclerAdapter<Hotel, HotelClientAdapter.viewHolder> {
    public IListener listener;

    public HotelClientAdapter(@NonNull FirebaseRecyclerOptions options, IListener listener) {
        super(options);
        this.listener = listener;
    }
    public HotelClientAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
        this.listener = null;
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull Hotel model) {
        holder.hotel_name.setText(model.getName());

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price_formatted = formatter.format(Integer.parseInt(model.getPrice()));
        holder.hotel_price.setText(price_formatted);

        holder.hotel_discount.setText("-" + model.getDiscount() + "%");
        holder.hotel_location.setText(model.getLocation());

        Glide.with(holder.hotel_img.getContext()).load(model.getImg_url()).placeholder(R.drawable.hera).error(R.drawable.hera).centerCrop().into(holder.hotel_img);
        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(model);
        });
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client, parent, false);
        return new viewHolder(view);
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView hotel_img;
        TextView hotel_name, hotel_location, hotel_price, hotel_discount;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            hotel_img = itemView.findViewById(R.id.hotel_img);
            hotel_name = itemView.findViewById(R.id.hotel_name);
            hotel_location = itemView.findViewById(R.id.hotel_location);
            hotel_price = itemView.findViewById(R.id.hotel_price);
            hotel_discount = itemView.findViewById(R.id.hotel_discount);

        }
    }
}