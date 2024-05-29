package com.example.myparkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class choice extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_the_spot);

        // Set click listeners for all buttons
        setButtonListeners();
    }

    private void setButtonListeners() {
        // List all button IDs
        List<Integer> buttonIds = new ArrayList<>();
        buttonIds.add(R.id.first_year);
        buttonIds.add(R.id.second_year);
        buttonIds.add(R.id.third_year);
        // Add the rest of your button IDs here

        // Set click listener for each button
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        // Determine which button was clicked
        String buttonText = ((Button) v).getText().toString();
        handleButtonClick(buttonText);
    }

    private void handleButtonClick(String buttonText) {
        // Use the button text as needed
        Toast.makeText(this, "Button " + buttonText + " clicked", Toast.LENGTH_SHORT).show();

        // Create an Intent to start the new activity
        Intent intent = new Intent(this, GoogleMaps.class);

        // Put the button text as an extra
        intent.putExtra("BUTTON_TEXT", buttonText);

        // Start the new activity
        startActivity(intent);
    }
}
