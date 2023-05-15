package com.example.hotelbooking.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.hotelbooking.R;
import java.util.List;

public class SlideImageAdapter extends RecyclerView.Adapter<SlideImageAdapter.SlideViewHolder> {
    private List<String> imgUrlList;
    private ViewPager2 viewPager2;

    public SlideImageAdapter(List<String> imgUrlList, ViewPager2 viewPager2) {
        this.imgUrlList = imgUrlList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_image, parent,false);
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        String imageUrl = imgUrlList.get(position);
        Glide.with(holder.img.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.hera)
                .error(R.drawable.hera)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return imgUrlList.size();
    }


    class SlideViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_slide);
        }
    }

}
