package com.example.ourwardrobe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

import outwardrobemodels.User;
import outwardrobemodels.Wardrobe;

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
//                url = new URL("http://192.168.56.1:3000/users/login");
                url = new URL("http://10.30.61.13:3000/users/login");

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

                    responseCode = connection.getResponseCode();
                    responseMessage = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

                } catch (IOException e) {
//                    Toast.makeText(loginscreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } catch (MalformedURLException e) {
//                Toast.makeText(loginscreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        protected void onPostExecute(Void param) {
            if (responseCode == 200) {
                JSONObject userObject = null;

                try {
                    userObject = new JSONObject(responseMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                User user = null;

                JSONArray wardrobesObject = null;

                try {
                    wardrobesObject = userObject.getJSONArray("wardList");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayList<Wardrobe> wardrobes = new ArrayList<Wardrobe>();

                for (int i = 0; i < wardrobesObject.length(); i++) {
                    JSONObject obj = null;

                    try {
                        obj = wardrobesObject.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Wardrobe w = null;

                    try {
                        w = new Wardrobe(Long.valueOf(obj.get("wId").toString()), obj.get("Nickname").toString(), obj.get("CreationTime").toString(), obj.get("WardrobeType").toString(), Long.valueOf(obj.get("AdminId").toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    wardrobes.add(w);
                }

                try {
                    byte[] bytes = Base64.getDecoder().decode(userObject.get("avatar").toString());

                    // [13, 14, 15, 188, -19]
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    user = new User(Long.getLong(userObject.get("uId").toString()), userObject.get("nickname").toString(), userObject.get("password").toString(), bitmap, userObject.get("oauthToken").toString(), userObject.get("gender").toString());
                    user.setWardrobes(wardrobes);

                    ApplicationContext.getInstance().setUser(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//
                Toast.makeText(loginscreen.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                openCategory();
            } else {
                Toast.makeText(loginscreen.this, responseMessage + " "
                        + responseCode, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Login(){
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

    public void openCategory(){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

    public void register(){
        Intent intent = new Intent(this, Register.class );
        startActivity(intent);
    }


}