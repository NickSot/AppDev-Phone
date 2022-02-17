package com.example.loginactivitycamerauploadpictures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class Register extends AppCompatActivity {

    private Button SignButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SignButton = (Button) findViewById(R.id.signu);
        SignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Login();
            }
        });

        EditText username = (EditText) findViewById(R.id.username);
        EditText Email = (EditText) findViewById(R.id.RegisterEmail);
        EditText Password = (EditText) findViewById(R.id.RegisterPassword);
        EditText RePassword = (EditText) findViewById(R.id.RePassword);

        MaterialButton Register = (MaterialButton) findViewById(R.id.registr);
        Register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Toast.makeText(Register.this, "Successfully Registered" ,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Login(){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }
}
