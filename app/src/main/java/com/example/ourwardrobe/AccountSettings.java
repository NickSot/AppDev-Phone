package com.example.ourwardrobe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ourwardrobemodels.User;

public class AccountSettings extends AppCompatActivity {

    private Button deleteBtn, changePassword;
    private ImageView backBtn;

    private class DeleteAccountRequest extends AbstractRequest {

        DeleteAccountRequest(String Email, String Password) throws JSONException {
            super("users/delUser", "", "DELETE", new JSONObject()
                .put("uNickname", Email)
                .put("uPassword", Password));
        }

        @Override
        protected void afterRequestSend() {
            if (responseCode == 200) {
                Toast.makeText(AccountSettings.this, "Successfully deleted account", Toast.LENGTH_SHORT).show();
                ApplicationContext.getInstance().setUser(null);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
        deleteBtn=findViewById(R.id.deleteButton);
        backBtn=findViewById(R.id.backBtn);
        changePassword=findViewById(R.id.changePassword);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAlertDialog();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountSettings.this, MainActivity.class );
                startActivity(intent);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountSettings.this, "Change Password Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CreateAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Account Deletion")
                .setMessage("Are you sure that you want to delete your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        User user = ApplicationContext.getInstance().getUser();
                        DeleteAccountRequest request = null;

                        try {
                            request = new DeleteAccountRequest(user.getNickname(), user.getPassword());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        request.execute();
                        Intent intent = new Intent(AccountSettings.this, Login.class);
                        startActivity(intent);
                        finishActivity(0);
                    }
                })
                .setNegativeButton("No", null);

        dialog.create();
        dialog.show();
    }
}