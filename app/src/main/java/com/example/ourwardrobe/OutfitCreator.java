package com.example.ourwardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class OutfitCreator extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfitcreator);

        ImageButton wButton = (ImageButton) findViewById(R.id.wardrobe_view_button);

        wButton.setOnClickListener(view -> startActivity
                (new Intent(this, MainActivity.class)));
    }
}
