package org.iso8583.WorkSheet.xlsx;

public enum TimeOut {


  T4("T4", 3, "3 lan timeout 1 lan success", new int[]{-1}, 4),
  T8("T8", 7, "7 lan timeout 1 lan success", new int[]{-1}, 8),
  T10("T10", 9, "7 lan timeout 1 lan success", new int[]{-1}, 10),
  T11("T11", 10, "7 lan timeout 1 lan success", new int[]{-1}, 11),
  T15("T15", 14, "4 lan timeout 1 lan fail 1 lan success  ", new int[]{1}, 15),
  T19("T19", 1, "8 lan timeout 1 lan fail 0 lan success ", new int[]{2}, 5),
  T24("T24", 6, "4 lan timeout 2 lan fail 0 lan success", new int[]{7}, 9),
  T28("T28", 10, "4 lan timeout 2 lan fail 0 lan success", new int[]{11}, 13),
  T30("T30", 12, "4 lan timeout 2 lan fail 0 lan success", new int[]{13}, -1),
  T31("T31", 13, "3 lan timeout 1 lan fail 1 lan success", new int[]{14}, -1),
  T35("T35", 5, "1 lan timeout 2 lan fail 1 lan success", new int[]{1, 6}, 9),
  T39("T39", 9, "1 lan timeout 3 lan fail 0 lan success", new int[]{1, 10}, 13),
  T44("T44", 14, "1 lan timeout 3 lan fail 0 lan success", new int[]{1, 15}, -1),
  T48("T48", 10, "1 lan timeout 3 lan fail 0 lan success", new int[]{1, 5, 11}, 13),
  T50("T50", 12, "1 lan timeout 3 lan fail 0 lan success", new int[]{1, 5, 13}, -1),
  T51("T51", 13, "1 lan timeout 3 lan fail 0 lan success", new int[]{1, 5, 14}, -1),
  T55("T55", 6, "1 lan timeout 3 lan fail 0 lan success", new int[]{1}, 7),
  T59("T59", 10, "1 lan timeout 3 lan fail 0 lan success", new int[]{1}, 11),
  T64("T64", 15, "1 lan timeout 3 lan fail 0 lan success", new int[]{1}, 16),
  T68("T68", 12, "1 lan timeout 3 lan fail 0 lan success", new int[]{1, 5}, 13),
  T70("T70", 14, "1 lan timeout 3 lan fail 0 lan success", new int[]{1, 5}, 15),
  T71("T71", 15, "1 lan timeout 3 lan fail 0 lan success", new int[]{1, 5}, 16),
  T75("T75", 4, "1 lan timeout 3 lan fail 0 lan success", new int[]{5, 9, 13}, -1),
  T79("T79", 8, "1 lan timeout 3 lan fail 0 lan success", new int[]{9, 13}, -1),
  T84("T84", 1, "1 lan timeout 3 lan fail 0 lan success", new int[]{2, 5}, 9),
  T88("T88", 2, "1 lan timeout 3 lan fail 0 lan success", new int[]{3, 5, 9}, 13),
  T90("T90", 4, "1 lan timeout 3 lan fail 0 lan success", new int[]{5, 9}, 13),
  T91("T91", 5, "1 lan timeout 3 lan fail 0 lan success", new int[]{2, 6}, 9),
  T95("T95", 6, "1 lan timeout 3 lan fail 0 lan success", new int[]{3, 7, 9}, 13),
  T99("T99", 11, "1 lan timeout 3 lan fail 0 lan success", new int[]{4, 8, 12}, 13),

  //QUY TRÌNH 7.1
  T104("T104", 1, "7.1 - MB", new int[]{-1}, 2),
  T105("T105", 2, "7.1 - MB", new int[]{3}, -1),
  T106("T106", 3, "7.1 - MB", new int[]{-1}, 4),
  T107("T107", 4, "7.1 - MB", new int[]{-1}, -1),
  //QUY TRÌNH 7
  //  WF3;4;6
  T200("T200", 1, "7.1 - MB", new int[]{-1}, 2),
  T201("T201", 2, "7.1 - MB", new int[]{3}, -1),
  T202("T202", 3, "7.1 - MB", new int[]{-1}, 4),
  T203("T203", 4, "7.1 - MB", new int[]{-1}, -1),
  //WF1;2
  T204("T204", 1, "7.1 - MB", new int[]{2}, -1),
  T205("T205", 2, "7.1 - MB", new int[]{-1}, 3),
  T206("T206", 3, "7.1 - MB", new int[]{4}, -1),
  T207("T207", 4, "7.1 - MB", new int[]{-1}, -1),
  //T208("T208", -1, "7.1 - MB", new int[]{1}, -1),
  T209("T209", 2, "7.1 - MB", new int[]{-1}, 3),
  T210("T210", 3, "7.1 - MB", new int[]{4}, -1),
  T211("T211", 4, "7.1 - MB", new int[]{-1}, -1),
  T212("T212", 3, "7.1 - MB", new int[]{4}, -1),
  T213("T213", 4, "7.1 - MB", new int[]{-1}, -1),
  //QUY TRÌNH 7.3
  T108("T108", 1, "7.1 - MB", new int[]{-1}, 2),
  T112("T112", 4, "7.1 - MB", new int[]{2}, -1),
  T116("T116", 3, "7.1 - MB", new int[]{-1}, 4),


  T1("T1", -1, "7.1 - MB", new int[]{-1}, 1),
  T5("T5", 4, "7.1 - MB", new int[]{-1}, 5),
  T9("T9", 8, "7.1 - MB", new int[]{-1}, 9),
  T14("T14", 14, "7.1 - MB", new int[]{-1}, 15),
  T18("T18", -1, "7.1 - MB", new int[]{1}, 5),
  T20("T20", 2, "7.1 - MB", new int[]{3}, 5),
  T21("T21", 3, "7.1 - MB", new int[]{4}, 5),
  T25("T25", 7, "7.1 - MB", new int[]{8}, 9),
  T29("T29", 11, "7.1 - MB", new int[]{12}, 13),
  T34("T34", -1, "7.1 - MB", new int[]{1, 5}, 9),
  T38("T38", 8, "7.1 - MB", new int[]{1, 9}, 13),
  T40("T40", 10, "7.1 - MB", new int[]{1, 11}, 13),
  T41("T41", 11, "7.1 - MB", new int[]{1, 12}, 13),
  T45("T45", 15, "7.1 - MB", new int[]{1, 16}, -1),
  T49("T49", 11, "7.1 - MB", new int[]{1, 5, 12}, 13),
  T54("T54", 5, "7.1 - MB", new int[]{1}, 6),
  T58("T58", 9, "7.1 - MB", new int[]{1}, 10),
  T60("T60", 11, "7.1 - MB", new int[]{1}, 12),
  T61("T61", 12, "7.1 - MB", new int[]{1}, 13),
  T65("T65", 9, "7.1 - MB", new int[]{1, 5}, 10),
  T69("T69", 13, "7.1 - MB", new int[]{1, 5}, 14),
  T74("T74", 3, "7.1 - MB", new int[]{4, 5, 9, 13}, -1),
  T78("T78", 7, "7.1 - MB", new int[]{8, 9, 13}, -1),
  T80("T80", 9, "7.1 - MB", new int[]{10, 13}, -1),
  T81("T81", 10, "7.1 - MB", new int[]{11, 13}, -1),
  T85("T85", 2, "7.1 - MB", new int[]{3, 5}, 9),
  T89("T89", 3, "7.1 - MB", new int[]{4, 5, 9}, 13),
  T94("T94", 5, "7.1 - MB", new int[]{2, 6, 9}, 13),
  T98("T98", 10, "7.1 - MB", new int[]{3, 7, 11}, 13),
  T102("T102", 7, "1 lan timeout 3 lan fail 0 lan success", new int[]{6}, 9),
  T103("T103", 8, "1 lan timeout 3 lan fail 0 lan success", new int[]{-1}, 9),


  T2("T2", 1, "7.1 - MB", new int[]{-1}, 2),
  T3("T3", 2, "7.1 - MB", new int[]{-1}, 3),
  T6("T6", 5, "7.1 - MB", new int[]{-1}, 6),
  T7("T7", 4, "7.1 - MB", new int[]{-1}, 7),
  T12("T12", 11, "7.1 - MB", new int[]{-1}, 12),
  T13("T13", 12, "7.1 - MB", new int[]{-1}, 13),
  T16("T16", 15, "7.1 - MB", new int[]{-1}, 16),
  T17("T17", 16, "7.1 - MB", new int[]{-1}, -1),
  T22("T22", 4, "7.1 - MB", new int[]{5}, 9),
  T23("T23", 5, "7.1 - MB", new int[]{6}, 9),
  T76("T76", 5, "7.1 - MB", new int[]{6, 9, 13}, -1),
  T77("T77", 6, "7.1 - MB", new int[]{7, 9, 13}, -1),
  T72("T72", 1, "7.1 - MB", new int[]{2, 5, 9, 13}, -1),
  T73("T73", 2, "7.1 - MB", new int[]{3, 5, 9, 13}, -1),
  T66("T66", 10, "7.1 - MB", new int[]{1, 5}, 11),
  T67("T67", 11, "7.1 - MB", new int[]{1, 5}, 12),
  T62("T62", 13, "7.1 - MB", new int[]{1}, 14),
  T63("T63", 14, "7.1 - MB", new int[]{1}, 15),
  T56("T56", 7, "7.1 - MB", new int[]{1}, 8),
  T57("T57", 8, "7.1 - MB", new int[]{1}, 9),
  T52("T52", 14, "7.1 - MB", new int[]{1, 5, 15}, -1),
  T53("T53", 15, "7.1 - MB", new int[]{1, 5, 16}, -1),
  T46("T46", -1, "7.1 - MB", new int[]{1, 5, 9}, 13),
  T47("T47", 9, "7.1 - MB", new int[]{1, 5, 10}, 13),
  T42("T42", 12, "7.1 - MB", new int[]{1, 13}, -1),
  T43("T43", 13, "7.1 - MB", new int[]{1, 14}, -1),
  T36("T36", 6, "7.1 - MB", new int[]{1, 7}, 9),
  T37("T37", 7, "7.1 - MB", new int[]{1, 8}, 9),
  T32("T32", 14, "7.1 - MB", new int[]{15}, -1),
  T33("T33", 15, "7.1 - MB", new int[]{16}, -1),
  T26("T26", 8, "7.1 - MB", new int[]{9}, 13),
  T27("T27", 9, "7.1 - MB", new int[]{10}, 13),
  T82("T82", 11, "7.1 - MB", new int[]{12, 13}, -1),
  T83("T83", 12, "7.1 - MB", new int[]{13}, -1),
  T86("T86", 3, "7.1 - MB", new int[]{4, 5}, 9),
  T87("T87", 1, "7.1 - MB", new int[]{2, 5, 9}, 13),
  T92("T92", 6, "7.1 - MB", new int[]{3, 7}, 9),
  T93("T93", 7, "7.1 - MB", new int[]{4, 8}, 9),
  T96("T96", 7, "7.1 - MB", new int[]{4, 8, 9}, 13),

  T97("T97", 9, "7.1 - MB", new int[]{2, 6, 10}, 13),
  T100("T100", 5, "7.1 - MB", new int[]{6}, 9),
  T101("T101", 6, "7.1 - MB", new int[]{7}, 9),
  ;

  private String errorCode;
  private int numberTimout;
  private String description;
  private int failPoint[];
  private int successPoint;

  TimeOut(String errorCode, int numberTimout, String description, int[] failPoint, int successPoint) {
    this.errorCode = errorCode;
    this.numberTimout = numberTimout;
    this.description = description;
    this.failPoint = failPoint;
    this.successPoint = successPoint;
  }

  public int[] getFailPoint() {
    return failPoint;
  }

  public void setFailPoint(int[] failPoint) {
    this.failPoint = failPoint;
  }

  public int getSuccessPoint() {
    return successPoint;
  }

  public void setSuccessPoint(int successPoint) {
    this.successPoint = successPoint;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public int getNumberTimout() {
    return numberTimout;
  }

  public void setNumberTimout(int numberTimout) {
    this.numberTimout = numberTimout;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
