package com.example.timepicker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timepicker.ApiService;
import com.example.timepicker.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class reservethespot extends AppCompatActivity {

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservethespot);

        TimePicker startTimePicker = findViewById(R.id.startTimePicker);
        TimePicker endTimePicker = findViewById(R.id.endTimePicker);
        Button reserveButton = findViewById(R.id.buttonReserve);
        String username = "name"; // Replace with your logic to get username
        String button = getIntent().getStringExtra("BUTTON_NAME");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://lamp.ms.wits.ac.za/home/s2688313/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startHour = startTimePicker.getHour();
                int startMinute = startTimePicker.getMinute();
                int endHour = endTimePicker.getHour();
                int endMinute = endTimePicker.getMinute();

                // Call the API method
                Call<JsonObject> call = apiService.reservethespot(
                        String.format("%02d:%02d", startHour, startMinute),
                        String.format("%02d:%02d", endHour, endMinute),
                        username,
                        button
                );

                private void such(String username, String button) {
                    Call<JsonObject> call = apiService.reservethespot(username, button, StartTime, EndTime);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.isSuccessful()) {
                                JsonObject responseObject = response.body();
                                if (responseObject != null && responseObject.has("success")) {
                                    boolean success = responseObject.get("success").getAsBoolean();
                                    if (success) {
                                        Toast.makeText(reservethespot.this, "reservation successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String errorMessage = "reservation failed";
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




                // Execute the API call asynchronously
               /* call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            // Handle successful response
                            Toast.makeText(reservethespot.this, "Parking reserved successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Handle unsuccessful response
                            Toast.makeText(reservethespot.this, "Failed to reserve parking", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        // Handle failure
                        Toast.makeText(reservethespot.this, "Failed to reserve parking: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });
    }


}
