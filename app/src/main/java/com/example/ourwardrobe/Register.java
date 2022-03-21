package com.example.ourwardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Register extends AppCompatActivity {

    private Button SignButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SignButton = (Button) findViewById(R.id.signu);
        SignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        EditText username = (EditText) findViewById(R.id.username);
        EditText Email = (EditText) findViewById(R.id.RegisterEmail);
        EditText Password = (EditText) findViewById(R.id.RegisterPassword);
        EditText RePassword = (EditText) findViewById(R.id.RePassword);

        MaterialButton Register = (MaterialButton) findViewById(R.id.registr);
        Register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                JSONObject request = new JSONObject();

                try {
                    request.put("nickname", username.getText().toString());
                    request.put("email", Email.getText().toString());
                    request.put("password", Password.getText().toString());
                    request.put("gender", "M");
                    request.put("avatar", "");
                    request.put("OAuthToken", "M");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                    "uNickname": "somebullfuck",
//                    "uPassword": "Il0veGirls",
//                    "nickname": "smth1",
//                    "creationTime": null,
//                    "wardrobeType": "Personal"

                JSONObject wardrobeRequest = new JSONObject();

                try {
                    wardrobeRequest.put("uNickname", username.getText().toString());
                    wardrobeRequest.put("uPassword", Password.getText().toString());
                    wardrobeRequest.put("nickname", "First Wardrobe");
                    wardrobeRequest.put("creationTime", null);
                    wardrobeRequest.put("wardrobeType", "Personal");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                URL url = null;
                URL wardrobeUrl = null;

                try {
                    if (!Password.getText().toString().equals(RePassword.getText().toString())) {
                        Toast.makeText(Register.this, "Passwords don't match..." ,Toast.LENGTH_SHORT).show();
                        return;
                    }

                    url = new URL("http://192.168.56.1:3000/users/register");
                    wardrobeUrl = new URL("http://192.168.56.1:3000/wardrobes/register");

                    try {
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        connection.setRequestProperty("Accept", "*");
                        connection.setRequestProperty("User-Agent", "Chrome");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setDoOutput(true);
                        connection.setRequestMethod("POST");
                        connection.setConnectTimeout(15000);
                        connection.setReadTimeout(15000);

                        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                        wr.writeBytes(request.toString());
                        wr.flush();
                        wr.close();

                        int responseCode = connection.getResponseCode();

                        connection = (HttpURLConnection) wardrobeUrl.openConnection();

                        connection.setRequestProperty("Accept", "*");
                        connection.setRequestProperty("User-Agent", "Chrome");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setDoOutput(true);
                        connection.setRequestMethod("POST");
                        connection.setConnectTimeout(15000);
                        connection.setReadTimeout(15000);

                        wr = new DataOutputStream(connection.getOutputStream());
                        wr.writeBytes(wardrobeRequest.toString());
                        wr.flush();
                        wr.close();

                        int responseCodeWardrobe = connection.getResponseCode();

                        //Response code of 201 means CREATED
                        if (responseCode == 201 && responseCodeWardrobe == 201) {
                            Toast.makeText(Register.this, "Successfully Registered" ,Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{
                            Toast.makeText(Register.this, "Could not register, something wrong with the server: " + responseCode ,Toast.LENGTH_SHORT).show();
                            return;
                        }

//                        System.out.println("Response Body : ");
//                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                        String inputLine;
//                        StringBuffer response = new StringBuffer();

                    } catch (IOException e) {
                        Toast.makeText(Register.this, e.getMessage() ,Toast.LENGTH_SHORT).show();
                    }
                } catch (MalformedURLException e) {
                    Toast.makeText(Register.this, e.getMessage() ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void Login(){
        Intent intent = new Intent(this, loginscreen.class );
        startActivity(intent);
    }
}