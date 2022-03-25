package com.example.ourwardrobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private Button shirts, pants, dresses, shoes, jackets, skirts;
    FloatingActionButton camera, gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoryinterface);


        camera = findViewById(R.id.fabcamera);
        gallery = findViewById(R.id.fabgallery);
        camera.bringToFront();
        gallery.bringToFront();

        shirts=findViewById(R.id.shirts);
        pants=findViewById(R.id.pants);
        dresses=findViewById(R.id.dresses);
        shoes=findViewById(R.id.shoes);
        jackets=findViewById(R.id.jackets);
        skirts=findViewById(R.id.skirts);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Camera.class);
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SpinnerNew.class);
                startActivity(intent);
            }
        });

        shirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, shirts.class);
                startActivity(intent);
            }
        });

        ImageButton oButton = (ImageButton) findViewById(R.id.outfit_creator_button);

        oButton.setOnClickListener(view -> startActivity
                (new Intent(this, OutfitCreator.class)));

    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.accset:
                Intent a = new Intent(this, accountSettings.class);
                this.startActivity(a);
                return true;
            case R.id.wardset:
                Intent b = new Intent(this, wardrobesettings.class);
                this.startActivity(b);
                return true;
            case R.id.logout:
                Intent c = new Intent(this, loginscreen.class);
                this.startActivity(c);
                return true;
            default:
                return false;
        }
    }

}
