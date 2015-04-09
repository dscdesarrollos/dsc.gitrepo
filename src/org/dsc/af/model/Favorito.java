package org.dsc.af.model;

public class Favorito {
   private final String asin;
   private final String url;
   private final String name;
   private final float price;

   public Favorito(final String asin, final String url, final String name, final float price) {
      this.asin = asin;
      this.url = url;
      this.name = name;
      this.price = price;
   }

   public String asin() {
      return this.asin;
   }

   public String url() {
      return this.url;
   }

   public String name() {
      return this.name;
   }

   public float price() {
      return this.price;
   }

   public String toString() {
      return this.asin;
   }

   public static Favorito read(final String value) {
      return null;
   }
}
