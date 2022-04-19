package com.example.ourwardrobe;

import android.app.Activity;
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

import androidx.annotation.RequiresApi;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.stream.Collectors;

import ourwardrobemodels.Wardrobe;

public abstract class AbstractClothActivity extends Activity {
    private String clothType;

    public AbstractClothActivity(String clothType) {
        this.clothType = clothType;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GetClothesRequest request = null;

        try {
            request = new GetClothesRequest(ApplicationContext.getInstance().getWardrobe().getwId(), clothType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        request.setCallback(() -> {
            GridView gridView = findViewById(R.id.shirts);

            if (this.clothType == "Shirt"){
                setContentView(R.layout.shirts);
                gridView = findViewById(R.id.gridshirts);
            }
            else if (this.clothType == "Pants") {
                setContentView(R.layout.activity_pants);
                gridView = findViewById(R.id.gridpants);
            }
            else if (this.clothType == "Skirt") {
                setContentView(R.layout.activity_skirts);
                gridView = findViewById(R.id.gridskirts);
            }
            else if (this.clothType == "Dress") {
                setContentView(R.layout.activity_dresses);
                gridView = findViewById(R.id.griddresses);
            }
            else if (this.clothType == "Shoe") {
                setContentView(R.layout.activity_shoes);
                gridView = findViewById(R.id.gridshoes);
            }
            else {
                setContentView(R.layout.activity_jackets);
                gridView = findViewById(R.id.gridjackets);
            }


            ArrayList<Bitmap> mImageIds = (ArrayList<Bitmap>) ApplicationContext.getInstance()
                    .getWardrobe().getClothes()
                    .stream().map(p -> p.getImage()).collect(Collectors.toList());

            gridView.setAdapter(new ImageAdapter(mImageIds,this, 525, 650));

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ShowDialogBox(Bitmap item_pos){
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog);

        TextView userID = dialog.findViewById(R.id.userID);
        ImageView image = dialog.findViewById(R.id.img);
        ToggleButton btnAdd = dialog.findViewById(R.id.btnadd);
        Button close = dialog.findViewById(R.id.close);

        Wardrobe w = ApplicationContext.getInstance().getWardrobe();
        String ogUser = w.getClothes().stream().filter(p -> p.getImage().equals(item_pos)).map(p -> p.getOriginalUser()).collect(Collectors.toList()).get(0);

        String title = String.format("Belongs to user: %s", ogUser);

        userID.setText(title);

        image.setImageBitmap(item_pos);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        for (Bitmap img: ApplicationContext.getInstance().getOutfitImages()) {
            if (item_pos.sameAs(img)) {
                btnAdd.setChecked(true);
                break;
            }
            else
                btnAdd.setChecked(false);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnAdd.isChecked()) {
                    ApplicationContext.getInstance().addImageToOutfit(item_pos);
                    Toast.makeText(AbstractClothActivity.this, "Cloth is added to Outfit Creator", Toast.LENGTH_SHORT).show();
                } else {
                    ApplicationContext.getInstance().removeImageFromOutfit(item_pos);
                    Toast.makeText(AbstractClothActivity.this, "Cloth is removed from Outfit Creator", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}
