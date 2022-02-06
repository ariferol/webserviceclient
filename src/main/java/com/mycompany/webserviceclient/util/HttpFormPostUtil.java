package com.mycompany.webserviceclient.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpFormPostUtil {

    private HttpURLConnection httpConn;
    private Map<String, Object> formParams;
    private String charset;

    public HttpFormPostUtil(String requestURL, String charset, Map<String, String> headers, Map<String, Object> formParams, String contentType) throws IOException {
        this.charset = charset;
        if (formParams == null) {
            this.formParams = new HashMap<>();
        } else {
            this.formParams = formParams;
        }
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        httpConn.setRequestProperty("Content-Type", isNotEmpty(contentType) ? contentType : "application/x-www-form-urlencoded");
        if (headers != null && headers.size() > 0) {
            Iterator<String> it = headers.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = headers.get(key);
                httpConn.setRequestProperty(key, value);
            }
        }
    }

    public HttpFormPostUtil(String requestURL, String charset, Map<String, String> headers, Map<String, Object> formParams) throws IOException {
        this(requestURL, charset, headers, formParams, null);
    }

    public HttpFormPostUtil(String requestURL, String charset, Map<String, String> headers) throws IOException {
        this(requestURL, charset, headers, null);
    }

    public HttpFormPostUtil(String requestURL, String charset) throws IOException {
        this(requestURL, charset, null, null);
    }

    public void addFormField(String name, Object value) {
        formParams.put(name, value);
    }

    public void addHeader(String key, String value) {
        httpConn.setRequestProperty(key, value);
    }

    private byte[] getParamsByte(Map<String, Object> params) {
        byte[] result = null;
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(this.encodeParam(param.getKey()));
            postData.append('=');
            postData.append(this.encodeParam(String.valueOf(param.getValue())));
        }
        try {
            result = postData.toString().getBytes(getCharSet());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Logger.getLogger(HttpFormPostUtil.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    private String encodeParam(String data) {
        String result = "";
        try {
            result = URLEncoder.encode(data, getCharSet());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Logger.getLogger(HttpFormPostUtil.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public String sendFormPost() throws IOException {
        String response = "";
        byte[] postDataBytes = this.getParamsByte(formParams);
        httpConn.getOutputStream().write(postDataBytes);
        int status = httpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = httpConn.getInputStream().read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            response = result.toString(this.charset);
            httpConn.disconnect();
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }
        return response;
    }

    private boolean isNotEmpty(String charset) {
        return charset != null && "" != charset;
    }

    private String getCharSet() {
        return isNotEmpty(this.charset) ? this.charset : "UTF-8";
    }
}
