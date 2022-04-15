package com.example.ourwardrobe;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONException;

public class AdminSettings extends WardrobeSettings {

    private class DeleteWardrobeRequest extends AbstractRequest {
        private int responseCode;
        private String responseMessage;

        public DeleteWardrobeRequest() throws JSONException {
            super("wardrobes/", String.valueOf(ApplicationContext.getInstance().getWardrobe().getwId()), "DELETE");
        }

        @Override
        protected void afterRequestSend() {
            if (responseCode == 200) {
                Toast.makeText(AdminSettings.this, "Family deleted", Toast.LENGTH_SHORT).show();
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
