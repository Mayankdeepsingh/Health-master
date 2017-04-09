package com.mayank.healthcare;

/**
 * Created by Mayank on 03-04-2017.
 */
public interface FetchDataListener {

    public void onPostExecute(String result);
    public void onPreExecute();

}
