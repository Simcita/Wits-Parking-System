package com.example.myparkingapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class reservethespot extends AppCompatActivity {

    private String buttonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservethespot);

        // Retrieve the button text from the Intent
        buttonText = getIntent().getStringExtra("BUTTON_TEXT");

        // Use the button text as needed, for example display it in a TextView
        //TextView textView = findViewById(R.id.textView);  // Make sure you have a TextView in your layout with this ID
        //textView.setText(buttonText);

        // Show a Toast message with the button text
        Toast.makeText(this, "Received button text: " + buttonText, Toast.LENGTH_SHORT).show();
    }
}
