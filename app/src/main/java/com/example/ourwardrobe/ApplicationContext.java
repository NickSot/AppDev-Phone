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
    private class GetUserInfoRequest extends AbstractRequest {
        public GetUserInfoRequest() throws JSONException {
            super("users/getInfo", "", "POST");
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void afterRequestSend() {
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

                    user = new User(Long.valueOf(userObject.get("uId").toString()), userObject.get("nickname").toString(), userObject.get("password").toString(), bitmap, userObject.get("oauthToken").toString(), userObject.get("gender").toString());
                    user.setWardrobes(wardrobes);

                    ApplicationContext.this.user = user;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static ApplicationContext instance;
    private User user;
    private Wardrobe currentWardrobe;

    private ArrayList<Bitmap> outfitCreatorImages = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getUserInfo(Callback cb) {
        GetUserInfoRequest request = null;

        try {
            request = new GetUserInfoRequest();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        request.setCallback(cb);
        request.execute();
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

    public void addImageToOutfit(Bitmap image) {
        if (outfitCreatorImages.size() <= 9)
            outfitCreatorImages.add(image);
    }

    public void removeImageFromOutfit(Bitmap image) {
        for (Bitmap img: outfitCreatorImages)
            if (img.sameAs(image))
                outfitCreatorImages.remove(img);
    }

    public ArrayList<Bitmap> getOutfitImages() {
        return outfitCreatorImages;
    }
}
