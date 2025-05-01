package com.example.ecozym;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OpeningScreenActivity extends AppCompatActivity {

    private Button btnSignIn;
    private Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);

        // Initialize views
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnCreateAccount = findViewById(R.id.btn_create_account);

        // Set click listeners
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Sign In screen
                Intent intent = new Intent(OpeningScreenActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Create Account screen
                Intent intent = new Intent(OpeningScreenActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}