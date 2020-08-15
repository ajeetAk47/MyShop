package com.codingprotocols.myshop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.codingprotocols.myshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;

import pl.droidsonroids.gif.GifImageView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private GifImageView loadingGif;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private SpaceNavigationView spaceNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageView imageView=toolbar.findViewById(R.id.profile_pic);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        loadingGif = findViewById(R.id.loading_gif);
        loadingGif.setVisibility(View.VISIBLE);

        spaceNavigationView=findViewById(R.id.navigationView);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("HOME", R.drawable.ic_baseline_home_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("CATEGORY", R.drawable.ic_baseline_category_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("SEARCH", R.drawable.ic_baseline_search_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("ACCOUNT", R.drawable.ic_baseline_account_circle_24));
        spaceNavigationView.showIconOnly();

        DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                try {
                    String userProfile = String.valueOf(document.get("profile"));
                    Picasso.get().load(userProfile).placeholder(R.drawable.default_photo).into(imageView);
                    title.setText(MessageFormat.format("Hi, {0}", document.get("name")));

                } catch (Exception e) {
                    Log.e(TAG, "Profile fetch error");
                    Picasso.get().load(R.drawable.default_photo).into(imageView);
                    title.setText("Name");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Toast.makeText(HomeActivity.this,"onCentreButtonClick", Toast.LENGTH_SHORT).show();
                spaceNavigationView.setCentreButtonSelectable(true);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Toast.makeText(HomeActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(HomeActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });
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
}