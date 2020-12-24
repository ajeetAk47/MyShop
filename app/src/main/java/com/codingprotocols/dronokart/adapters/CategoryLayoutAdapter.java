package com.codingprotocols.dronokart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingprotocols.dronokart.R;
import com.codingprotocols.dronokart.models.CategoryModel;

import java.util.List;

public class CategoryLayoutAdapter extends BaseAdapter {

    List<CategoryModel> categoryModelList;

    public CategoryLayoutAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @Override
    public int getCount() {
        return categoryModelList.size();
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

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item_layout,null);
            ImageView productImage = view.findViewById(R.id.category_image);
            TextView productName = view.findViewById(R.id.category_name);

//            Glide.with(view.getContext())
//                    .load(categoryModelList.get(position).getCategoryIcon())
//                    .apply(new RequestOptions().placeholder(R.drawable.banner_placeholder))
//                    .transform(new RoundedCorners(15))
//                    .into(productImage);

            productImage.setImageResource(categoryModelList.get(position).getCategoryIcon());
            productName.setText(categoryModelList.get(position).getCategoryName());
        return view;
    }
}
