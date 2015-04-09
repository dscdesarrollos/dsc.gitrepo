package org.dsc.af.model;

public class Timing {
   public static final long SEG = 1000;
   public static final long MIN = 60 * SEG;
   public static final long HOR = 60 * MIN;
   public static final long DIA = 24 * HOR;
   public static final long[] MES = { 28 * DIA, 29 * DIA, 30 * DIA, 31 * DIA };
   public static final long ANO = 365 * DIA;
}
