package com.codingprotocols.myshop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codingprotocols.myshop.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    //A constant for detecting the login intent result
    private static final int RC_SIGN_IN = 234;

    //Tag for the logs optional
    private static final String TAG = "LoginActivity";

    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore db;
    String newToken;
    GifImageView loadingGif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        loadingGif = findViewById(R.id.loading_gif);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.sign_in_button).setOnClickListener(v -> {
            signIn();
            loadingGif.setVisibility(View.VISIBLE);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                loadingGif.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.e(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        final FirebaseUser user = mAuth.getCurrentUser();
                        // Create a new user with a first and last name
                        Log.d(TAG, "User Signed In");
                        Map<String, String> userMap = new HashMap<>();
                        userMap.put("name", user.getDisplayName());
                        userMap.put("email", user.getEmail());
                        userMap.put("profile", String.valueOf(user.getPhotoUrl()));
                        userMap.put("createdDateTime", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US).format(Calendar.getInstance().getTime()));
                        userMap.put("token", "Added Soon");
                        db.collection("users").document(user.getUid()).get().addOnCompleteListener(task1 -> {
                            DocumentSnapshot document = task1.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "user Already Exist");
                                sendUserToHomeActivity();
                            } else {
                                db.collection("users")
                                        .document(user.getUid()).set(userMap).addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "user Created");
                                    sendUserToHomeActivity();
                                }).addOnFailureListener(e -> {
                                    Log.e(TAG, "Error adding document", e);
                                });
                            }
                        });
                        loadingGif.setVisibility(View.GONE);
                    } else {
                        loadingGif.setVisibility(View.INVISIBLE);
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendUserToHomeActivity() {
        Intent mainActivityIntent = new Intent(LoginActivity.this, HomeActivity.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivityIntent);
        finish();
    }

    //this method is called on click
    private void signIn() {
        //getting the google signIn intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}