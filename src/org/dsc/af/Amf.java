package org.dsc.af;

import org.dsc.af.model.Pais;

public class Amf {
   
   public static String KEY = "AMF";
   
   public static String phoneNumber;
   public static String imei;
   public static Pais pais;

   public static AmfFav favs;

   public static String ASIN = "";
   public static String URL = "";

   public static String status() {
      return "Amf status " + " | phoneNumber: " + phoneNumber + " | imei: " + imei + "\n | pais: " + pais + " | ASIN: "
            + ASIN + "\n | URL: " + URL;
   }
}
