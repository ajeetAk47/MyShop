package com.codingprotocols.dronokart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codingprotocols.dronokart.R;
import com.codingprotocols.dronokart.models.BannerSliderModel;

import java.util.List;

public class BannerSliderLayoutAdapter extends PagerAdapter {
    private List<BannerSliderModel> bannerSliderModelList;

    public BannerSliderLayoutAdapter(List<BannerSliderModel> bannerSliderModelList) {
        this.bannerSliderModelList = bannerSliderModelList;
    }

    @Override
    public int getCount() {
        return bannerSliderModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.banner_slider_layout,container,false);
        ImageView banner=view.findViewById(R.id.bannerImage);
        Glide.with(container.getContext())
                .load(bannerSliderModelList.get(position).getBanner())
                .apply(new RequestOptions().placeholder(R.drawable.banner_placeholder))
                .transform(new RoundedCorners(15))
                .into(banner);
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
