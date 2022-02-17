package com.example.loginactivitycamerauploadpictures;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login();


    }

    public void Login(){
        TextView Email = (TextView) findViewById(R.id.userEmail);
        TextView Password = (TextView) findViewById(R.id.password);
        MaterialButton Login = (MaterialButton) findViewById(R.id.Login);


        //admin 123
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Email.getText().toString().equals("bla")
                        && Password.getText().toString().equals("123")){
                    //correct password
                    Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();

                    openCamera();

                }else
                    //incorrect password
                    Toast.makeText(MainActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void openCamera(){
        Intent intent = new Intent(this, Camera.class );
        startActivity(intent);
    }
}
