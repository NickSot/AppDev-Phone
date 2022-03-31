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
    private class CreateClotheRequest extends AsyncTask<Void, Void, Void> {
        private int responseCode;
        private Bitmap image;
        private String clotheType;
        private Long originalWardrobeId;

        public CreateClotheRequest(Bitmap image, String clotheType, Long originalWardrobeId) {
            this.image = image;
            this.clotheType = clotheType;
            this.originalWardrobeId = originalWardrobeId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject wardrobeRequest = new JSONObject();

            try {
                User user = ApplicationContext.getInstance().getUser();

                wardrobeRequest.put("uNickname", user.getNickname());
                wardrobeRequest.put("uPassword", user.getPassword());
                wardrobeRequest.put("originalWardrobeId", originalWardrobeId);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                wardrobeRequest.put("image", Base64.encodeToString(stream.toByteArray(), 1));
                wardrobeRequest.put("clothType", clotheType);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            URL wardrobeUrl = null;

            try {
                wardrobeUrl = new URL("http://192.168.56.1:3000/clothes/register");

                try {

                    HttpURLConnection connection = (HttpURLConnection) wardrobeUrl.openConnection();

                    connection.setRequestProperty("Accept", "*");
                    connection.setRequestProperty("User-Agent", "Chrome");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(15000);
                    connection.setReadTimeout(15000);

                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes(wardrobeRequest.toString());
                    wr.flush();
                    wr.close();

                    responseCode = connection.getResponseCode();

                } catch (IOException e) {
//                    Toast.makeText(Register.this, e.getMessage() ,Toast.LENGTH_SHORT).show();
                }
            } catch (MalformedURLException e) {
//                Toast.makeText(Register.this, e.getMessage() ,Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            if (responseCode == 201) {
                Toast.makeText(Camera.this,"Successfully Submitted ", Toast.LENGTH_SHORT).show();
            }
            else{
                Log.println(Log.ERROR, "important!", String.valueOf(responseCode));
            }
        }
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

                switch (spinner.getSelectedItem().toString()) {
                    case "Shirts":
                        request = new CreateClotheRequest(clotheImage, "Shirt", ApplicationContext.getInstance().getWardrobe().getwId());
                        break;
                    case "Skirts":
                        request = new CreateClotheRequest(clotheImage, "Skirt", ApplicationContext.getInstance().getWardrobe().getwId());
                        break;
                    case "Dresses":
                        request = new CreateClotheRequest(clotheImage, "Dress", ApplicationContext.getInstance().getWardrobe().getwId());
                        break;
                    case "Shoes":
                        request = new CreateClotheRequest(clotheImage, "Shoe", ApplicationContext.getInstance().getWardrobe().getwId());
                        break;
                    case "Pants":
                        request = new CreateClotheRequest(clotheImage, "Pants", ApplicationContext.getInstance().getWardrobe().getwId());
                        break;
                    case "Jackets":
                        request = new CreateClotheRequest(clotheImage, "Jacket", ApplicationContext.getInstance().getWardrobe().getwId());
                        break;
                    default:
                        Toast.makeText(Camera.this, "Choose a cloth type, please!", Toast.LENGTH_SHORT).show();
                        return;
                }

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

            img.setImageBitmap(bitmap);
        }
    }


}
