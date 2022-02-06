/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webserviceclient.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ariferol
 */
public class MyUtil {

    public static String getResponseSendPostURL1(String postUrl, String param1) {
        String result = null;
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("param1", param1);
        result = sendPostURL1(postUrl, params);;
        return result;
    }

    public static String sendPostURL1(String postUrl, Map<String, Object> params) {
        StringBuilder result = new StringBuilder();
        URL url = null;
        try {
            url = new URL(postUrl);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MyUtil.class.getName()).log(Level.SEVERE, null, ex);
            return result.toString();
        }

        /* Form post parameters prepare; */
        StringBuilder postData = new StringBuilder();
        byte[] postDataBytes = null;
        try {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            postDataBytes = postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MyUtil.class.getName()).log(Level.SEVERE, null, ex);
            return result.toString();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            Logger.getLogger(MyUtil.class.getName()).log(Level.SEVERE, null, ex);
            return result.toString();
        }
        try {
            if (conn != null) {
                conn.setRequestMethod("POST");
            }
        } catch (ProtocolException ex) {
            Logger.getLogger(MyUtil.class.getName()).log(Level.SEVERE, null, ex);
            return result.toString();
        }
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        try {
            conn.getOutputStream().write(postDataBytes);
        } catch (IOException ex) {
            Logger.getLogger(MyUtil.class.getName()).log(Level.SEVERE, null, ex);
            return result.toString();
        }

        Reader reader = null;
        try {
            reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
        } catch (IOException ex) {
            Logger.getLogger(MyUtil.class.getName()).log(Level.SEVERE, null, ex);
            return result.toString();
        }
        if (reader != null) {
            try (BufferedReader brd = new BufferedReader(reader)) {
                String line;
                while ((line = brd.readLine()) != null) {
                    result.append(line);
                }
            } catch (IOException ex) {
                Logger.getLogger(MyUtil.class.getName()).log(Level.SEVERE, null, ex);
                return result.toString();
            }
        }
        conn.disconnect();
        return result.toString();
    }

}
