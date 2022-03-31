package com.example.ourwardrobe;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import outwardrobemodels.User;
import outwardrobemodels.Wardrobe;

public class Gallery extends AppCompatActivity {
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
                image.compress(Bitmap.CompressFormat.PNG, 1, stream);
                String compressedImage = Base64.encodeToString(stream.toByteArray(), 1);
                wardrobeRequest.put("image", compressedImage);
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
                Toast.makeText(Gallery.this,"Successfully Submitted ", Toast.LENGTH_SHORT).show();
            }
            else{
                Log.println(Log.ERROR, "important!", String.valueOf(responseCode));
            }
        }
    }

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


    private Button btn;
    int PICK_IMAGE_MULTIPLE = 1;
    Bitmap clotheImage;
    List<Bitmap> imagesEncodedList;
    private GridView gvGallery;
    private gv_item galleryAdapter;
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    Uri mImageUri;

    private static final String FRAGMENT_NAME = "imageFragment";
    Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Spinner spinner = (Spinner) findViewById(R.id.OutfitCattegory);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Outfits, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        btn = findViewById(R.id.btn);
        gvGallery = (GridView) findViewById(R.id.gv);

        bt2 = findViewById(R.id.SubmitGallery);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
            }
        });
        if (savedInstanceState != null) {
            mArrayUri = (ArrayList<Uri>) savedInstanceState.getSerializable("array");
        }

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Work on submitting clothe to the wardrobe

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
                        Wardrobe w = ApplicationContext.getInstance().getWardrobe();
                        request = new CreateClotheRequest(clotheImage, "Shoe", ApplicationContext.getInstance().getWardrobe().getwId());
                        break;
                    case "Pants":
                        request = new CreateClotheRequest(clotheImage, "Pants", ApplicationContext.getInstance().getWardrobe().getwId());
                        break;
                    case "Jackets":
                        request = new CreateClotheRequest(clotheImage, "Jacket", ApplicationContext.getInstance().getWardrobe().getwId());
                        break;
                    default:
                        Toast.makeText(Gallery.this, "Choose a cloth type, please!", Toast.LENGTH_SHORT).show();
                        return;
                }

                request.execute();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose")){

                }
                else{
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<>();

                if (data.getData() != null) {

                    mImageUri = data.getData();
                    clotheImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                    clotheImage = reduceBitmapSize(clotheImage, 16000);
                    mArrayUri.add(mImageUri);
                    galleryAdapter = new gv_item(getApplicationContext(), mArrayUri);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();

                            clotheImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            clotheImage = reduceBitmapSize(clotheImage, 16000);
                            imagesEncodedList.add(clotheImage);

                            galleryAdapter = new gv_item(getApplicationContext(), mArrayUri);
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
