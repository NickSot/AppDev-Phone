package com.example.ourwardrobe;

import androidx.fragment.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;

public class ImageRetainingFragment extends Fragment {
    private Bitmap selectedImage;
    @Override    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }
    public void setImage(Bitmap selectedImage) {
        this.selectedImage = selectedImage;
    }
    public Bitmap getImage() {
        return this.selectedImage;
    }

}
