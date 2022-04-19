package com.example.ourwardrobe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.ImageButton;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;
import java.util.stream.Collectors;

import ourwardrobemodels.User;
import ourwardrobemodels.Wardrobe;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, AdapterView.OnItemSelectedListener {

    private Button shirts, pants, dresses, shoes, jackets, skirts;
    FloatingActionButton camera, gallery;
    String[] wardrobeFamilies = {"Personal Wardrobe", "Wardrobe Family 1", "Wardrobe Family 2", "Wardrobe Family 3"};
    RecyclerView recyclerView;

    @SuppressLint({"NewApi", "WrongThread"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        ApplicationContext.getInstance().getUserInfo(() -> {
            User user = ApplicationContext.getInstance().getUser();

            Object [] wardrobeNicknames = user.getWardrobes().stream().map(w -> w.getNickname() + ", ID: " + w.getwId().toString()).toArray();

            wardrobeFamilies = (Arrays.copyOf(wardrobeNicknames, wardrobeNicknames.length, String[].class));

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

            android.widget.Spinner spin = (android.widget.Spinner) findViewById(R.id.wardrobespinner);
            spin.setOnItemSelectedListener(this);

            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.color_spinner_layout, wardrobeFamilies);
            arrayAdapter.setDropDownViewResource(R.layout.color_spinner_dropdown);
            spin.setAdapter(arrayAdapter);

            spin.setSelection(0);
            spin.setSelected(true);

            if (spin.isSelected()) {
                ApplicationContext.getInstance().setWardrobe(Long.valueOf(spin.getSelectedItem().toString().split("ID: ")[1]));

                GetUsersOfWardrobeRequest usersOfWardrobeRequest = null;

                try {
                    usersOfWardrobeRequest = new GetUsersOfWardrobeRequest(ApplicationContext.getInstance().getWardrobe().getwId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                usersOfWardrobeRequest.setCallback(() -> {
                    recyclerView = findViewById(R.id.familyRecycler);
                    List<UserModel> userModelList = new ArrayList<>();

                    ArrayList<String> names = new ArrayList<>();

                    Wardrobe w = ApplicationContext.getInstance().getWardrobe();
                    if (w != null)
                        names = (ArrayList<String>) w.getUsers().stream()
                                .map(
                                        p -> p.getId() == ApplicationContext.getInstance().getWardrobe().getAdminId()
                                                ? p.getNickname() + " [Admin]" : p.getNickname())
                                .collect(Collectors.toList());

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
                            Intent intent = new Intent(MainActivity.this, Spinner.class);
                            startActivity(intent);
                        }
                    });

                    shirts.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, Shirts.class);
                            startActivity(intent);
                        }
                    });

                    pants.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, Pants.class);
                            startActivity(intent);
                        }
                    });

                    skirts.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, Skirts.class);
                            startActivity(intent);
                        }
                    });

                    dresses.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, Dresses.class);
                            startActivity(intent);
                        }
                    });

                    shoes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, Shoes.class);
                            startActivity(intent);
                        }
                    });

                    jackets.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, Jackets.class);
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

                    for (String s: names) {
                        UserModel userModel = new UserModel(s);

                        userModelList.add(userModel);
                    }

                    usersAdapter = new RecyclerViewAdapter(userModelList);
                    recyclerView.setAdapter(usersAdapter);
                });

                usersOfWardrobeRequest.execute();
            }
        });
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
                Intent a = new Intent(this, AccountSettings.class);

                this.startActivity(a);
                return true;

            case R.id.wardset:
                Intent b = new Intent(this, WardrobeSettings.class);

                User user = ApplicationContext.getInstance().getUser();
                Wardrobe currWardrobe = ApplicationContext.getInstance().getWardrobe();

                if (currWardrobe.getAdminId() == user.getId() && !currWardrobe.getWardrobeType().equals("Personal"))
                    b = new Intent(this, AdminSettings.class);

                this.startActivity(b);
                return true;

            case R.id.logout:
                Intent c = new Intent(this, Login.class);

                this.startActivity(c);
                return true;

            default:
                return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        final Intent intent;

        ApplicationContext.getInstance().setWardrobe(Long.valueOf(adapterView.getSelectedItem().toString().split("ID: ")[1]));

        GetUsersOfWardrobeRequest req = null;
        try {
            req = new GetUsersOfWardrobeRequest(ApplicationContext.getInstance().getWardrobe().getwId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        req.setCallback(() -> {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

            recyclerView = findViewById(R.id.familyRecycler);

            List<UserModel> userModelList = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();

            Wardrobe w = ApplicationContext.getInstance().getWardrobe();
            if (w != null)
                names = (ArrayList<String>) w.getUsers().stream()
                        .map(
                                p -> p.getId() == ApplicationContext.getInstance().getWardrobe().getAdminId()
                                        ? p.getNickname() + " [Admin]" : p.getNickname())
                        .collect(Collectors.toList());

            RecyclerViewAdapter usersAdapter;

            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

            for (String s: names) {
                UserModel userModel = new UserModel(s);

                userModelList.add(userModel);
            }

            usersAdapter = new RecyclerViewAdapter(userModelList);
            recyclerView.setAdapter(usersAdapter);
        });

        req.execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
