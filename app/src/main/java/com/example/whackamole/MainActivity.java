package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    static String sTag = "MainActivity";
    private Button btChangepage;

    private GoogleSignInClient mGoogleSignInClient;
    SignInButton btSighIn;
    Button btSighOut;

    String sLogin = "Login";
    String sLogout = "Logout";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----- Chage page : game page
        btChangepage = (Button) findViewById(R.id.startId);
        btChangepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,GamePage.class);
                startActivity(intent);
            }
        });
        //----- End Chage page : game page

        //----- Google log in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("254871341874-385dsgptk1etsf7h6t4miuaht5cql557.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btSighIn = findViewById(R.id.button_SignIn);
        btSighIn.setOnClickListener(v->{
            startActivityForResult(mGoogleSignInClient.getSignInIntent(),200);
        });
        btSighOut = (Button)findViewById(R.id.signoutId);

        Task<GoogleSignInAccount> task = mGoogleSignInClient.silentSignIn();
        if (task.isSuccessful()) {
            Log.d(sTag, "Got cached sign-in");
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(sTag, "Token: "+account.getIdToken());
                btChangepage.setText(account.getDisplayName() + " start");
                googleLoninState(sLogin);

            } catch (ApiException e) {
                Log.w(sTag, "Google sign in failed", e);
                mGoogleSignInClient.signOut();
            }
        }
        //----- End Google log in
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // Google log in
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
//                String str = "Token: " + account.getEmail();
//                btChangepage.setText(str);
                googleLoninState(sLogin);
                btChangepage.setText(account.getDisplayName() + " start");

            } catch (ApiException e) {
                Log.w(sTag, "Google sign in failed", e);
                mGoogleSignInClient.signOut();
            }
        }
    }

    public void SignOut_OnClick(View view){
        mGoogleSignInClient.signOut();
        googleLoninState(sLogout);
        btChangepage.setText("start");
    }

    private void googleLoninState(String state){
        if(state.equals(sLogin)){
            btSighIn.setVisibility(View.INVISIBLE);
            btSighOut.setVisibility(View.VISIBLE);
        } else if(state.equals(sLogout)){
            btSighIn.setVisibility(View.VISIBLE);
            btSighOut.setVisibility(View.INVISIBLE);
        }
    }
}