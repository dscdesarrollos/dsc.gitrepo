package org.dsc.gapp.services;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dsc.af.AmfQR;
import org.dsc.gapp.core.C;
import org.dsc.sdk.DSC;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Coin {
   private static long lastTimeStamp = 0;

   private static Map<String, BigDecimal> euroTable = new HashMap<String, BigDecimal>();
   private static Map<String, BigDecimal> coinTable = new HashMap<String, BigDecimal>();

   private static final String SVC_URL = "http://www.ecb.int/stats/eurofxref/eurofxref-daily.xml";
   private static final String CUBE = "Cube";
   private static final String CURRENCY = "currency";
   private static final String RATE = "rate";

   private static final BigDecimal ONE_DEC = new BigDecimal(1);

   /**
    * Request del xml de conversion de moneda a euros y trasnformacion en Map
    */
   private static void loadCoinTable() {
      lastTimeStamp = System.currentTimeMillis();
      try {
         URL server = new URL(SVC_URL);
         HttpURLConnection connection = (HttpURLConnection) server.openConnection();
         connection.connect();

         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         Document doc = db.parse(new InputSource(connection.getInputStream()));
         connection.disconnect();
         doc.getDocumentElement().normalize();
         NodeList nodeList = doc.getElementsByTagName(CUBE);

         DSC.A().log("Exchange::inPounds() nodeList.getlength(): " + nodeList.getLength());
         for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
               Element element = (Element) node;

               String currency = element.getAttribute(CURRENCY);
               String rate = element.getAttribute(RATE);
               BigDecimal rateDecimal = new BigDecimal(rate);
               euroTable.put(currency, rateDecimal);
               coinTable.put(currency, ONE_DEC.divide(rateDecimal));

            } else {
               DSC.A().log("Exchange::inPounds() no es un Element");
            }
         }
         // conversion de euros a euros
         euroTable.put(C.EUR, ONE_DEC);
         coinTable.put(C.EUR, ONE_DEC);
      } catch (Exception e) {
         DSC.A().error("Exchange::inPounds() XML Pasing Exception = " + e, e);
      }
   }

   /**
    * Conversion de libras a euros
    */
   public static String gbpInEuros(final String pounds) {
      return exchange(C.GBP, pounds, C.EUR);
   }

   /**
    * Conversion de euros a libras
    */
   public static String eurInGbp(final String euros) {
      return exchange(C.EUR, euros, C.GBP);
   }

   /**
    * Conversion de moneda de un pais a euros
    * @param country pais
    * @param coin precio en moneda del apis
    * @return precio en euros
    */
   private static String exchange(final String countryS, final String coin, final String countryD) {
      String inEuros = C.ERR_SVC_RSP;
      if (coin.length() > 0 && coin.length() < 10 && !coin.contains(C.ERR_SVC_RSP)) {
         // peticion recarga tabla de cambio de moneda
         if (System.currentTimeMillis() - lastTimeStamp > C.DAY_INSECS) {
            loadCoinTable();
         }
         // por defecto ratio de otra moneda a euro
         BigDecimal rateDecimal = euroTable.get(countryS);
         if (C.EUR.equals(countryS)) {
            // ratio de euro a otra moneda
            rateDecimal = coinTable.get(countryD);
         }
         // TODO conversion de moneda a otra moneda no euro
         if (null != rateDecimal) {
            DSC.A().log("currencyFrom=" + countryS + "currencyTo=" + countryD + " rate=" + rateDecimal);
            // conversion libras a euros
            inEuros = applyRate(new BigDecimal(coin), rateDecimal);
         }
      }
      DSC.A().log("Exchange::inEuro().return " + inEuros);
      return inEuros;
   }

   private static String applyRate(final BigDecimal coin, BigDecimal rate) {
      String applyRate = C.ERR_SVC_RSP;
      // conversion libras a euros
      final BigDecimal coinRated = coin.divide(rate, 2);
      final String coinRatedFormat = coinRated.toString();
      // redondeo a dos cifras
      if (coinRatedFormat.length() > 8) {
         applyRate = coinRatedFormat.substring(0, coinRatedFormat.indexOf("."));
      }
      return applyRate;
   }
   
   
   public static void main(String[] args) {
      String libras = "15";
      String euros = Coin.gbpInEuros(libras);
      System.out.println(libras + " libras --> " + euros + " euros");
   }
}
