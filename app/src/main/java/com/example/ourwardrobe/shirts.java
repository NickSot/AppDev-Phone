package com.example.ourwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class shirts extends AppCompatActivity {

    ArrayList<Integer> mImageIds = new ArrayList<>(Arrays.asList(
            R.drawable.shirt1, R.drawable.shirt2, R.drawable.shirt3, R.drawable.shirt4
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shirts);

        GridView gridView = findViewById(R.id.gridshirts);
        gridView.setAdapter(new ImageAdapter(mImageIds,this));
    }
}