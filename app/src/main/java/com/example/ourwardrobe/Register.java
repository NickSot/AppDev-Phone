package com.example.ourwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Register extends AppCompatActivity {

    private class RegisterRequest extends AsyncTask<Void, Void, Void> {
        private String Password;
        private String Nickname;
        private String Email;
        private String RePassword;

        private int responseCode;
        private int responseCodeWardrobe;

        public RegisterRequest(String nickname,  String email, String password, String rePassword) {
            Password = password;
            Nickname = nickname;
            Email = email;
            RePassword = rePassword;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject request = new JSONObject();

            try {
                request.put("nickname", Nickname);
                request.put("email", Email);
                request.put("password", Password);
                request.put("gender", "M");
                request.put("avatar", "");
                request.put("OAuthToken", "M");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject wardrobeRequest = new JSONObject();

            try {
                wardrobeRequest.put("uNickname", Nickname);
                wardrobeRequest.put("uPassword", Password);
                wardrobeRequest.put("nickname", "First Wardrobe");
                wardrobeRequest.put("creationTime", null);
                wardrobeRequest.put("wardrobeType", "Personal");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            URL url = null;
            URL wardrobeUrl = null;

            try {
                if (!Password.equals(RePassword)) {
                    responseCode = -1;
                    return null;
                }

//                url = new URL("http://192.168.56.1:3000/users/register");
//                wardrobeUrl = new URL("http://192.168.56.1:3000/wardrobes/register");
                url = new URL("http://192.168.0.119:3000/users/register");
                wardrobeUrl = new URL("http://192.168.0.119:3000/wardrobes/register");

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

                    responseCode = connection.getResponseCode();

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

                    responseCodeWardrobe = connection.getResponseCode();

                } catch (IOException e) {
//                    Toast.makeText(Register.this, e.getMessage() ,Toast.LENGTH_SHORT).show();
                }
            } catch (MalformedURLException e) {
//                Toast.makeText(Register.this, e.getMessage() ,Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void _) {
            if (responseCode == 201 && responseCodeWardrobe == 201) {
                Toast.makeText(Register.this, "Successfully Registered" ,Toast.LENGTH_SHORT).show();
                Register.this.Login();
            }
            else if (responseCode == -1) {
                Toast.makeText(Register.this, "Passwords don't match..." ,Toast.LENGTH_SHORT).show();
            }
            else if (responseCode == 404){
                Toast.makeText(Register.this, "Email does not exist." ,Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Register.this, "Could not register, something wrong with the server: " + responseCode ,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Button SignButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                RegisterRequest request = new RegisterRequest(username.getText().toString(),
                        Email.getText().toString(),
                        Password.getText().toString(),
                        RePassword.getText().toString());

                request.execute();
            }
        });
    }
    public void Login(){
        Intent intent = new Intent(this, loginscreen.class );
        startActivity(intent);
    }
}