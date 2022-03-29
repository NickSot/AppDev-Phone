package com.example.ourwardrobe;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

    ArrayList<Integer> OCImageIds = new ArrayList < >(Arrays.asList(
            R.drawable.cl1,R.drawable.cl2,R.drawable.cl3,R.drawable.cl4,
            R.drawable.cl5,R.drawable.cl6,R.drawable.cl7,R.drawable.cl8,
            R.drawable.cl9
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfitcreator);

        GridView gridView = findViewById(R.id.OCGrid);

        gridView.setAdapter(new ImageAdapterOC(OCImageIds,this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int item_pos = OCImageIds.get(position);
            }
        });

        Button dButton = (Button) findViewById(R.id.deleteAllButton);

        dButton.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           OCImageIds.clear();
                                           //ImageAdapterOC.notifyDataSetChanged();
                                       }
                                   });

        ImageButton wButton = (ImageButton) findViewById(R.id.wardrobe_view_button);

        wButton.setOnClickListener(view -> startActivity
                (new Intent(this, MainActivity.class)));


    }


}
