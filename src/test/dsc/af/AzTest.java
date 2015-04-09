package test.dsc.af;

import org.dsc.af.AmfLoc;
import org.dsc.af.az.AzWS;

public class AzTest {

   public static void main(String[] args) {
      try {
         AzWS azws = new AzWS();
         double ret = azws.requestAmazonPrecio(AmfLoc.ES, "B00HWQVT7I");
         
         azws.searchProducts(AmfLoc.ES, "X-Men Origins");
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      

   }

}
