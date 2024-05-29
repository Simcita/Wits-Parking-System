package com.example.myparkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FindTheLot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findthelot);

        Button first = findViewById(R.id.first_year);
        Button second = findViewById(R.id.second_year);
        Button third = findViewById(R.id.third_year);
        Button staff = findViewById(R.id.staff);

        final String firstimportantText = first.getText().toString();
        final String secondimportantText = second.getText().toString();
        final String thirdimportantText = third.getText().toString();
        final String staffimportantText = staff.getText().toString();

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindTheLot.this, FindTheSpot.class);
                intent.putExtra("IMPORTANT_TEXT", firstimportantText);

                startActivity(intent);
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindTheLot.this, FindTheSpot.class);
                intent.putExtra("IMPORTANT_TEXT", secondimportantText);
                startActivity(intent);
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindTheLot.this, FindTheSpot.class);
                intent.putExtra("IMPORTANT_TEXT", thirdimportantText);
                startActivity(intent);
            }
        });
        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindTheLot.this, FindTheSpot.class);
                intent.putExtra("IMPORTANT_TEXT", staffimportantText);
                startActivity(intent);
            }
        });

    }

        private void handleButtonClick (String buttonText){
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

