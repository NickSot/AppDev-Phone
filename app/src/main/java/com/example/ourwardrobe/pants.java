package com.example.ourwardrobe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Build;
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
import java.util.stream.Collectors;

public class pants extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GetClothesRequest request = new GetClothesRequest(ApplicationContext.getInstance().getWardrobe().getwId(), "Pants");

        request.setCallback(() -> {
            setContentView(R.layout.shirts);

            ArrayList<Bitmap> mImageIds = (ArrayList<Bitmap>) ApplicationContext.getInstance()
                    .getWardrobe().getClothes()
                    .stream().map(p -> p.getImage()).collect(Collectors.toList());

            GridView gridView = findViewById(R.id.gridshirts);
            gridView.setAdapter(new ImageAdapter(mImageIds,this));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Bitmap item_pos = mImageIds.get(position);

                    ShowDialogBox(item_pos);
                }
            });
        });

        request.execute();
    }

    public void ShowDialogBox(Bitmap item_pos){
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog);

        TextView userID = dialog.findViewById(R.id.userID);
        ImageView image = dialog.findViewById(R.id.img);
        ToggleButton btnAdd = dialog.findViewById(R.id.btnadd);
        Button close = dialog.findViewById(R.id.close);

        String title = "empty for now";

        int index = title.indexOf("/");
        String name = title.substring(index+1,title.length());
        userID.setText(name);

        image.setImageBitmap(item_pos);

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
                    Toast.makeText(pants.this, "Cloth is added to Outfit Creator", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(pants.this, "Cloth is removed from Outfit Creator", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}