package org.dsc.gapp.services;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.dsc.sdk.DSC;

public class Crypt {
   private static Crypt me = null;
   private Cipher encryptor = null;
   private Cipher decryptor = null;
   
   
   private static String key1 = "Bar12345Bar12345"; // 128 bit key
   private static String key2 = "ThisIsASecretKet";
   
   private static final String AES = "AES";
   private static final String AES_CBC_PKCSPAD = "AES/CBC/PKCS5PADDING";

   private Crypt() {}

   public static Crypt setup() {
      try {
         if (null == me) {
            me = new Crypt();
            IvParameterSpec iv = new IvParameterSpec(key2.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key1.getBytes("UTF-8"), AES);

            me.encryptor = Cipher.getInstance(AES_CBC_PKCSPAD);
            me.encryptor.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            me.decryptor = Cipher.getInstance(AES_CBC_PKCSPAD);
            me.decryptor.init(Cipher.DECRYPT_MODE, skeySpec, iv);
         }
      } catch (Exception ex) {
         DSC.A().error("Crypt.setup " + ex.getMessage(), ex);
      }
      return me;
   }

   public static String encrypt(String value) {
      try {
         Crypt crypt = setup();
         byte[] encrypted = crypt.encryptor.doFinal(value.getBytes());
         return Base64.encodeBase64String(encrypted);
      } catch (Exception ex) {
         DSC.A().error("Crypt.setup " + ex.getMessage(), ex);
      }
      return null;
   }

   public static String decrypt(String encrypted) {
      try {
         Crypt crypt = setup();
         byte[] original = crypt.decryptor.doFinal(Base64.decodeBase64(encrypted));
         return new String(original);
      } catch (Exception ex) {
         DSC.A().error("Crypt.setup " + ex.getMessage(), ex);
      }
      return null;
   }

   public static void main(String[] args) {
      System.out.println("\noriginal string:" + "Hello World");
      String encrypted = Crypt.encrypt("Hello World");
      System.out.println("encrypted string:" + encrypted);
      String decrypted = Crypt.decrypt(encrypted);
      System.out.println("decrypted string:" + decrypted);

      System.out.println("\noriginal string:" + "TEST=2&TEST=3&test=13&Test=123");
      encrypted = Crypt.encrypt("TEST=2&TEST=3&test=13&Test=123");
      System.out.println("encrypted string:" + encrypted);
      decrypted = Crypt.decrypt(encrypted);
      System.out.println("decrypted string:" + decrypted);

      // JNCryptor cryptor = new AES256JNCryptor();
      // byte[] plaintext = "Hello, World!".getBytes();
      // String password = "secretsquirrel";
      //
      // try {
      // byte[] ciphertext = cryptor.encryptData(plaintext, password.toCharArray());
      //
      // System.out.println(cryptor.decryptData(ciphertext, password.toCharArray()));
      // } catch (CryptorException e) {
      // // Something went wrong
      // e.printStackTrace();
      // }
   }
}
