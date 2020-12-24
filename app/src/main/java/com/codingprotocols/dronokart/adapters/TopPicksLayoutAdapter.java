package com.codingprotocols.dronokart.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingprotocols.dronokart.R;
import com.codingprotocols.dronokart.activities.ProductDetailsActivity;
import com.codingprotocols.dronokart.models.BasicProductModel;

import java.util.List;

public class TopPicksLayoutAdapter extends BaseAdapter {

    List<BasicProductModel> basicProductModelList;

    public TopPicksLayoutAdapter(List<BasicProductModel> basicProductModelList) {
        this.basicProductModelList = basicProductModelList;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.basic_product_layout, null);
            ImageView productImage = view.findViewById(R.id.product_image);
            TextView productName = view.findViewById(R.id.product_title);
            TextView productPrice = view.findViewById(R.id.product_price);

//            Glide.with(view.getContext())
//                    .load(basicProductModelList.get(position).getProductImage())
//                    .apply(new RequestOptions().placeholder(R.drawable.banner_placeholder))
//                    .transform(new RoundedCorners(15))
//                    .into(productImage);

            productImage.setImageResource(basicProductModelList.get(position).getProductImage());
            productName.setText(basicProductModelList.get(position).getProductName());
            productPrice.setText(basicProductModelList.get(position).getProductPrice());

            view.setOnClickListener(v -> {
                Intent productDetailsIntent=new Intent(v.getContext(), ProductDetailsActivity.class);
                productDetailsIntent.putExtra("name",productName.getText());
                productDetailsIntent.putExtra("price",productPrice.getText());
                v.getContext().startActivity(productDetailsIntent);
            });
        } else {
            view = convertView;
        }
        return view;
    }
}
