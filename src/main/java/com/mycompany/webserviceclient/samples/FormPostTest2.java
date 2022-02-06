package com.mycompany.webserviceclient.samples;

import com.mycompany.webserviceclient.util.MyUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ariferol
 */
public class FormPostTest2 {

    public static void main(String[] args) {
        // Bu örnekte urlencoded form post yapılmaktadır.
        System.out.println("START**********************************************************************************************************************************************");

        try {
            String sampleUrl = "https://reqres.in/api/products/3";
            
            Map<String, String> headers = new HashMap<>();
            headers.put("sampleHeaderAttributeName", "sampleHeaderValue");
            /*
             * "application/x-www-form-urlencoded, multipart/form-data, text/plain" olmak üzere 3 çeşit form content-type ı vardır.
             * İki tür form content type ını destekler. "multipart/form-data" örneği ayrıca FormPostTest3 üncü örnekte yapıldı.      
            */
            String contentType = "application/x-www-form-urlencoded";
//            String contentType = "text/plain.";
            com.mycompany.webserviceclient.util.HttpFormPostUtil httpPostFormUtil
                    = new com.mycompany.webserviceclient.util.HttpFormPostUtil(sampleUrl, "UTF-8", headers, null, contentType);

            httpPostFormUtil.addFormField("param1", "param1");

            String response = httpPostFormUtil.sendFormPost();

            System.out.println(response);
        } catch (Exception e) {
            Logger.getLogger(FormPostTest2.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("END**********************************************************************************************************************************************");
    }
}
