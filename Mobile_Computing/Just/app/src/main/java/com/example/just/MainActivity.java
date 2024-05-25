package com.example.just;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonSign;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSign = findViewById(R.id.buttonRegister);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://lamp.ms.wits.ac.za/home/s2688313/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    showError("Please provide a username");
                } else if (!isValidPassword(password)) {
                    showError("Password must be at least 6 characters");
                } else {
                    signUp(username, password);
                }
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private void signUp(String username, String password) {
        Call<JsonObject> call = apiService.signUp(username, password);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject responseObject = response.body();
                    if (responseObject != null && responseObject.has("success")) {
                        boolean success = responseObject.get("success").getAsBoolean();
                        if (success) {
                            Toast.makeText(MainActivity.this, "Sign-up successful", Toast.LENGTH_SHORT).show();
                        } else {
                            String errorMessage = "Sign-up failed";
                            if (responseObject.has("message")) {
                                errorMessage += ": " + responseObject.get("message").getAsString();
                            }
                            showError(errorMessage);
                        }
                    } else {
                        showError("Unexpected response from server");
                    }
                } else {
                    showError("Server error. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                showError("Network error. Please try again later.");
            }
        });
    }
}
