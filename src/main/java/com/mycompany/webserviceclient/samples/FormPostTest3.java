package com.mycompany.webserviceclient.samples;

import com.mycompany.webserviceclient.util.HttpMultipartFormPostUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ariferol
 */
public class FormPostTest3 {

    public static void main(String[] args) {
        /* Bu örnekte multipart/form-data yöntemi ile hem headerdaki, hem formdaki attribute ve file upload işlemleri, form içindeki 
         * veriler, kesintiye uğramayacak şekilde sunucu tarafına post edilebilmektedir.
         */
        System.out.println("START**********************************************************************************************************************************************");

        try {
            String sampleUrl = "https://reqres.in/api/products/3";
            Map<String, String> headers = new HashMap<>();
            headers.put("sampleHeaderAttributeName", "sampleHeaderValue");
            HttpMultipartFormPostUtil multipart = new HttpMultipartFormPostUtil(sampleUrl, "UTF-8", headers);

            multipart.addFormField("param1", "param1");

            multipart.addFilePart("sendedFile", new File("sendedFileName.jpg"));

            String response = multipart.sendMultipartFormDataPost();
            System.out.println(response);

        } catch (Exception e) {
            Logger.getLogger(FormPostTest2.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("END**********************************************************************************************************************************************");

    }
}
