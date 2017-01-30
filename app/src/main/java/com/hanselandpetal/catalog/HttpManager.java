package com.hanselandpetal.catalog;

import android.net.http.AndroidHttpClient;
import android.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by b0kn0y on 1/29/2017.
 */

public class HttpManager {

    public static String getData(String uri){

        BufferedReader reader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;

            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if(reader != null) {
                try {
                    reader.close();
                }catch (IOException e ) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }

    /**
     * @description :: This block of code accepts username and password
     * to be sent to the aunthenticated web server.
     * @param uri
     * @param userName
     * @param password
     * @return
     */
    public static String getData(String uri, String userName, String password) {

        BufferedReader reader = null;
        byte[] loginBytes = (userName + ":" + password).getBytes();
        StringBuilder loginBuilder = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));

        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.addRequestProperty("Authorization", loginBuilder.toString());

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;

            while((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if(reader != null) {
                try {
                    reader.close();
                }catch (IOException e ) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }
}
