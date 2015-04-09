package org.dsc.af.az;

import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dsc.af.AmfLoc;
import org.dsc.af.model.Pais;
import org.dsc.sdk.DSC;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class AzWS {
   public static final double PRECIO_ND = 0xFFFFFFFF;

   /**
    * @param url
    * @return
    */
   public static String asin(String url) {
      String asin = "recargar";
      try {
         if (url.contains(AmfLoc.UK.toString())) {
            asin = url.substring(32, url.length());
            asin = asin.substring(0, asin.indexOf("/"));
         } else {
            asin = url.substring(29, url.length());
            asin = asin.substring(0, asin.indexOf("/"));
         }
         DSC.A().log("AmfQR::asin().return " + asin);
      } catch (Exception e) {
         DSC.A().log("AmfQR::asin().error " + e.getMessage());
         try {
            if (url.contains("cache") && url.contains(AmfLoc.UK.toString())) {
               asin = url.substring(32, url.length());
               asin = asin.substring(0, asin.indexOf("?"));
            } else {
               asin = url.substring(29, url.length());
               asin = asin.substring(0, asin.indexOf("?"));
            }
            DSC.A().log("AmfQR::asin().return en Exception " + asin);
         } catch (Exception d) {
            DSC.A().error("AmfQR::asin() " + d.getMessage(), d);
            asin = "recargar";
         }
      }
      if (asin.contains("/") || asin.contains("=") || asin.contains("_")) {
         asin = "recargar";
      }
      return asin;
   }

   /**
    * @param pais
    * @param asin
    * @return
    * @throws Exception
    */
   public static double requestAmazonPrecio(final Pais pais, final String asin) throws Exception {
      double precio = PRECIO_ND;
      try {
         String AWS_ACCESS_KEY_ID = "AKIAJCQUYDWQX4K2IB4A";
         String AWS_SECRET_KEY = "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";
         String ENDPOINT = "webservices.amazon." + pais.code();
         String ITEM_ID = asin;
         SRHelp helper = null;
         try {
            helper = SRHelp.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
         } catch (Exception e) {
            e.printStackTrace();
         }

         String requestUrl = null;

         Map<String, String> params = new HashMap<String, String>();
         params.put("Service", "AWSECommerceService");
         params.put("Version", "2011-08-01");
         params.put("Operation", "ItemLookup");
         params.put("ItemId", ITEM_ID);
         params.put("ResponseGroup", "OfferFull");
         params.put("AssociateTag", pais.tag());

         requestUrl = helper.sign(params);

         DSC.A().log("AmfWS::requestAmazonPrecio() Signed requestUrl is \"" + requestUrl + "\"");
         precio = fetchPrice(requestUrl);
         DSC.A().log("AmfWS::requestAmazonPrecio() Price precioFinal is \"" + precio + "\"");
         if (AmfLoc.UK.equals(pais)) {
            precio = AzWS.pounds2euros(precio);
         }
      } catch (Exception e) {
         DSC.A().log("recuperarPrecio request Exception = " + e);
      }
      return precio;
   }

   private static double fetchPrice(String requestUrl) {
      double precio = PRECIO_ND;
      try {
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document doc = db.parse(requestUrl);
         Node priceNode = doc.getElementsByTagName("FormattedPrice").item(0);
         String precioNode = priceNode.getTextContent();
         DSC.A().log("AmfWS::fetchPrice() precioNode=" + precioNode);
         precio = Double.parseDouble(precioNode);
      } catch (Exception e) {
         DSC.A().log("AmfWS::fetchPrice() ERROR " + e);
      }
      return precio;
   }

   /**
    * Coniverte precioPounds a precioEuros
    * @param precioPounds
    * @return
    */
   public static double pounds2euros(final double precioPounds) {
      double precioEuros = PRECIO_ND;
      try {
         if (precioPounds > 0) {
            URL url = new URL("http://www.ecb.int/stats/eurofxref/eurofxref-daily.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Cube");
            DSC.A().log("AmfWS::pounds2euros() nodeList.getlength(): " + nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
               Node node = nodeList.item(i);
               if (node instanceof Element) {
                  Element element = (Element) node;
                  String currency = element.getAttribute("currency");
                  String rate = element.getAttribute("rate");
                  if (currency.equalsIgnoreCase("GBP")) {
                     DSC.A().log("currency=" + currency + " rate=" + rate);
                     BigDecimal rateDecimal = new BigDecimal(rate);
                     BigDecimal precioDecimal = new BigDecimal(precioPounds);
                     precioEuros = precioDecimal.divide(rateDecimal, 2).doubleValue();
                     DSC.A().log("AmfWS::fetchPrice() precioEuros=" + precioEuros);
                     break;
                  }

               } else {
                  DSC.A().log("AmfWS::pounds2euros() no es un Element");
               }
            }
         } else {

         }
      } catch (Exception e) {
         DSC.A().log("AmfWS::pounds2euros() ERROR " + e);
      }
      return precioEuros;
   }
}