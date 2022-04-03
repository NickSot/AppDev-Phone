package com.example.ourwardrobe;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateClotheRequest extends AbstractRequest {
    public CreateClotheRequest(String stream, String clotheType, Long originalWardrobeId) throws JSONException {
        super("clothes/register/", "", "POST", new JSONObject()
                .put("uNickname", ApplicationContext.getInstance().getUser().getNickname())
                .put("uPassword", ApplicationContext.getInstance().getUser().getPassword())
                .put("originalWardrobeId", originalWardrobeId)
                .put("image", stream)
                .put("clothType", clotheType));
    }

    @Override
    protected void afterRequestSend() {
        if (responseCode != 200){
            Log.println(Log.ERROR, "important!", String.valueOf(responseCode));
        }
    }
}