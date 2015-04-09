package org.dsc.af;

import java.util.HashSet;
import java.util.Set;

import org.dsc.af.model.Favorito;
import org.dsc.af.model.Pais;

/**
 * Traductor de articulo a buscar por inode en amazon
 * @author DSC
 */
public class AmfFav {

   public void add(final String asin, final double priceMin, final Pais paisMin) {
      // editor.putString(asin, asin + ";" + priceMin + ";" + paisMin);
   }

   public void del(final String asin) {
      // editor.remove(asin);
   }

   public Set<Favorito> getAll() {
      Set<Favorito> favoritos = new HashSet<Favorito>();
      // Object[] objs = shPrefs.getAll().values().toArray();
      // for (int i = 0; i < objs.length; i++) {
      // String obj = (String) objs[i];
      // favoritos.add(Favorito.read(obj));
      // }
      return favoritos;
   }
}
