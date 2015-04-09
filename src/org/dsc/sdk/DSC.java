package org.dsc.sdk;

public class DSC {
   private static DSC dscT = null;

   public static DSC A() {
      if (null == dscT) {
         dscT = new DSC();
      }
      return dscT;
   }

   public void log(final String text) {
      // TODO sistema de logging en GAPPS
      System.out.println(text);
   }
   
   public void error(final String text) {
      // TODO sistema de logging en GAPPS
      System.out.println("ERROR: " + text);
   }
   
   public void error(final String text, final Exception e) {
      // TODO sistema de logging en GAPPS
      System.out.println("ERROR: " + text);
      e.printStackTrace();
   }
}
