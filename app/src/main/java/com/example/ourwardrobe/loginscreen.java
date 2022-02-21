package com.example.ourwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class loginscreen extends AppCompatActivity {

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
                    Toast.makeText(loginscreen.this,"Login Successful", Toast.LENGTH_SHORT).show();
                    openCategory();

                }else
                    //incorrect password
                    Toast.makeText(loginscreen.this,"Login Failed, try again or click on Go to Registration Page " +
                                    "to register",
                            Toast.LENGTH_SHORT).show();
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