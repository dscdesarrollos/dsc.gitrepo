package test.dsc.af;

import org.dsc.af.AmfLoc;
import org.dsc.af.az.AzWS;

public class AzTest {

   public static void main(String[] args) {
      try {
         AzWS azws = new AzWS();
         azws.itemSearch(AmfLoc.ES, "X-Men Origins");
         double ret = azws.itemLookup(AmfLoc.ES, "B00HWQVT7I");
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
