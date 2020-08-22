package com.codingprotocols.myshop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.codingprotocols.myshop.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private GifImageView loadingGif;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseAnalytics mFirebaseAnalytics;
    private android.app.AlertDialog signOutDialog;
    private Button loginOutButton;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private TextView myOrdersViewTextView, myAddressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageView imageView = toolbar.findViewById(R.id.profile_pic);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadingGif = findViewById(R.id.loading_gif);
        loginOutButton = findViewById(R.id.logout_button);

        loadingGif.setVisibility(View.VISIBLE);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        loginOutButton.setOnClickListener(v -> {
            signOutDialog = signOutAndCacheCleanDialog();
            signOutDialog.show();
        });

        DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                try {
                    String userProfile = String.valueOf(document.get("profile"));
                    Picasso.get().load(userProfile).placeholder(R.drawable.default_photo).into(imageView);
                    title.setText(MessageFormat.format("Hi, {0}", Objects.requireNonNull(document.get("name")).toString().split(" ")[0]));

                } catch (Exception e) {
                    Log.e(TAG, "Profile fetch error");
                    Picasso.get().load(R.drawable.default_photo).into(imageView);
                    title.setText("Hi, User");
                } finally {
                    loadingGif.setVisibility(View.INVISIBLE);
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

    }

    private android.app.AlertDialog signOutAndCacheCleanDialog() {
        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this, R.style.AlertDialogStyle)
                .setTitle("Alert")
                .setMessage("Are Really want to Sign Out")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        (dialog, id) -> mGoogleSignInClient.signOut().addOnCompleteListener(AccountActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                mAuth.signOut();
                                sendToLogInActivity();
                            }
                        }))
                .setNeutralButton("No", (dialog, which) -> {
                    //dismiss
                });
        return (alertDialogBuilder.create());
    }

    private void sendToLogInActivity() {
        Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
}