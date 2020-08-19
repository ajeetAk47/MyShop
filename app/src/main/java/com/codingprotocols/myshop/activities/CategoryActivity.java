package com.codingprotocols.myshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.codingprotocols.myshop.R;
import com.codingprotocols.myshop.adapters.CategoryLayoutAdapter;
import com.codingprotocols.myshop.adapters.TopPicksLayoutAdapter;
import com.codingprotocols.myshop.models.BasicProductModel;
import com.codingprotocols.myshop.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.category);
        setSupportActionBar(toolbar);
        GridView categoryGridView=findViewById(R.id.category_grid_view);

        List<CategoryModel> categoryModelArrayList=new ArrayList<>();
        categoryModelArrayList.add(new CategoryModel("Category 1",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 2",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 3",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 4",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 5",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 6",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 7",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 8",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 9",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 10",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 11",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 12",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 13",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 14",R.drawable.simple_logo));
        categoryModelArrayList.add(new CategoryModel("Category 15",R.drawable.simple_logo));

        categoryGridView.setAdapter(new CategoryLayoutAdapter(categoryModelArrayList));
    }
}