package com.example.ourwardrobe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import outwardrobemodels.User;
import outwardrobemodels.Wardrobe;

public class wardrobesettings extends AppCompatActivity {

    private class LeaveWardrobeRequest extends AbstractRequest {
        public LeaveWardrobeRequest(Long wId) throws JSONException {
            super("users/register/delWardrobe", "", "DELETE", new JSONObject().put("wId", wId));
        }


        @Override
        protected void afterRequestSend() {
            if (responseCode == 200) {
                Toast.makeText(wardrobesettings.this, "Family Left", Toast.LENGTH_SHORT).show();
                wardrobesettings.this.startActivity(new Intent(wardrobesettings.this, MainActivity.class));
            }
        }
    }

    private class CreateWardrobeRequest extends AbstractRequest {
        public CreateWardrobeRequest(String wardrobeName) throws JSONException {
            super("wardrobes/register", "", "POST",
                    new JSONObject().put("nickname", wardrobeName).put("creationTime", null)
                        .put("wardrobeType", "Shared"));
        }

        @Override
        protected void afterRequestSend() {
            if (responseCode == 201) {
                Toast.makeText(wardrobesettings.this, "Family Created", Toast.LENGTH_SHORT).show();
                
                wardrobesettings.this.startActivity(new Intent(wardrobesettings.this, MainActivity.class));
            }
        }
    }

    private class JoinWardrobeRequest extends AbstractRequest{
        public JoinWardrobeRequest(int wId) throws JSONException {
            super("users/register/addWardrobe", "",
                    "POST", new JSONObject().put("wId", wId));
        }

        @Override
        protected void afterRequestSend() {
            if (responseCode == 200) {
                Toast.makeText(wardrobesettings.this, "Family Joined", Toast.LENGTH_SHORT).show();
                wardrobesettings.this.startActivity(new Intent(wardrobesettings.this, MainActivity.class));
            }
        }
    }

    EditText editCreateText, editJoinText, editLeaveText;
    TextView textView, createFamily, textLeave;
    Button leaveBtn, submitBtnJoin, submitBtnInit, submitBtnLeave;
    protected Button deleteBtn;
    private ImageView backBtn;
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsettings);

        textView = (TextView) findViewById(R.id.textJoin);
        editCreateText = (EditText) findViewById(R.id.createWard);
        editJoinText = (EditText) findViewById(R.id.joinWard);
        leaveBtn = (Button) findViewById(R.id.leaveFamily);
        submitBtnJoin = (Button) findViewById(R.id.submitBtnJoin);
        submitBtnInit = (Button) findViewById(R.id.submitBtnInit);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        createFamily = (TextView) findViewById(R.id.textCreate);

        textLeave = (TextView) findViewById(R.id.textLeave);
        submitBtnLeave = (Button) findViewById(R.id.submitBtnLeave);
        editLeaveText = (EditText) findViewById(R.id.leaveWard);

        deleteBtn = (Button) findViewById(R.id.deleteFamily);
        deleteBtn.setVisibility(View.INVISIBLE);
        deleteBtn.setEnabled(false);

        recyclerView = findViewById(R.id.familyRecycler);
        List<userModel> userModelList = new ArrayList<>();

        GetUsersOfWardrobeRequest req = null;

        try {
            req = new GetUsersOfWardrobeRequest(ApplicationContext.getInstance().getWardrobe().getwId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        req.execute(() -> {
            ArrayList<String> names = new ArrayList<>();

            Wardrobe w = ApplicationContext.getInstance().getWardrobe();
            if (w != null)
                names = (ArrayList<String>) w.getUsers().stream()
                        .map(
                                p -> p.getId() == ApplicationContext.getInstance().getWardrobe().getAdminId()
                                        ? p.getNickname() + " [Admin]" : p.getNickname())
                        .collect(Collectors.toList());

            RecyclerViewAdapter usersAdapter;

            submitBtnJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editJoinText.getText().toString().equals("")) {
                        Toast.makeText(wardrobesettings.this, "Please insert a ID of the wardrobe family to join!", Toast.LENGTH_SHORT);
                        return;
                    }

                    JoinWardrobeRequest request = null;

                    try {
                        request = new JoinWardrobeRequest(Integer.valueOf(editJoinText.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    request.execute();
                }
            });

            submitBtnInit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editCreateText.getText().toString().equals("")) {
                        Toast.makeText(wardrobesettings.this, "Please insert a nickname of the wardrobe!", Toast.LENGTH_SHORT);
                        return;
                    }

                    createFamily.setText("ID: " + editCreateText.getText());

                    CreateWardrobeRequest request = null;
                    try {
                        request = new CreateWardrobeRequest(editCreateText.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    request.execute();
                }
            });

            leaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LeaveWardrobeRequest request = null;

                    try {
                        request = new LeaveWardrobeRequest(ApplicationContext.getInstance().getWardrobe().getwId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    request.setCallback(() -> {
                        startActivity(new Intent(wardrobesettings.this, MainActivity.class));
                    });

                    request.execute();
                }
            });

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(wardrobesettings.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            submitBtnLeave.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (editLeaveText.getText().toString().equals("")) {
                        Toast.makeText(wardrobesettings.this, "Please insert an ID of the family you want to leave!", Toast.LENGTH_SHORT);
                        return;
                    }

                    LeaveWardrobeRequest request = null;

                    try {
                        request = new LeaveWardrobeRequest(Long.valueOf(editLeaveText.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    request.setCallback(() -> {
                        Intent intent = new Intent(wardrobesettings.this, MainActivity.class );
                        startActivity(intent);
                    });

                    request.execute();
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
        });
    }
}