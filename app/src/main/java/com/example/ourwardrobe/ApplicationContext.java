package com.example.ourwardrobe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

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

public class ApplicationContext {
    private static ApplicationContext instance;
    private User user;
    private Wardrobe currentWardrobe;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getUserInfo() {
        int responseCode = -1;
        String responseMessage = "";

        JSONObject request = new JSONObject();

        try {
            request.put("uNickname", user.getNickname());
            request.put("uPassword", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        URL url = null;

        try {
            url = new URL("http://192.168.56.1:3000/users/getInfo");

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

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                user = new User(Long.getLong(userObject.get("uId").toString()), userObject.get("nickname").toString(), userObject.get("password").toString(), bitmap, userObject.get("oauthToken").toString(), userObject.get("gender").toString());
                user.setWardrobes(wardrobes);

                ApplicationContext.this.user = user;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private ApplicationContext() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static ApplicationContext getInstance() {
        if (instance == null)
            instance = new ApplicationContext();

        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setWardrobe(long wardrobeId) {
        currentWardrobe = user.getWardrobes().stream().filter(p -> p.getwId() == wardrobeId).findFirst().get();
    }

    public Wardrobe getWardrobe() {
        return currentWardrobe;
    }
}
