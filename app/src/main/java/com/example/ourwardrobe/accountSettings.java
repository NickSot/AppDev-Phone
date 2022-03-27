package com.example.ourwardrobe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import outwardrobemodels.User;

public class accountSettings extends AppCompatActivity {

    private Button deleteBtn, changePassword;
    private ImageView backBtn;

    private class DeleteAccountRequest extends AsyncTask<Void, Void, Void> {
        int responseCode;
        String responseMessage;

        private String Email;
        private String Password;

        DeleteAccountRequest(String Email, String Password) {
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
                url = new URL("http://192.168.56.1:3000/users/delUser");

                try {
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("User-Agent", "Chrome");
                    connection.setRequestProperty("Content-type", "application/json");
                    connection.setRequestMethod("DELETE");
                    connection.setDoOutput(true);
                    connection.setConnectTimeout(15000);
                    connection.setReadTimeout(15000);

                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes(request.toString());
                    wr.flush();
                    wr.close();

                    Log.println(Log.DEBUG, "req", request.toString());

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
                Toast.makeText(accountSettings.this, "Successfully deleted account", Toast.LENGTH_SHORT).show();
                ApplicationContext.getInstance().setUser(null);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
        deleteBtn=findViewById(R.id.deleteButton);
        backBtn=findViewById(R.id.backBtn);
        changePassword=findViewById(R.id.changePassword);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAlertDialog();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(accountSettings.this, MainActivity.class );
                startActivity(intent);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(accountSettings.this, "Change Password Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CreateAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Account Deletion")
                .setMessage("Are you sure that you want to delete your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        User user = ApplicationContext.getInstance().getUser();
                        DeleteAccountRequest request = new DeleteAccountRequest(user.getNickname(), user.getPassword());
                        request.execute();
                        Intent intent = new Intent(accountSettings.this, loginscreen.class);
                        startActivity(intent);
                        finishActivity(0);
                    }
                })
                .setNegativeButton("No", null);

        dialog.create();
        dialog.show();
    }
}