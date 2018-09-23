package com.martel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public class SplashScreen extends BaseActivity {

    private String TAG = "SplashScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });

        SharedPreferences preferences =
                getSharedPreferences("user_preferences", MODE_PRIVATE);
        if (preferences.contains("ja_abriu_app")) {
            //TODO verificaEmail();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user!=null){
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        } else {
            mostrarSplash();
        }

    }

    private void mostrarSplash() {
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                verificaEmail();

            }
        }, 2000);
    }
    private void adicionarPreferenceJaAbriu(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("ja_abriu_app", true);
        editor.commit();
    }

    private void verificaEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            boolean isEmailVerified = user.isEmailVerified();
            if (isEmailVerified){
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                myRef.child(user.getUid()).child("verified").setValue(user.isEmailVerified());
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashScreen.this, SignInActivity.class));
                finish();
            }
        }else {
            startActivity(new Intent(SplashScreen.this, SignInActivity.class));
            finish();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        verificaEmail();
    }
}
