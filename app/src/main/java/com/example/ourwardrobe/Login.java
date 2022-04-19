package com.example.ourwardrobe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Base64;

import ourwardrobemodels.User;
import ourwardrobemodels.Wardrobe;

public class Login extends AppCompatActivity {

private Button RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        Login();
        RegisterButton = (Button) findViewById(R.id.signUp);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private class LoginRequest extends AbstractRequest {
        LoginRequest(String Email, String Password) throws JSONException {
            super("users/login", "", "POST", new JSONObject().put("uNickname", Email).put("uPassword", Password));
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

                    // [13, 14, 15, 188, -19]
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    user = new User(Long.getLong(userObject.get("uId").toString()), userObject.get("nickname").toString(), userObject.get("password").toString(), bitmap, userObject.get("oauthToken").toString(), userObject.get("gender").toString());
                    user.setWardrobes(wardrobes);

                    ApplicationContext.getInstance().setUser(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//
                Toast.makeText(Login.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                openCategory();
            } else if (responseCode == 404) {
                Toast.makeText(Login.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Login.this, responseMessage + " "
                        + responseCode, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Login(){
        TextView Email = (TextView) findViewById(R.id.userEmail);
        TextView Password = (TextView) findViewById(R.id.password);
        MaterialButton Login = (MaterialButton) findViewById(R.id.Login);

        //admin 123
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRequest lr = null;

                try {
                    lr = new LoginRequest(Email.getText().toString(), Password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                lr.execute();
            }
        });
    }

    public void openCategory(){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

    public void register(){
        Intent intent = new Intent(this, Register.class );
        startActivity(intent);
    }


}