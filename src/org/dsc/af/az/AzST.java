package org.dsc.af.az;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Traductor de articulo a buscar por inode en amazon
 * @author DSC
 */
public class AzST {
   // para buscar por nodo --> node=
   // para buscar por descuento --> pct-off=80-99
   // para buscar por producto determinado --> field-keywords=bolso
   // para ordenar de mayor a menor precio --> sort=-price (tambien vale sort=price-desc-rank)
   // para ordenar de menor a mayor precio --> sort=price (tambien vale sort=price-asc-rank)
   // para obtener los mas populares --> sort=popularity-rank
   // para obtener los mas relevantes --> sort=relevance-rank

   public static String search(final String text) {
      String search = "field-keywords=" + text;
      String normalizedText = text.replace('á', 'a').replace('é', 'e').replace('í', 'i').replace('ó', 'o')
            .replace('ú', 'u').toLowerCase();
      for (String key : dicc.keySet()) {
         if (normalizedText.matches(key)) {
            search = "node=" + dicc.get(key);
            break;
         }
      }
      return search;
   }

   private static Map<String, String> dicc = new HashMap<String, String>();
   static {
      // tienda apps android
      dicc.put("android apps", "1661649031");
      dicc.put("android juegos", "1726755031");

      // accesorios kindle
      dicc.put("accesorios kindles", "1354858031");

      // libros
      dicc.put("ebooks kindle", "827231031"); // a favoritos
      dicc.put("libros", "599364031");
      dicc.put("libros ingles", "665418031");
      dicc.put("libros otros idiomas", "599367031");
      dicc.put("libros infantiles", "902621031");
      dicc.put("libros juveniles", "902621031");
      dicc.put("libros texto universitarios", "902673031");

      // cine, tv musica
      dicc.put("tienda mp3", "1748200031"); // a favoritos
      dicc.put("cine", "599379031");
      dicc.put("series", "665293031");
      dicc.put("blu-ray", "665303031");
      dicc.put("musica", "599373031");
      dicc.put("instrumentos musicales", "1354271031");

      // electronica
      dicc.put("saldos electronica", "230593603"); // a favoritos

      dicc.put("electronica", "599370031");
      dicc.put("fotografia videocamaras", "664660031");
      dicc.put("moviles telefonia", "931491031");
      dicc.put("tv", "664659031");
      dicc.put("television", "664659031");
      dicc.put("video", "664659031");
      dicc.put("home cinema", "664659031");
      dicc.put("audio", "664684031");
      dicc.put("mp3", "664684031");
      dicc.put("gps", "664661031");
      dicc.put("electronica accesorios", "928455031");

      // informatica
      dicc.put("informatica", "667049031");
      dicc.put("portatil", "938008031");
      dicc.put("tablet", "1676289031");
      dicc.put("sobremesa", "937994031");
      dicc.put("servidor", "937994031");
      dicc.put("componentes", "937912031");
      dicc.put("impresoras", "2457641031");
      dicc.put("tinta", "2457641031");
      dicc.put("software", "599376031");
      dicc.put("informatica accesorios", "2457643031");

      // consolas videojuegos
      dicc.put("videojuegos", "599382031");
      dicc.put("juegos pc", "665498031");
      dicc.put("consolas accesorios", "665290031");

      // juguetes bebe
      dicc.put("juguetes", "599385031");
      dicc.put("juegos", "599385031");
      dicc.put("bebe", "1703495031");

      // hogar
      dicc.put("hogar", "599391031");
      dicc.put("electrodomestico", "2165363031");
      dicc.put("cocina", "2181872031");
      dicc.put("comedor", "2181872031");
      dicc.put("casa", "1355125031");
      dicc.put("baño", "1765950031");
      dicc.put("iluminacion", "1765973031");
      dicc.put("cuidado personal", "1355102031");

      // bricolaje herramientas
      dicc.put("bricolaje", "2454133031");
      dicc.put("herramientas bricolaje", "3049288031");
      dicc.put("herramientas jardineria", "3249445031");
      dicc.put("instalacion electrica", "3049284031");
      dicc.put("fontaneria baño", "3249441031");
      dicc.put("fontaneria cocina", "3249441031");
      dicc.put("seguridad", "3049292031");
      dicc.put("prevencion", "3049292031");

      // zapatos complementos
      dicc.put("zapatos", "1571262031");
      dicc.put("bolsos", "2142049031");
      dicc.put("complementos", "2142049031");
      dicc.put("equipaje", "2454129031");
      dicc.put("relojes", "599388031");
      dicc.put("joyeria", "2454126031");

      // deporte aire libre
      dicc.put("deporte", "2454136031");
      dicc.put("fitness", "2993535031");
      dicc.put("running", "2993535031");
      dicc.put("ciclismo", "2928487031");
      dicc.put("tenis", "2985165031");
      dicc.put("padel", "2985165031");
      dicc.put("golf", "2928503031");
      dicc.put("deporte equipo", "2975183031");
      dicc.put("deporte acuaticos", "2928491031");
      dicc.put("deporte invierno", "2928493031");
      dicc.put("acampada", "2928471031");
      dicc.put("senderismo", "2928471031");
      dicc.put("ropa deportiva", "2975170031");
      dicc.put("calzado deportivo", "2928484031");
      dicc.put("gps electronica", "2928496031");

      // coche moto
      dicc.put("piezas coche", "1951051031");
      dicc.put("coche accesorios", "1951051031");
      dicc.put("herramientas coche", "2566955031");
      dicc.put("herramientas moto", "2566955031");
   }

   public static String get(final String key) {
      return dicc.get(key);
   }

   public static Set<String> keySet() {
      return dicc.keySet();
   }

   public static Collection<String> values() {
      return dicc.values();
   }

   public static String[] toArray() {
      String[] array = new String[0];
      return dicc.keySet().toArray(array);
   }
}
