package com.example.ecozym;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpStep1Activity extends AppCompatActivity {

    private EditText etEmail, etPassword, etConfirmPassword;
    private ImageButton btnBack, btnTogglePassword, btnToggleConfirmPassword;
    private Button btnNext;
    private TextView btnPrevious, tvLogin;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_step1);

        // Initialize views
        initViews();
        // Set listeners
        setListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnBack = findViewById(R.id.btn_back);
        btnTogglePassword = findViewById(R.id.btn_toggle_password);
        btnToggleConfirmPassword = findViewById(R.id.btn_toggle_confirm_password);
        btnNext = findViewById(R.id.btn_next);
        btnPrevious = findViewById(R.id.btn_previous);
        tvLogin = findViewById(R.id.tv_login);
    }

    private void setListeners() {
        // Back button click
        btnBack.setOnClickListener(v -> finish());

        // Toggle password visibility
        btnTogglePassword.setOnClickListener(v -> togglePasswordVisibility(etPassword, btnTogglePassword));
        btnToggleConfirmPassword.setOnClickListener(v -> toggleConfirmPasswordVisibility(etConfirmPassword, btnToggleConfirmPassword));

        // Previous button is disabled in first step
        btnPrevious.setEnabled(false);
        btnPrevious.setAlpha(0.5f);

        // Login text click
        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpStep1Activity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Next button click
        btnNext.setOnClickListener(v -> {
            if (validateInputs()) {
                // Store email and password in SharedPreferences or as extras in Intent
                saveUserCredentials();
                // Navigate to Step 2
                Intent intent = new Intent(SignUpStep1Activity.this, SignUpStep2Activity.class);
                startActivity(intent);
            }
        });

        // Add text change listeners to validate in real-time
        setupTextWatchers();
    }

    private void togglePasswordVisibility(EditText editText, ImageButton toggleButton) {
        isPasswordVisible = !isPasswordVisible;
        if (isPasswordVisible) {
            // Show password
            editText.setTransformationMethod(null);
            toggleButton.setImageResource(R.drawable.ic_visibility_off);
        } else {
            // Hide password
            editText.setTransformationMethod(new PasswordTransformationMethod());
            toggleButton.setImageResource(R.drawable.ic_visibility);
        }
        // Move cursor to the end
        editText.setSelection(editText.getText().length());
    }

    private void toggleConfirmPasswordVisibility(EditText editText, ImageButton toggleButton) {
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
        if (isConfirmPasswordVisible) {
            // Show password
            editText.setTransformationMethod(null);
            toggleButton.setImageResource(R.drawable.ic_visibility_off);
        } else {
            // Hide password
            editText.setTransformationMethod(new PasswordTransformationMethod());
            toggleButton.setImageResource(R.drawable.ic_visibility);
        }
        // Move cursor to the end
        editText.setSelection(editText.getText().length());
    }

    private boolean validateInputs() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validate email
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email address");
            return false;
        }

        // Validate password
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            return false;
        } else if (password.length() < 8) {
            etPassword.setError("Password must be at least 8 characters");
            return false;
        }

        // Validate confirm password
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError("Confirm password is required");
            return false;
        } else if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return false;
        }

        return true;
    }

    private void saveUserCredentials() {
        // Here you would typically store these credentials securely
        // For a real app, consider using encrypted SharedPreferences, DataStore, or a secure database
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Example using SharedPreferences (not secure for real passwords)
        getSharedPreferences("EcoZymPrefs", MODE_PRIVATE)
                .edit()
                .putString("user_email", email)
                .putString("signup_step", "1")
                .apply();

        // Note: In a real app, never store raw passwords in SharedPreferences
        // This is just for demonstration purposes
    }

    private void setupTextWatchers() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Clear errors when user types
                if (etEmail.hasFocus()) {
                    etEmail.setError(null);
                } else if (etPassword.hasFocus()) {
                    etPassword.setError(null);
                } else if (etConfirmPassword.hasFocus()) {
                    etConfirmPassword.setError(null);
                }
            }
        };

        etEmail.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etConfirmPassword.addTextChangedListener(textWatcher);
    }
}