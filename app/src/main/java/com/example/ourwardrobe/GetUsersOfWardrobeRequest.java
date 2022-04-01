package com.example.ourwardrobe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.telecom.Call;
import android.util.Base64;

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

import outwardrobemodels.Clothe;
import outwardrobemodels.User;
import outwardrobemodels.Wardrobe;

public class GetUsersOfWardrobeRequest extends AsyncTask<Void, Void, Void> {
    private int responseCode;
    private String responseMessage;

    private Long wId;

    public GetUsersOfWardrobeRequest(Long wId) {
        this.wId = wId;
    }

    private Callback cb;

    public void setCallback(Callback cb) {
        this.cb = cb;
    }

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
            url = new URL("http://192.168.56.1:3000/wardrobes/" + String.valueOf(wId));

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

    @Override
    protected void onPostExecute(Void unused) {
        if (responseCode == 200) {
            JSONObject wardrobeObject = null;

            try {
                wardrobeObject = new JSONObject(responseMessage);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray usersObjectArray = null;

            try {
                usersObjectArray = wardrobeObject.getJSONArray("userList");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ApplicationContext.getInstance().getWardrobe().getUsers().clear();

            for (int i = 0; i < usersObjectArray.length(); i++) {
                try {
                    JSONObject obj = usersObjectArray.getJSONObject(i);

                    Long uId = Long.valueOf(obj.get("uId").toString());
                    String nickname = obj.get("Nickname").toString();

                    ApplicationContext.getInstance().getWardrobe().addUser(uId, nickname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (cb != null)
                cb.function();
        }
    }
}
