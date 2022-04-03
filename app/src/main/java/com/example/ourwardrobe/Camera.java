package com.example.ourwardrobe;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import outwardrobemodels.User;

public class Camera extends AppCompatActivity {
    private static Bitmap reduceBitmapSize(Bitmap bitmap, int MAX_SIZE) {
        double ratioSquare;
        int bitmapHeight, bitmapWidth;

        bitmapHeight = bitmap.getHeight();
        bitmapWidth = bitmap.getWidth();
        ratioSquare = (bitmapHeight * bitmapWidth) / MAX_SIZE;

        if (ratioSquare <= 1)
            return bitmap;
        double ratio = Math.sqrt(ratioSquare);

        int requiredHeight = (int) Math.round(bitmapHeight / ratio);
        int requiredWidth = (int) Math.round(bitmapWidth / ratio);

        return Bitmap.createScaledBitmap(bitmap, requiredWidth, requiredHeight, true);
    }


    ImageView img;
    Button bt;
    Button bt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Spinner spinner = (Spinner) findViewById(R.id.Outfits);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Outfits, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (parent.getItemAtPosition(position).equals("Choose")){

                }
                else{
                    Toast.makeText(parent.getContext(), "Selected " + item, Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        img = findViewById(R.id.image_view);
        bt = findViewById(R.id.open_Camera);
        bt2 = findViewById(R.id.Submit);

        if(ContextCompat.checkSelfPermission(Camera.this,
                Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(Camera.this, new String[]{
                    Manifest.permission.CAMERA}, 101);
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {

//            <item>Choose</item>
//        <item>Shirts</item>
//        <item>Dresses</item>
//        <item>Pants</item>
//        <item>Skirts</item>
//        <item>Shoes</item>
//        <item>Jackets</item>

            @Override
            public void onClick(View view) {
                CreateClotheRequest request = null;

                ByteArrayOutputStream streamBytes = new ByteArrayOutputStream();
                clotheImage.compress(Bitmap.CompressFormat.PNG, 100, streamBytes);

                String stream = Base64.encodeToString(streamBytes.toByteArray(), 1);

                try {
                    switch (spinner.getSelectedItem().toString()) {
                        case "Shirts":
                            request = new CreateClotheRequest(stream, "Shirt", ApplicationContext.getInstance().getWardrobe().getwId());
                            break;
                        case "Skirts":
                            request = new CreateClotheRequest(stream, "Skirt", ApplicationContext.getInstance().getWardrobe().getwId());
                            break;
                        case "Dresses":
                            request = new CreateClotheRequest(stream, "Dress", ApplicationContext.getInstance().getWardrobe().getwId());
                            break;
                        case "Shoes":
                            request = new CreateClotheRequest(stream, "Shoe", ApplicationContext.getInstance().getWardrobe().getwId());
                            break;
                        case "Pants":
                            request = new CreateClotheRequest(stream, "Pants", ApplicationContext.getInstance().getWardrobe().getwId());
                            break;
                        case "Jackets":
                            request = new CreateClotheRequest(stream, "Jacket", ApplicationContext.getInstance().getWardrobe().getwId());
                            break;
                        default:
                            Toast.makeText(Camera.this, "Choose a cloth type, please!", Toast.LENGTH_SHORT).show();
                            return;
                    }
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }

                request.setCallback(() -> {
                    Toast.makeText(Camera.this,"Successfully Submitted ", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Camera.this, MainActivity.class);
                    startActivity(intent);
                });

                request.execute();
            }
        });
    }

    private Bitmap clotheImage = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){
            clotheImage = (Bitmap) data.getExtras().get("data");
            Bitmap bitmap= clotheImage;
//            bitmap = reduceBitmapSize(bitmap, 4000);
            img.setImageBitmap(bitmap);
        }
    }


}
