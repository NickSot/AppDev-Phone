package com.example.ourwardrobe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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

    private class DeleteWardrobeRequest extends AbstractRequest {
        private int responseCode;
        private String responseMessage;

        public DeleteWardrobeRequest() throws JSONException {
            super("wardrobes/", String.valueOf(ApplicationContext.getInstance().getWardrobe().getwId()), "DELETE");
        }

        @Override
        protected void afterRequestSend() {
            if (responseCode == 200) {
                Toast.makeText(adminsettings.this, "Family deleted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deleteBtn.setVisibility(View.VISIBLE);
        deleteBtn.setEnabled(true);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteWardrobeRequest request = null;

                try {
                    request = new DeleteWardrobeRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                request.execute();
            }
        });
    }
}
