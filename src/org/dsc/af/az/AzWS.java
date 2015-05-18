package org.dsc.af.az;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dsc.af.AmfLoc;
import org.dsc.af.model.Pais;
import org.dsc.gapp.services.Coin;
import org.dsc.sdk.DSC;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AzWS {

   private static String AWS_ACCESS_KEY_ID = "AKIAJCQUYDWQX4K2IB4A";
   private static String AWS_SECRET_KEY = "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";

   private static String AWS_SERVICE = "AWSECommerceService";
   private static String AWS_VERSION = "2011-08-01";

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
    * @param itemId asin del producto Amazon
    * @return
    * @throws Exception
    */
   public static double itemLookup(final Pais pais, final String itemId) throws Exception {
      double precio = Coin.PRECIO_ND;
      try {
         Map<String, String> params = new HashMap<String, String>();
         params.put("Operation", "ItemLookup");
         params.put("ItemId", itemId);
         params.put("ResponseGroup", "OfferFull");

         Document doc = invokeAzWS(pais, params);         

         NodeList offerSummaryList = doc.getElementsByTagName("OfferSummary");
         if (null != offerSummaryList && offerSummaryList.getLength() > 0) {
            Node offerSummary = offerSummaryList.item(0);
            Node priceNode = offerSummary.getFirstChild().getFirstChild();
            String precioNode = priceNode.getTextContent();

            DSC.A().log("AmfWS::requestAmazonPrecio() Price precioFinal is \"" + precioNode + "\"");
            if (AmfLoc.UK.equals(pais)) {
               precioNode = Coin.gbpInEuros(precioNode);
            }
            precio = Double.parseDouble(precioNode);
         }

         NodeList offerList = doc.getElementsByTagName("Offers");
         if (null != offerList && offerList.getLength() > 0) {

         }

      } catch (Exception e) {
         DSC.A().log("recuperarPrecio request Exception = " + e);
         e.printStackTrace();
      }
      return precio;
   }

   public static void itemSearch(Pais pais, String search) {
      try {
         /*
          * The parameters that apply to the largest number of search indices are shown in the following table. Parameter
          * Valid Search Indices BrowseNode Every index except All and Blended Condition Every index except All and
          * Blended Keywords All MaximumPrice Every index except All and Blended MinimumPrice Every index except All and
          * Blended Title Every index except All and Blended
          * 
          * ItemSearch requires that you specify a search index and at least one of the following parameters:
          * Actor, Artist, AudienceRating, Author, Brand, BrowseNode, Composer, Conductor, Director
          * Keywords, Manufacturer, MusicLabel, Orchestra, Power, Publisher, Title
          */
         Map<String, String> params = new HashMap<String, String>();
         params.put("Operation", "ItemSearch");
         params.put("Title", search);
         // allowed categories "Music", "DVD", "VideoGames"
         params.put("SearchIndex", "DVD");
         params.put("ResponseGroup", "Medium");
         params.put("ItemPage", "5");

         Document doc = invokeAzWS(pais, params);
      } catch (Exception e) {
         DSC.A().log("recuperarPrecio request Exception = " + e);
      }
   }
   
   /**
    * 
    * @param pais
    * @param params
    * @return
    * @throws Exception
    */
   private static Document invokeAzWS(final Pais pais,  final Map<String, String> params) throws Exception {
      String ENDPOINT = "webservices.amazon." + pais.code();
      SRHelp helper = SRHelp.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);

      params.put("Service", AWS_SERVICE);
      params.put("Version", AWS_VERSION);
      params.put("AssociateTag", pais.tag());

      String requestUrl = helper.sign(params);
      DSC.A().log("AmfWS::invokeAzWS() Signed requestUrl is \"" + requestUrl + "\"");

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(requestUrl);

      DOMSource domSource = new DOMSource(doc);
      StringWriter writer = new StringWriter();
      StreamResult result = new StreamResult(writer);
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.transform(domSource, result);
      DSC.A().log("AmfWS::invokeAzWS() XML IN String format is: \n" + writer.toString());
      
      return doc;
   }
}
