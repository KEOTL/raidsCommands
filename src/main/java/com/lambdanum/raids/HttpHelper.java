package com.lambdanum.raids;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {

    private Gson gson;

    public HttpHelper(Gson gson) {
        this.gson = gson;
    }

    public String get(String url) {
        return doHttp(url, "GET");
    }

    public String post(String url) {
        return doHttp(url, "POST");
    }

    private String doHttp(String urlToRead, String method) {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T get(String url, Class<T> type) {
        return gson.fromJson(get(url), type);
    }

    public <T> T post(String url, Class<T> type) {
        return gson.fromJson(post(url), type);
    }

}
