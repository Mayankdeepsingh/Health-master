package com.mayank.healthcare;

/**
 * Created by Mayank on 03-04-2017.
 */
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mayank S Rawat on 4/1/2017.
 */
public class FetchData extends AsyncTask<String, String, String> {

    FetchDataListener listener;
    private String urlString;

    public void setArgs(String url, FetchDataListener listener) {
        urlString = url;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        listener.onPostExecute(result);
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection httpConnection = null;
        try {
            URL mUrl = new URL(urlString);
            httpConnection = (HttpURLConnection) mUrl.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setConnectTimeout(100000);
            httpConnection.setReadTimeout(100000);

            httpConnection.connect();

            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                return sb.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return null;
    }
}