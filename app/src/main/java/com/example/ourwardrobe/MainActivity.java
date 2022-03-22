package com.example.ourwardrobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private CardView shirts, pants, dresses, shoes, jackets, skirts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoryinterface);

        shirts=findViewById(R.id.shirts);
        pants=findViewById(R.id.pants);
        dresses=findViewById(R.id.dresses);
        shoes=findViewById(R.id.shoes);
        jackets=findViewById(R.id.jackets);
        skirts=findViewById(R.id.skirts);

        shirts.setOnClickListener(this);
        pants.setOnClickListener(this);
        dresses.setOnClickListener(this);
        shoes.setOnClickListener(this);
        jackets.setOnClickListener(this);
        skirts.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.shirts:i=new Intent(this,shirts.class);startActivity(i);
            break;
        }
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