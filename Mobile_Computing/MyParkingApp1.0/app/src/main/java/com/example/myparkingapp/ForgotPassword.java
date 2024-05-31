package com.example.myparkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {
    private boolean passwordVisible1;
    private boolean passwordVisible2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EditText emailEditText = findViewById(R.id.email);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPassword);
        EditText passwordEditText = findViewById(R.id.password_signup);
        Button resetPasswordButton = findViewById(R.id.newPasswordbtn);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUpPasswordVisibilityToggle(passwordEditText, true);
        setUpPasswordVisibilityToggle(confirmPasswordEditText, false);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePasswordReset(emailEditText, passwordEditText, confirmPasswordEditText);
            }
        });
    }

    private void setUpPasswordVisibilityToggle(EditText editText, boolean isPassword) {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= editText.getRight() - editText.getCompoundDrawables()[RIGHT].getBounds().width()) {
                        int selection = editText.getSelectionEnd();
                        if (isPassword ? passwordVisible1 : passwordVisible2) {
                            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0);
                            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            if (isPassword) {
                                passwordVisible1 = false;
                            } else {
                                passwordVisible2 = false;
                            }
                        } else {
                            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_24, 0);
                            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            if (isPassword) {
                                passwordVisible1 = true;
                            } else {
                                passwordVisible2 = true;
                            }
                        }
                        editText.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void handlePasswordReset(EditText emailEditText, EditText passwordEditText, EditText confirmPasswordEditText) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(ForgotPassword.this, "Please fill up all the details", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(ForgotPassword.this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
        } else if (!Password.isValid(password)) {
            Toast.makeText(ForgotPassword.this, "Password must have at least 8 characters, including a digit, letter, and special symbol", Toast.LENGTH_SHORT).show();
        } else {
            // Send email to the user
            String subject = "Password Reset Request";
            String message = "You requested a password reset. Please use the following temporary password to log in: " + password;
            new SendMailTask(email, subject, message).execute();

            Toast.makeText(ForgotPassword.this, "Password reset email sent successfully", Toast.LENGTH_SHORT).show();
        }
    }
}



