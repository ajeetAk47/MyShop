package com.codingprotocols.myshop.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingprotocols.myshop.R;
import com.codingprotocols.myshop.activities.ProductDetailsActivity;
import com.codingprotocols.myshop.models.BasicProductModel;

import java.util.List;

public class DealsOfTheDayLayoutAdapter extends RecyclerView.Adapter<DealsOfTheDayLayoutAdapter.ViewHolder> {

    List<BasicProductModel> basicProductModels;

    public DealsOfTheDayLayoutAdapter(List<BasicProductModel> basicProductModels) {
        this.basicProductModels = basicProductModels;
    }

    @NonNull
    @Override
    public DealsOfTheDayLayoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.basic_product_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealsOfTheDayLayoutAdapter.ViewHolder viewHolder, int position) {
//        Glide.with(viewHolder.itemView.getContext())
//                .load(basicProductModels.get(position).getProductImage())
//                .apply(new RequestOptions().placeholder(R.drawable.banner_placeholder))
//                .transform(new RoundedCorners(15))
//                .into(viewHolder.productImage);
        viewHolder.productImage.setImageResource(basicProductModels.get(position).getProductImage());
        viewHolder.productName.setText(basicProductModels.get(position).getProductName());
        viewHolder.productPrice.setText(basicProductModels.get(position).getProductPrice());

    }

    @Override
    public int getItemCount() {
        return basicProductModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            productName=itemView.findViewById(R.id.product_title);
            productPrice=itemView.findViewById(R.id.product_price);

            itemView.setOnClickListener(v -> {
                Intent productDetailsIntent=new Intent(itemView.getContext(), ProductDetailsActivity.class);
                productDetailsIntent.putExtra("name",productName.getText());
                productDetailsIntent.putExtra("price",productPrice.getText());
                itemView.getContext().startActivity(productDetailsIntent);
            });
        }
    }
}
