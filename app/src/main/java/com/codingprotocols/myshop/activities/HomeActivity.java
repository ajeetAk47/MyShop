package com.codingprotocols.myshop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.codingprotocols.myshop.R;
import com.codingprotocols.myshop.fragments.HomeFragment;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;

import pl.droidsonroids.gif.GifImageView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private GifImageView loadingGif;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private SpaceNavigationView spaceNavigationView;
    private FloatingSearchView mSearchView;
    private FrameLayout frameLayoutHome;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageView imageView = toolbar.findViewById(R.id.profile_pic);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        frameLayoutHome = findViewById(R.id.main_frame_layout);
        setFragment(new HomeFragment());
        loadingGif = findViewById(R.id.loading_gif);
        loadingGif.setVisibility(View.VISIBLE);
        mSearchView = findViewById(R.id.floating_search_view);
        spaceNavigationView = findViewById(R.id.navigationView);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("HOME", R.drawable.ic_baseline_home_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("CATEGORY", R.drawable.ic_baseline_category_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("CART", R.drawable.ic_baseline_shopping_cart_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("ACCOUNT", R.drawable.ic_baseline_account_circle_24));
        spaceNavigationView.showIconOnly();

        DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                try {
                    String userProfile = String.valueOf(document.get("profile"));
                    Picasso.get().load(userProfile).placeholder(R.drawable.default_photo).into(imageView);
                    title.setText(MessageFormat.format("Hi, {0}", document.get("name").toString().split(" ")[0]));

                } catch (Exception e) {
                    Log.e(TAG, "Profile fetch error");
                    Picasso.get().load(R.drawable.default_photo).into(imageView);
                    title.setText("Hi, User");
                }finally {
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(title.getId()));
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, title.getText().toString());
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "user");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    loadingGif.setVisibility(View.INVISIBLE);
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                if (mSearchView.getVisibility() == View.VISIBLE) {
                    mSearchView.setVisibility(View.GONE);
                    frameLayoutHome.setVisibility(View.VISIBLE);
                } else if (mSearchView.getVisibility() == View.GONE) {
                    mSearchView.setVisibility(View.VISIBLE);
                    frameLayoutHome.setVisibility(View.GONE);
                }
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 1:
                        startActivity(new Intent(HomeActivity.this,CategoryActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(HomeActivity.this, CartActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(HomeActivity.this,AccountActivity.class));
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });


        // Search
        mSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> {

            //get suggestions based on newQuery

            //pass them on to the search view
//                mSearchView.swapSuggestions(newSuggestions);
        });


    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        fragmentTransaction.commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        //if the user is not logged in
        //opening the login activity
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        spaceNavigationView.changeCurrentItem(0);
    }
}