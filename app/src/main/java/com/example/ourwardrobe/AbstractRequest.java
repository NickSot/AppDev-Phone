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
import java.util.Map;

public abstract class AbstractRequest extends AsyncTask <Void, Void, Void> {
//    192.168.56.1:3000
//    192.168.0.119:3000
    // 10.30.61.13

    protected String socket;
    private String url;
    private String requestParam;
    private String requestMethod;
    protected Callback cb;
    JSONObject request = new JSONObject();

    public void setCallback(Callback cb) {
        this.cb = cb;
    }

    protected int responseCode;
    protected String responseMessage;

    public AbstractRequest() {
        this.socket = "http://192.168.233.101:3000/";
    }

    public AbstractRequest(String socket, String url, String requestParam, String requestMethod) throws JSONException {
        this.socket = socket;
        this.url = url;
        this.requestParam = requestParam;
        this.requestMethod = requestMethod;

        if (!(request.has("uNickname") & request.has("uPassword"))){
            request.put("uNickname", ApplicationContext.getInstance().getUser().getNickname());
            request.put("uPassword", ApplicationContext.getInstance().getUser().getPassword());
        }
    }

    public AbstractRequest(String url, String requestParam, String requestMethod) throws JSONException {
        this();

        this.url = url;
        this.requestParam = requestParam;
        this.requestMethod = requestMethod;

        if (!(request.has("uNickname") & request.has("uPassword"))){
            request.put("uNickname", ApplicationContext.getInstance().getUser().getNickname());
            request.put("uPassword", ApplicationContext.getInstance().getUser().getPassword());
        }
    }

    public AbstractRequest(String url, String requestParam, String requestMethod, JSONObject request) throws JSONException {
        this();

        this.url = url;
        this.requestParam = requestParam;
        this.requestMethod = requestMethod;

        this.request = request;

        if (!(request.has("uNickname") & request.has("uPassword"))){
            request.put("uNickname", ApplicationContext.getInstance().getUser().getNickname());
            request.put("uPassword", ApplicationContext.getInstance().getUser().getPassword());
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        URL url = null;

        try {
            url = new URL(this.socket + this.url + requestParam);

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

        if (cb != null)
            cb.function();
    }

    protected abstract void afterRequestSend();
}
