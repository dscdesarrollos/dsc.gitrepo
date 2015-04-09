package org.dsc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dsc.af.Amf;
import org.dsc.gapp.services.Crypt;

@SuppressWarnings("serial")
public class TheGate extends HttpServlet {
   public static String DSC = "dsc";
   public static String PKG = "pkg";

   public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      resp.setContentType("text/plain");
      resp.getWriter().println("Hello, world");
   }

   public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      resp.setContentType("text/plain");
      PrintWriter pw = resp.getWriter();

      String dsc = req.getParameter(DSC);
      String pkg = req.getParameter(PKG);
      dsc = Crypt.decrypt(dsc);
//      DsCReq dr = DsCReq.parse(dsc);
//      if (Amf.KEY.equals(dr.destiny)) {
//         Amf.process(dr, pkg, pw);
//      }
   }
}
