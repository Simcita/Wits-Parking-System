package com.example.myparkingapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText user = findViewById(R.id.username);
        EditText pass = findViewById(R.id.password);

        TextView access = findViewById(R.id.wrong);

        Button SignUp = findViewById(R.id.signup);
        Button Login  = findViewById(R.id.login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String User = user.getText().toString();
                String Passw = pass.getText().toString();

                post("https://lamp.ms.wits.ac.za/home/s2688828/Users.php", "", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Something went wrong
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String myjson = null;
                                    try {
                                        myjson = response.body().string();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                    try {
                                        JSONArray all = new JSONArray(myjson);
                                        for (int i=0; i<all.length(); i++){
                                            JSONObject item=all.getJSONObject(i);
                                            String name = item.getString("USERS_ID");
                                            String pass = item.getString("PASSWORD");
                                            if(name.equals(User) && pass.equals(Passw)){
                                                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                                                startActivity(intent);
                                                setContentView(R.layout.activity_main3);
                                                break;
                                            }
                                            else{
                                                    if(i == all.length()-1){
                                                        if(!(name.equals(User)) && !(pass.equals(Passw))){
                                                            Toast.makeText(MainActivity.this, "Your Username or Password might be wrong", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            // Do what you want to do with the response.
                        } else {
                            // Request not successful
                        }
                    }
                });

            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                setContentView(R.layout.activity_main2);
            }
        });
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
    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }*/
}



