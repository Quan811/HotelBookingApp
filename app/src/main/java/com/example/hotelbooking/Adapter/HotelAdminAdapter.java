package com.example.hotelbooking.Adapter;


import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.hotelbooking.Model.Hotel;
import com.example.hotelbooking.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class HotelAdminAdapter extends FirebaseRecyclerAdapter<Hotel, HotelAdminAdapter.viewHolder> {

    public HotelAdminAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, final int position, @NonNull Hotel model) {
        holder.hotel_name.setText(model.getName());

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price_formatted = formatter.format(Integer.parseInt(model.getPrice()));
        holder.hotel_price.setText(price_formatted);

        holder.hotel_discount.setText("-" + model.getDiscount() + "%");
        holder.hotel_location.setText(model.getLocation());

        Glide.with(holder.hotel_img.getContext())
                .load(model.getImg_url())
                .placeholder(R.drawable.hera)
                .error(R.drawable.hera)
                .centerCrop()
                .into(holder.hotel_img);

        //bat sk nut edit
        holder.bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.hotel_img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_hotel))
                        .setExpanded(true, 1200)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.edtName);
                EditText url = view.findViewById(R.id.edtURL);
                EditText location = view.findViewById(R.id.edtLocation);
                EditText price = view.findViewById(R.id.edtPrice);
                EditText discount = view.findViewById(R.id.edtDiscount);
                Button bt_update = view.findViewById(R.id.bt_update);
                Button bt_back = view.findViewById(R.id.bt_back);

                name.setText(model.getName());
                url.setText(model.getImg_url());
                location.setText(model.getLocation());
                price.setText(model.getPrice());
                discount.setText(model.getDiscount());

                dialogPlus.show();

                bt_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("img_url", url.getText().toString());
                        map.put("location", location.getText().toString());
                        map.put("price", price.getText().toString());
                        map.put("discount", discount.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Hotel")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.hotel_name.getContext(), "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.hotel_name.getContext(), "Đã xảy ra lỗi.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
                bt_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogPlus.dismiss();
                    }
                });

            }
        });
        holder.bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.hotel_name.getContext());
                builder.setTitle("CẢNH BÁO!");
                builder.setMessage("Sau khi xóa sẽ không thể khôi phục, vẫn muốn xóa?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Hotel")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.hotel_name.getContext(), "Đã hủy yêu cầu", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false);
        return new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView hotel_img;
        TextView hotel_name, hotel_location, hotel_price, hotel_discount;
        Button bt_edit, bt_delete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            hotel_img = itemView.findViewById(R.id.hotel_img);
            hotel_name = itemView.findViewById(R.id.hotel_name);
            hotel_location = itemView.findViewById(R.id.hotel_location);
            hotel_price = itemView.findViewById(R.id.hotel_price);
            hotel_discount = itemView.findViewById(R.id.hotel_discount);
            bt_edit = itemView.findViewById(R.id.bt_edit);
            bt_delete = itemView.findViewById(R.id.bt_delete);
        }

    }

}
