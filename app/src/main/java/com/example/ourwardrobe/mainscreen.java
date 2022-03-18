package com.example.ourwardrobe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ourwardrobe.databinding.FabBinding;
import com.google.android.material.snackbar.Snackbar;

public class mainscreen extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private FabBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private class Request extends AsyncTask<Void, Void, Void> {

        Request() {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    public void openCategory()  {
        Intent intent = new Intent(this,Camera.class);
    }
}