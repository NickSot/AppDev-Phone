package com.example.ourwardrobe;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class OutfitCreator extends AppCompatActivity {

//    ArrayList<Integer> OCImageIds = new ArrayList < >(Arrays.asList(
//            R.drawable.cl1,R.drawable.cl2,R.drawable.cl3,R.drawable.cl4,
//            R.drawable.cl5,R.drawable.cl6,R.drawable.cl7,R.drawable.cl8,
//            R.drawable.cl9
//    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_outfitcreator);
        GridView gridView = findViewById(R.id.OCGrid);

        ArrayList<Bitmap> OCImageIds = ApplicationContext.getInstance().getOutfitImages();
        gridView.setAdapter(new ImageAdapter(OCImageIds,this, 350, 450));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bitmap item_pos = OCImageIds.get(position);
            }
        });

        Button dButton = (Button) findViewById(R.id.deleteAllButton);

        dButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ApplicationContext.getInstance().getOutfitImages().clear();
               OutfitCreator.this.recreate();
           }
        });

        ImageButton wButton = (ImageButton) findViewById(R.id.wardrobe_view_button);

        wButton.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
    }
}