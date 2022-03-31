package com.example.ourwardrobe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import outwardrobemodels.User;

public class wardrobesettings extends AppCompatActivity {

    private class LeaveWardrobeRequest extends AsyncTask<Void, Void, Void> {
        private int responseCode;
        private Long wId;

        public LeaveWardrobeRequest(Long wId) {
            this.wId = wId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (ApplicationContext.getInstance().getWardrobe().getWardrobeType().equals("Personal"))
                return null;

            JSONObject wardrobeRequest = new JSONObject();

            try {
                User user = ApplicationContext.getInstance().getUser();

                wardrobeRequest.put("uNickname", user.getNickname());
                wardrobeRequest.put("uPassword", user.getPassword());
                wardrobeRequest.put("wId", wId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            URL wardrobeUrl = null;

            try {
                wardrobeUrl = new URL("http://192.168.56.1:3000/users/register/delWardrobe");

                try {

                    HttpURLConnection connection = (HttpURLConnection) wardrobeUrl.openConnection();

                    connection.setRequestProperty("Accept", "*");
                    connection.setRequestProperty("User-Agent", "Chrome");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);
                    connection.setRequestMethod("DELETE");
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
            if (responseCode == 200) {
                Toast.makeText(wardrobesettings.this, "Family Left", Toast.LENGTH_SHORT).show();
                wardrobesettings.this.startActivity(new Intent(wardrobesettings.this, MainActivity.class));
            }
        }


    }

    private class CreateWardrobeRequest extends AsyncTask<Void, Void, Void> {
        private String wardrobeName;
        private int responseCode;

        public CreateWardrobeRequest(String wardrobeName) {
            this.wardrobeName = wardrobeName;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject wardrobeRequest = new JSONObject();

            try {
                User user = ApplicationContext.getInstance().getUser();

                wardrobeRequest.put("uNickname", user.getNickname());
                wardrobeRequest.put("uPassword", user.getPassword());
                wardrobeRequest.put("nickname", wardrobeName);
                wardrobeRequest.put("creationTime", null);
                wardrobeRequest.put("wardrobeType", "Shared");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            URL wardrobeUrl = null;

            try {
                wardrobeUrl = new URL("http://192.168.56.1:3000/wardrobes/register");

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
                Toast.makeText(wardrobesettings.this, "Family Created", Toast.LENGTH_SHORT).show();

                wardrobesettings.this.startActivity(new Intent(wardrobesettings.this, MainActivity.class));
            }
        }
    }

    private class JoinWardrobeRequest extends AsyncTask<Void, Void, Void>{
        private int responseCode;
        private int wId;

        public JoinWardrobeRequest(int wId) {
            this.wId = wId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject wardrobeRequest = new JSONObject();

            try {
                User user = ApplicationContext.getInstance().getUser();

                wardrobeRequest.put("uNickname", user.getNickname());
                wardrobeRequest.put("uPassword", user.getPassword());
                wardrobeRequest.put("wId", wId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            URL wardrobeUrl = null;

            try {
                wardrobeUrl = new URL("http://192.168.56.1:3000/users/register/addWardrobe");

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
                Toast.makeText(wardrobesettings.this, "Family Joined", Toast.LENGTH_SHORT).show();
                wardrobesettings.this.startActivity(new Intent(wardrobesettings.this, MainActivity.class));
            }
        }
    }

    EditText editCreateText, editJoinText;
    TextView textView, createFamily;
    Button leaveBtn, submitBtnJoin, submitBtnInit;
    protected Button deleteBtn;
    private ImageView backBtn;
    RecyclerView recyclerView;

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
        deleteBtn = (Button) findViewById(R.id.deleteFamily);

        deleteBtn.setVisibility(View.INVISIBLE);
        deleteBtn.setEnabled(false);

        recyclerView = findViewById(R.id.familyRecycler);
        List<userModel> userModelList = new ArrayList<>();

        String[] names = {"Jessica", "Amy", "Chantal", "Emily", "Christie", "Annabelle", "Joan"};
        RecyclerViewAdapter usersAdapter;

        submitBtnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.ERROR, "debug", editJoinText.getText().toString());
                JoinWardrobeRequest request = new JoinWardrobeRequest(Integer.valueOf(editJoinText.getText().toString()));
                request.execute();
            }
        });

        submitBtnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFamily.setText("ID: " + editCreateText.getText());

                CreateWardrobeRequest request = new CreateWardrobeRequest(editCreateText.getText().toString());
                request.execute();
            }
        });

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeaveWardrobeRequest request = new LeaveWardrobeRequest(ApplicationContext.getInstance().getWardrobe().getwId());
                request.execute();
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