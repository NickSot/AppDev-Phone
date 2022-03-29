package com.example.ourwardrobe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, AdapterView.OnItemSelectedListener {

    private Button shirts, pants, dresses, shoes, jackets, skirts;
    FloatingActionButton camera, gallery;
    String[] wardrobeFamilies = {"Personal Wardrobe", "Wardrobe Family 1", "Wardrobe Family 2", "Wardrobe Family 3"};
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Object [] wardrobeNicknames = ApplicationContext.getInstance().getUser().getWardrobes().stream().map(w -> w.getNickname() + ", ID: " + w.getwId().toString()).toArray();

        wardrobeFamilies = (Arrays.copyOf(wardrobeNicknames, wardrobeNicknames.length, String[].class));

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

        Spinner spin = (Spinner) findViewById(R.id.wardrobespinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.color_spinner_layout, wardrobeFamilies);
        arrayAdapter.setDropDownViewResource(R.layout.color_spinner_dropdown);
        spin.setAdapter(arrayAdapter);

        recyclerView = findViewById(R.id.familyRecycler);
        List<userModel> userModelList = new ArrayList<>();

        String[] names = {"Jessica", "Amy", "Chantal", "Emily", "Christie", "Annabelle", "Joan"};
        RecyclerViewAdapter usersAdapter;

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

        pants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, pants.class);
                startActivity(intent);
            }
        });

        skirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, skirts.class);
                startActivity(intent);
            }
        });

        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, dresses.class);
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, shoes.class);
                startActivity(intent);
            }
        });

        jackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, jackets.class);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        final Intent intent;
        switch (position){
            case 1:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
