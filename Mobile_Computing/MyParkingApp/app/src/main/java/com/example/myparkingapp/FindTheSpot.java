package com.example.myparkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FindTheSpot extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_the_spot);
        LinearLayout mainLayout = findViewById(R.id.spot_layout);
        mainLayout.setOrientation(LinearLayout.VERTICAL);




        post("https://lamp.ms.wits.ac.za/home/s2729931/Parking_Lot.php", "", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(FindTheSpot.this, "Request failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myjson = response.body().string();
                    runOnUiThread(() -> handleResponse(myjson, mainLayout));
                } else {
                    runOnUiThread(() -> Toast.makeText(FindTheSpot.this, "Request not successful: " + response.message(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }


    private void handleResponse(String myjson, LinearLayout mainLayout) {
        try {
            JSONArray all = new JSONArray(myjson);
            if (all.length() > 0) {
                Toast.makeText(FindTheSpot.this, "Here is your Parking Spot.", Toast.LENGTH_SHORT).show();

                JSONObject item = all.getJSONObject(0);
                String name = item.getString("SPOT_ID");
                String nme = "Get Directions";

                Button t = new Button(FindTheSpot.this);
                t.setWidth(20);
                t.setHeight(150);
                t.setTextSize(20);
                t.setText(nme);

                mainLayout.addView(t);

                t.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FindTheSpot.this, GoogleMaps.class);

                        Intent intent2 = getIntent();
                        String importantText = intent2.getStringExtra("IMPORTANT_TEXT");
                        intent.putExtra("IMPORTANT_TEXT", importantText);
                        startActivity(intent);
                        //setContentView(R.layout.maps);
                    }
                });
            } else {
                Toast.makeText(FindTheSpot.this, "Sorry, there are no parking lots available for parking.", Toast.LENGTH_SHORT).show();
            }
;

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(FindTheSpot.this, "Failed to parse response", Toast.LENGTH_SHORT).show();
        }
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    Call post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}