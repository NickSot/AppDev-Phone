package com.example.ourwardrobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class solocategory extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private static final String TAG = "solocategory";
    private Button shirts, pants, dresses, shoes, jackets, skirts;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    FloatingActionButton camera, gallery;
    String[] wardrobeFamilies = {"Personal Wardrobe", "Wardrobe Family 1", "Wardrobe Family 2", "Wardrobe Family 3"};
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solo_category);

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

        recyclerView = findViewById(R.id.familyRecycler);
        List<userModel> userModelList = new ArrayList<>();

        String[] names = {"User 1", "User 2", "User 3", "User 4"};
        RecyclerViewAdapter usersAdapter;

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(solocategory.this, Camera.class);
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(solocategory.this, SpinnerNew.class);
                startActivity(intent);
            }
        });

        shirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(solocategory.this, shirts.class);
                startActivity(intent);
            }
        });
        ImageButton oButton = (ImageButton) findViewById(R.id.outfit_creator_button);

        oButton.setOnClickListener(view -> startActivity
                (new Intent(this, OutfitCreator.class)));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        for (String s:names) {
            userModel userModel = new userModel(s);

            userModelList.add(userModel);
        }

        usersAdapter = new RecyclerViewAdapter(userModelList);
        recyclerView.setAdapter(usersAdapter);
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
