package org.dsc.af;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dsc.af.model.Pais;
import org.dsc.sdk.DSC;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class AmfQR {
   private static String descSearch = "0-99";
   private static String artSearch = "cámara reflex";

   private static Map<String, String> urlParams;
   static {
      urlParams = new HashMap<String, String>();
      urlParams.put("ie", "UTF8");
      urlParams.put("tag", "---");
      urlParams.put("camp", "3626");
      urlParams.put("creative", "24822");
      urlParams.put("linkCode", "as2");
      urlParams.put("creativeASIN", "---");
      urlParams.put("force-full-site", "1");
      urlParams.put("ref_", "aw_bottom_links");
      urlParams.put("ref-refURL", "http%3A%2F%2Famafinder.blogspot.com.es%2F");
   };

   /**
    * @param descuento
    * @param articulo
    * @return
    */
   public static String formatSearchUrl(final String descuento, final String articulo, final String pais) {
      if (descuento != null && !descuento.equalsIgnoreCase("")) {
         int desc = Integer.parseInt(descuento);
         if (0 <= desc && desc <= 99) {
            descSearch = desc + "-99";
         }
      }
      if (articulo != null && !articulo.equalsIgnoreCase("")) {
         artSearch = articulo;
      }
      Amf.URL = "http://www.amazon." + pais + "/gp/search/?ie=UTF8&pct-off=" + descSearch + "&field-keywords="
            + artSearch;
      DSC.A().log("AmfQR::formatUrl().return " + Amf.URL);
      return Amf.URL;
   }

   /**
    * Contructor de uri para el pais y asin indicado
    * @param pais pais para el que se construye la url
    * @param asin amazon id de objeto a buscar
    * @return uri para lanzar a amazon
    * @throws MalformedURLException
    */
   public static URL amazonBuyUri(final Pais pais, final String asin) throws MalformedURLException {
      // actualizacion de asin en busqueda
      urlParams.put("creativeASIN", asin);
      urlParams.put("tag", pais.tag());
      String url = "http://www.amazon." + pais.code() + "/gp/product/" + asin + "/ref=as_li_ss_tl?";
      int i = 0;
      for (String key : urlParams.keySet()) {
         if (i > 0)
            url += "&";
         url += key + "=" + urlParams.get(key);
         i++;
      }
      Amf.URL = url;
      DSC.A().log("AmfQR::amazonBuyUri(" + pais.code() + ", " + Amf.ASIN + ").return " + Amf.URL);
      return new URL(Amf.URL);
   }
}
