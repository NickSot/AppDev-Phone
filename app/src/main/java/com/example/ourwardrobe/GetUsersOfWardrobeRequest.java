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

public class GetUsersOfWardrobeRequest extends AbstractRequest {
    private Long wId;

    public GetUsersOfWardrobeRequest(Long wId) throws JSONException {
        super("wardrobes/", String.valueOf(wId), "POST");
        this.wId = wId;
    }

    @Override
    protected void afterRequestSend() {
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
