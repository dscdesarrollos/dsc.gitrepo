package org.dsc.af.model;

public class Pais {
   private final String code;
   private final String tag;
   private final String m;

   public Pais(final String code, final String tag, final String m) {
      this.code = code;
      this.tag = tag;
      this.m = m;
   }

   public String code() {
      return this.code;
   }

   public String tag() {
      return this.tag;
   }

   public String m() {
      return this.m;
   }

   public String toString() {
      return this.code;
   }
}
