package org.dsc.af;

import java.util.HashSet;

import org.dsc.af.model.Pais;

public class AmfLoc {
   public static final Pais DE = new Pais("de", "httpamafind0a-21", "A8KICS1PHF7ZO");
   public static final Pais ES = new Pais("es", "articuinteree-21", "A6T89FGPU3U0Q");
   public static final Pais FR = new Pais("fr", "httpamafind00-21", "A2CVHYRTWLQO9T");
   public static final Pais IT = new Pais("it", "httpamafindeb-21", "A1HO9729ND375Y");
   public static final Pais UK = new Pais("co.uk", "httpamafind01-21", "A2OAJ7377F756P");

   public static final Pais PO = new Pais("po", "httpamafind0a-21", "");
   public static final Pais CN = new Pais("cn", "", "");
   public static final Pais US = new Pais("us", "", "");

   public static Pais is(final String code) {
      if ("cn".equals(code)) {
         return AmfLoc.CN;
      } else if ("de".equals(code)) {
         return AmfLoc.DE;
      } else if ("es".equals(code)) {
         return AmfLoc.ES;
      } else if ("fr".equals(code)) {
         return AmfLoc.FR;
      } else if ("it".equals(code)) {
         return AmfLoc.IT;
      } else if ("po".equals(code)) {
         return AmfLoc.PO;
      } else if ("co.uk".equals(code) || "en".equals(code)) {
         return AmfLoc.UK;
      } else if ("us".equals(code)) {
         return AmfLoc.US;
      } else {
         return null;
      }
   }

   public static HashSet<Pais> EUROPA;
   public static HashSet<Pais> NORTHAM;
   static {
      EUROPA = new HashSet<Pais>();
      EUROPA.add(AmfLoc.DE);
      EUROPA.add(AmfLoc.ES);
      EUROPA.add(AmfLoc.FR);
      EUROPA.add(AmfLoc.IT);
      // EUROPA.add(AmfLoc.PO);
      EUROPA.add(AmfLoc.UK);

      NORTHAM = new HashSet<Pais>();
      NORTHAM.add(AmfLoc.CN);
      NORTHAM.add(AmfLoc.US);
      NORTHAM.add(AmfLoc.UK);
   }

   public HashSet<Pais> is(final Pais pais) {
      if (AmfLoc.UK.equals(pais)) {
         // UK pertenece a los dos grupos, con prioridad EU
         return EUROPA;
      } else if (NORTHAM.contains(pais)) {
         return NORTHAM;
      } else {
         return EUROPA;
      }
   }
}
