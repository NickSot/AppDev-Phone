package com.example.ourwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;

public class shoes extends AppCompatActivity {

    ArrayList<Integer> mImageIds = new ArrayList<>(Arrays.asList(
            R.drawable.shirt1, R.drawable.shirt2, R.drawable.shirt3, R.drawable.shirt4
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes);

        GridView gridView = findViewById(R.id.gridshoes);
        gridView.setAdapter(new ImageAdapter(mImageIds,this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int item_pos = mImageIds.get(position);

                ShowDialogBox(item_pos);
            }
        });
    }

    public void ShowDialogBox(int item_pos){
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog);

        TextView userID = dialog.findViewById(R.id.userID);
        ImageView image = dialog.findViewById(R.id.img);
        ToggleButton btnAdd = dialog.findViewById(R.id.btnadd);
        Button close = dialog.findViewById(R.id.close);

        String title = getResources().getResourceName(item_pos);

        int index = title.indexOf("/");
        String name = title.substring(index+1,title.length());
        userID.setText(name);

        image.setImageResource(item_pos);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnAdd.isChecked()) {
                    Toast.makeText(shoes.this, "Cloth is added to Outfit Creator", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(shoes.this, "Cloth is removed from Outfit Creator", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}