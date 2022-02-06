/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webserviceclient.samples;

import com.mycompany.webserviceclient.util.GsonCalendarSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.webserviceclient.util.GsonDateSerializer;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ariferol
 */
public class FormPostTest {

    public static void main(String[] args) {
        System.out.println("START**********************************************************************************************************************************************");
        String sampleUrl = "https://reqres.in/api/products/3";
        String resultStr = com.mycompany.webserviceclient.util.MyUtil.getResponseSendPostURL1(sampleUrl, "param1");

        /*Burada dönen rest sonucu deserialize ediliyor*/
        if (resultStr != null && "" != resultStr) {
            Gson gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(Calendar.class, new GsonCalendarSerializer())
                    .registerTypeHierarchyAdapter(Date.class, new GsonDateSerializer())
                    .setDateFormat("dd.MM.yyyy HH:mm:ss.SSS").create();
            Object result = gson.fromJson(resultStr, Object.class);
            System.out.println("END**********************************************************************************************************************************************");

        }
    }
}
