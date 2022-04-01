package com.example.ourwardrobe;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractRequest extends AsyncTask <Void, Void, Void> {
//    192.168.56.1:3000
//    192.168.0.119:3000
    private String socket;
    private String url;
    private String requestParam;
    private String requestMethod;
    protected Callback cb;

    public void setCallback(Callback cb) {
        this.cb = cb;
    }

    protected int responseCode;
    protected String responseMessage;

    public AbstractRequest(String socket, String url, String requestParam, String requestMethod) {
        this.socket = socket;
        this.url = url;
        this.requestParam = requestParam;
        this.requestMethod = requestMethod;
    }

    public AbstractRequest(String url, String requestParam, String requestMethod) {
        this.url = url;
        this.requestParam = requestParam;
        this.requestMethod = requestMethod;
        this.socket = "http://192.168.0.119:3000";
    }

    @Override
    protected Void doInBackground(Void... voids) {
        JSONObject request = new JSONObject();

        try {
            request.put("uNickname", ApplicationContext.getInstance().getUser().getNickname());
            request.put("uPassword", ApplicationContext.getInstance().getUser().getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        URL url = null;

        try {
            url = new URL(this.socket + "/" + this.url + requestParam);

            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Chrome");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setRequestMethod(requestMethod);
                connection.setDoOutput(true);
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);

                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(request.toString());
                wr.flush();
                wr.close();

                responseCode = connection.getResponseCode();
                responseMessage = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            } catch (IOException e) {
                Log.println(Log.ERROR, "error", e.getMessage());
            }

        } catch (MalformedURLException e) {
            Log.println(Log.ERROR, "error", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        afterRequestSend();
    }

    protected abstract void afterRequestSend();
}
