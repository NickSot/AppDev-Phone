package com.example.ourwardrobe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

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

import outwardrobemodels.Clothe;

public class GetClothesRequest extends AsyncTask<Void, Void, Void> {
    private Long wId;
    private String clotheType;

    private int responseCode;
    private String responseMessage;

    private ArrayList<Clothe> clothes = new ArrayList<>();

    public interface Callback {
        void function();
    }

    private Callback cb;

    public void setCallback(Callback cb) {
        this.cb = cb;
    }

    public GetClothesRequest(Long wId, String clotheType) {
        this.wId = wId;
        this.clotheType = clotheType;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Send the request to retrieve the clothes of that particular wardrobe

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
        // Set the image array
        if (responseCode == 200) {
            JSONObject wardrobeObject = null;

            try {
                wardrobeObject = new JSONObject(responseMessage);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray clotheObjectArray = null;

            try {
                clotheObjectArray = wardrobeObject.getJSONArray("clothList");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < clotheObjectArray.length(); i++) {

                try {
                    JSONObject obj = clotheObjectArray.getJSONObject(i);

                    Long ogId = Long.valueOf(obj.get("OriginalWardrobeId").toString());
                    String clotheType = obj.get("ClothType").toString();

                    if (!clotheType.equals(this.clotheType))
                        continue;

                    Long cId = Long.valueOf(obj.get("cId").toString());

                    byte[] imageBytes = Base64.decode(obj.get("Image").toString(), 1);
                    Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                    Clothe c = new Clothe(cId, clotheType, image, ogId);
                    clothes.add(c);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ApplicationContext.getInstance().getWardrobe().getClothes().clear();
            ApplicationContext.getInstance().getWardrobe().getClothes().addAll(clothes);

            cb.function();
        }
    }
}
