package de.perfectban.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class HttpGetRequest
{
    private final HashMap<String, Object> headers = new HashMap<>();
    private final String url;
    private String result;

    public HttpGetRequest(String url) {
        this.url = url;

        this.headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        this.headers.put("Cache-Control", "no-cache");
        this.headers.put("Accept", "*/*");
    }

    public HttpGetRequest send() throws IOException {
        URL url = new URL(this.url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        for (String key : headers.keySet()) {
            httpURLConnection.setRequestProperty(key, headers.get(key).toString());
        }

        InputStream inputStream;
        if (httpURLConnection.getResponseCode() >= 400) {
            inputStream = httpURLConnection.getErrorStream();
        } else {
            inputStream = httpURLConnection.getInputStream();
        }

        if (inputStream == null) {
            this.result = "";
            return this;
        }

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        this.result = result.toString();

        return this;
    }

    public String getAsString() {
        if (this.result == null) {
            throw new NullPointerException("No result received (yet)");
        }

        return this.result;
    }
}
