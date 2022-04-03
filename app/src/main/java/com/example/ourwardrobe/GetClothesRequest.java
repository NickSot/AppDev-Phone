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

public class GetClothesRequest extends AbstractRequest {
    private Long wId;
    private String clotheType;

    private ArrayList<Clothe> clothes = new ArrayList<>();

    public GetClothesRequest(Long wId, String clotheType) throws JSONException {
        super("wardrobes/", String.valueOf(wId), "POST");

        this.wId = wId;
        this.clotheType = clotheType;
    }

    @Override
    protected void afterRequestSend() {
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

                    Long ogId = Long.valueOf(obj.get("originalWardrobeId").toString());
                    String clotheType = obj.get("clothType").toString();

                    if (!clotheType.equals(this.clotheType))
                        continue;

                    Long cId = Long.valueOf(obj.get("cId").toString());

                    byte[] imageBytes = Base64.decode(obj.get("image").toString(), 1);
                    Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                    Clothe c = new Clothe(cId, clotheType, image, ogId);
                    clothes.add(c);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ApplicationContext.getInstance().getWardrobe().getClothes().clear();
            ApplicationContext.getInstance().getWardrobe().getClothes().addAll(clothes);

            if (cb != null)
                cb.function();
        }
    }
}
