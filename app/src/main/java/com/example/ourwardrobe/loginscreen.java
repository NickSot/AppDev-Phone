package com.example.ourwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class loginscreen extends AppCompatActivity {

    private Button RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        Login();
        RegisterButton = (Button) findViewById(R.id.signUp);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private class LoginRequest extends AsyncTask<Void, Void, Void> {
        int responseCode;
        String responseMessage;

        private String Email;
        private String Password;

        LoginRequest(String Email, String Password) {
            this.Email = Email;
            this.Password = Password;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject request = new JSONObject();

            try {
                request.put("uNickname", Email);
                request.put("uPassword", Password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            URL url = null;

            try {
                url = new URL("http://131.155.197.124:3000/users/login");

                try {
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Chrome");
                    connection.setRequestProperty("Content-type", "application/json");
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setConnectTimeout(15000);
                    connection.setReadTimeout(15000);

                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes(request.toString());
                    wr.flush();
                    wr.close();

                    Log.println(Log.DEBUG, "req", request.toString());

                    responseCode = connection.getResponseCode();
                    responseMessage = connection.getResponseMessage();

                } catch (IOException e) {
//                    Toast.makeText(loginscreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } catch (MalformedURLException e) {
//                Toast.makeText(loginscreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        protected void onPostExecute(Void param) {
            if (responseCode == 200) {
                Toast.makeText(loginscreen.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                openCategory();
            } else {
                Toast.makeText(loginscreen.this, responseMessage + " "
                        + responseCode, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Login() { //        SETS THE LOGIN PAGE
        TextView Email = (TextView) findViewById(R.id.userEmail);
        TextView Password = (TextView) findViewById(R.id.password);
        MaterialButton Login = (MaterialButton) findViewById(R.id.Login);

        //admin 123
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRequest lr = new LoginRequest(Email.getText().toString(), Password.getText().toString());
                lr.execute();
            }
        });
    }

        public void openCategory() {
            Intent intent = new Intent(this, mainscreen.class);
            startActivity(intent);
        }

        public void register() {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }
    }
