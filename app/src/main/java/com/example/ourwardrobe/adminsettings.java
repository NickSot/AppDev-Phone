package com.example.ourwardrobe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class adminsettings extends wardrobesettings {

    private class DeleteWardrobeRequest extends AsyncTask<Void, Void, Void> {
        private int responseCode;
        private String responseMessage;

        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject request = new JSONObject();

            try {
                request.put("uNickname", ApplicationContext.getInstance().getUser().getNickname());
                request.put("uPassword", ApplicationContext.getInstance().getUser().getPassword());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            URL url = null;

            try {
                url = new URL("http://192.168.56.1:3000/wardrobes/" + String.valueOf(ApplicationContext.getInstance().getWardrobe().getwId()));

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

        @Override
        protected void onPostExecute(Void unused) {
            if (responseCode == 200) {
                Toast.makeText(adminsettings.this, "Family deleted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deleteBtn.setVisibility(View.VISIBLE);
        deleteBtn.setEnabled(true);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteWardrobeRequest request = new DeleteWardrobeRequest();
                request.execute();
            }
        });
    }
}
