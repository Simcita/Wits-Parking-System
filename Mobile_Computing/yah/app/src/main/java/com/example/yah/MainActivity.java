package com.example.yah;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText startTimeEditText, endTimeEditText, spotIdEditText;
    private Button submitButton;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startTimeEditText = findViewById(R.id.startTimeEditText);
        endTimeEditText = findViewById(R.id.endTimeEditText);
        spotIdEditText = findViewById(R.id.spotIdEditText);
        submitButton = findViewById(R.id.submitButton);

        client = new OkHttpClient();


        startTimeEditText.setOnClickListener(v -> showTimePickerDialog(startTimeEditText));
        endTimeEditText.setOnClickListener(v -> showTimePickerDialog(endTimeEditText));

        submitButton.setOnClickListener(v -> submitBooking());
    }



    private void showTimePickerDialog(EditText timeEditText) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            String time = hourOfDay + ":" + minute1;
            timeEditText.setText(time);
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void submitBooking() {

        String startTime = startTimeEditText.getText().toString();
        String endTime = endTimeEditText.getText().toString();
        String spotId = spotIdEditText.getText().toString();

        if (startTime.isEmpty() || endTime.isEmpty() || spotId.isEmpty()) {
            Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody formBody = new FormBody.Builder()
                .add("start_time", startTime)
                .add("end_time", endTime)
                .add("spot_id", spotId)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2688313/bookings.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Booking failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                runOnUiThread(() -> {
                    try {
                        if (response.isSuccessful()) {
                            // Log the response body for debugging
                            System.out.println("Server Response: " + responseBody);

                            JSONObject jsonResponse = new JSONObject(responseBody);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(MainActivity.this, "Booking successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Booking failed: " + jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Booking failed: Server error " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Booking failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
