package com.example.ourwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class wardrobesettings extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Button leaveBtn, submitBtn;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobesettings);

        textView = (TextView) findViewById(R.id.textJoin);
        editText = (EditText) findViewById(R.id.token);
        leaveBtn = (Button) findViewById(R.id.leaveFamily);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        backBtn = (ImageView) findViewById(R.id.backBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(wardrobesettings.this, "Family Joined", Toast.LENGTH_SHORT).show();
            }
        });

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(wardrobesettings.this, "Family Left", Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(wardrobesettings.this, MainActivity.class );
                startActivity(intent);
            }
        });
    }
}