package com.example.ourwardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ourwardrobe.databinding.FabBinding;
import com.google.android.material.snackbar.Snackbar;

public class mainscreen extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private FabBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Click on the Camera or Gallery to upload pictures", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                gallery();
            }
        });

        binding.fabcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Click on the Camera or Gallery to upload pictures", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                camera();
            }
        });
    }
    public void camera(){
        Intent intent = new Intent(mainscreen.this, Camera.class );
        startActivity(intent);
    }

    public void gallery(){
        Intent intent = new Intent(mainscreen.this, Gallery.class );
        startActivity(intent);
    }

}