package com.example.ourwardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class wardrobesettings extends AppCompatActivity {

    EditText editCreateText, editJoinText;
    TextView textView, createFamily;
    Button leaveBtn, submitBtnJoin, submitBtnInit;
    private ImageView backBtn;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobesettings);

        textView = (TextView) findViewById(R.id.textJoin);
        editCreateText = (EditText) findViewById(R.id.createWard);
        editJoinText = (EditText) findViewById(R.id.joinWard);
        leaveBtn = (Button) findViewById(R.id.leaveFamily);
        submitBtnJoin = (Button) findViewById(R.id.submitBtnJoin);
        submitBtnInit = (Button) findViewById(R.id.submitBtnInit);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        createFamily = (TextView) findViewById(R.id.textCreate);

        recyclerView = findViewById(R.id.familyRecycler);
        List<userModel> userModelList = new ArrayList<>();

        String[] names = {"Jessica", "Amy", "Chantal", "Emily", "Christie", "Annabelle", "Joan"};
        RecyclerViewAdapter usersAdapter;

        submitBtnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(wardrobesettings.this, "Family Joined", Toast.LENGTH_SHORT).show();
            }
        });

        submitBtnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(wardrobesettings.this, "Family Created", Toast.LENGTH_SHORT).show();
                createFamily.setText("ID: " + editCreateText.getText());
            }
        });

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(wardrobesettings.this, "Family Left", Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(wardrobesettings.this, MainActivity.class );
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        for (String s:names) {
            userModel userModel = new userModel(s);

            userModelList.add(userModel);
        }

        usersAdapter = new RecyclerViewAdapter(userModelList);
        recyclerView.setAdapter(usersAdapter);
    }
}