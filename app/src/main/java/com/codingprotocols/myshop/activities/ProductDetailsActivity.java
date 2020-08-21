package com.codingprotocols.myshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.codingprotocols.myshop.R;
import com.codingprotocols.myshop.adapters.ProductDetailsAdapter;
import com.codingprotocols.myshop.adapters.ProductImageAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager productImageViewPager;
    private TabLayout productImageTabLayout;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTapLayout;
    private TextView productTitle;
    private TextView productPrice;
    private TextView productOldPrice;
    private TextView productRating;
    private Button addToCartButton;
    private Button buyNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.category);
        setSupportActionBar(toolbar);

        productImageViewPager=findViewById(R.id.product_image_viewpager);
        productImageTabLayout =findViewById(R.id.image_indicator);
        productDetailsViewPager=findViewById(R.id.product_details_view_pager);
        productDetailsTapLayout=findViewById(R.id.product_details_tab_layout);
        productTitle=findViewById(R.id.product_title);
        productPrice=findViewById(R.id.product_price);
        productOldPrice=findViewById(R.id.product_old_price);
        productRating=findViewById(R.id.product_rating_text_view);
        addToCartButton=findViewById(R.id.add_to_cart_button);
        buyNowButton=findViewById(R.id.buy_now_button);

        Intent intent=getIntent();
        if (intent!=null){
            productTitle.setText(intent.getStringExtra("name"));
            productPrice.setText(intent.getStringExtra("price"));
        }else{
            finish();
        }

        List<Integer> productImages=new ArrayList<>();
        productImages.add(R.drawable.simple_logo);
        productImages.add(R.drawable.simple_logo);
        productImages.add(R.drawable.simple_logo);
        productImages.add(R.drawable.simple_logo);
        productImages.add(R.drawable.simple_logo);

        ProductImageAdapter productImageAdapter=new ProductImageAdapter(productImages);
        productImageViewPager.setAdapter(productImageAdapter);
        productImageTabLayout.setupWithViewPager(productImageViewPager,true);

        productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTapLayout.getTabCount()));
        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTapLayout));
        productDetailsTapLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}