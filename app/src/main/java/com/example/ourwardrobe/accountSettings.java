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

public class accountSettings extends AppCompatActivity {

    private Button deleteBtn, changePassword;
    private ImageView backBtn;

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
                Intent intent = new Intent(accountSettings.this, MainActivity.class );
                startActivity(intent);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(accountSettings.this, "Change Password Clicked", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(accountSettings.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null);

        dialog.create();
        dialog.show();
    }
}