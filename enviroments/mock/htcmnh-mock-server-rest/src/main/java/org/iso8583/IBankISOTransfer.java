package org.iso8583;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IBankISOTransfer {
  private static ArrayList<Integer> counts = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1));
  private static ArrayList<Integer> countf = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1));
  public static int COUNT_NUMBER_SEND = 1;
  public static int COUNT_NUMBER_REQUEST = 1;
  public static int COUNT_NUMBER_R = 1;

  public ISOMsg bankInternal(ISOMsg isoMsg) throws ISOException {
    ISOMsg reply = new ISOMsg();
    reply.setMTI("0410");
    reply.set(3, isoMsg.getString(3));
    reply.set(4, isoMsg.getString(4));
    reply.set(7, isoMsg.getString(7));
    reply.set(11, isoMsg.getString(11));
    reply.set(40, isoMsg.getString(40));
    reply.set(55, isoMsg.getString(55));
    reply.set(64, isoMsg.getString(64));

    reply.set(28, isoMsg.getString(28));
    reply.set(35, isoMsg.getString(35));
    reply.set(44, isoMsg.getString(44));
    reply.set(54, isoMsg.getString(54));
    reply.set(102, isoMsg.getString(102));
    reply.set(103, isoMsg.getString(103));
    reply.set(122, isoMsg.getString(122));


    return checkResponseCode(isoMsg, reply);
  }

  public ISOMsg bankExternal(ISOMsg isoMsg) throws ISOException {
    ISOMsg reply = new ISOMsg();
    reply.setMTI("0410");
    reply.set(3, isoMsg.getString(3));
    reply.set(4, isoMsg.getString(4));
    reply.set(7, isoMsg.getString(7));
    reply.set(11, isoMsg.getString(11));
    reply.set(40, isoMsg.getString(40));
    reply.set(55, isoMsg.getString(55));
    reply.set(64, isoMsg.getString(64));
    reply.set(74, isoMsg.getString(74));

    reply.set(28, isoMsg.getString(28));
    reply.set(35, isoMsg.getString(35));
    reply.set(44, isoMsg.getString(44));
    reply.set(54, isoMsg.getString(54));
    reply.set(102, isoMsg.getString(102));
    reply.set(103, isoMsg.getString(103));
    reply.set(122, isoMsg.getString(122));

    return checkResponseCode(isoMsg, reply);
  }

  public ISOMsg bankCardExternal(ISOMsg isoMsg) throws ISOException {
    ISOMsg reply = new ISOMsg();
    reply.setMTI("0410");
    reply.set(3, isoMsg.getString(3));
    reply.set(4, isoMsg.getString(4));
    reply.set(7, isoMsg.getString(7));
    reply.set(11, isoMsg.getString(11));
    reply.set(28, isoMsg.getString(28));
    reply.set(35, "BANK");
    reply.set(37, isoMsg.getString(37));
    reply.set(55, isoMsg.getString(55));
    reply.set(60, isoMsg.getString(60));
    reply.set(64, isoMsg.getString(64));
    reply.set(102, isoMsg.getString(102));
    reply.set(103, isoMsg.getString(103));
    return checkResponseCode(isoMsg, reply);
  }

  public ISOMsg bankCardInternal(ISOMsg isoMsg) throws ISOException {
    ISOMsg reply = new ISOMsg();
    reply.setMTI("0410");
    reply.set(3, isoMsg.getString(3));
    reply.set(4, isoMsg.getString(4));
    reply.set(7, isoMsg.getString(7));
    reply.set(11, isoMsg.getString(11));
    reply.set(28, isoMsg.getString(28));
    reply.set(35, "BANK");
    reply.set(37, isoMsg.getString(37));
    reply.set(55, isoMsg.getString(55));
    reply.set(60, isoMsg.getString(60));
    reply.set(64, isoMsg.getString(64));
    reply.set(102, isoMsg.getString(102));
    reply.set(103, isoMsg.getString(103));
    return checkResponseCode(isoMsg, reply);
  }

  public ISOMsg bankRechargeProcessCode(ISOMsg isoMsg) throws ISOException {
    ISOMsg reply = new ISOMsg();
    reply.setMTI("0410");
    reply.set(3, isoMsg.getString(3));
    reply.set(4, isoMsg.getString(4));
    reply.set(7, isoMsg.getString(7));
    reply.set(11, isoMsg.getString(11));
    reply.set(40, isoMsg.getString(40));
    reply.set(55, isoMsg.getString(55));
    reply.set(59, isoMsg.getString(59));//0
    reply.set(64, isoMsg.getString(64));
    reply.set(102, isoMsg.getString(102));//0
    reply.set(103, isoMsg.getString(103));//0
    reply.set(122, isoMsg.getString(122));//0
    return checkResponseCode(isoMsg, reply);
  }

  public ISOMsg accountBalance(ISOMsg isoMsg) throws ISOException {
    ISOMsg reply = new ISOMsg();
    reply.setMTI("0210");
    reply.set(3, isoMsg.getString(3));
    reply.set(7, isoMsg.getString(7));
    reply.set(11, isoMsg.getString(11));
    reply.set(32, isoMsg.getString(32));
    reply.set(37, isoMsg.getString(37));
    //reply.set(54, isoMsg.getString(54));
    //reply.set(39, isoMsg.getString(39));
    if ("VTT1811".equals(isoMsg.getString(102)) || "MB1221".equals(isoMsg.getString(102))) {
      reply.set(54, "10000000");
    }
    reply.set(55, isoMsg.getString(55));
    reply.set(64, isoMsg.getString(64));
    reply.set(80, isoMsg.getString(80));
    reply.set(90, "FT0000000000");
    reply.set(100, isoMsg.getString(100));
    reply.set(102, isoMsg.getString(102));
    reply.set(39, "00"); // auto Success
    return reply;
  }

  private ISOMsg checkResponseCode(ISOMsg isoMsg, ISOMsg reply) {

    String[] codeSuccess = {"T109", "T110", "T111", "T113", "T114", "T115", "T117", "T112",
            "T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12", "T13", "T14", "T15",
            "T16", "T17", "T18", "T19", "T20", "T21", "T22", "T23", "T24", "T25", "T26", "T27", "T28", "T29",
            "T30", "T31", "T32", "T33", "T34", "T35", "T36", "T37", "T38", "T39", "T40", "T41", "T42", "T43",
            "T44", "T45", "T46", "T47", "T48", "T49", "T50", "T51", "T52", "T53", "T54", "T55", "T56",
            "T57", "T58", "T59", "T60", "T61", "T62", "T63", "T64", "T65", "T66", "T67", "T68", "T69", "T70", "T71", "T72",
            "T73", "T74", "T75", "T76", "T77", "T78", "T79", "T80", "T81", "T82", "T83", "T84", "T85", "T86", "T87",
            "T88", "T89", "T90", "T91", "T92", "T93", "T94", "T95", "T96", "T97", "T98", "T99", "T100", "T101", "T102", "T103",
            "T406", "T407", "THATBAI", "T409", "T410", "L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "T401", "T402", "T403",
            "Y1", "Y2", "Y3", "Y4", "Y5", "Y6", "Y7", "Y8"};

    List<Integer> errorcode = new ArrayList<>(Arrays.asList(23, 25, 27, 30, 31, 33, 34, 35,
            36, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 56,
            57, 64, 65, 66, 67, 68, 71, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95));
    if ("T405".equals(isoMsg.getString(55)) || "T404".equals(isoMsg.getString(55))) {
      if ("MB".equals(isoMsg.getString(36))) {
        reply.set(39, "01");
        return reply;
      } else if ("VTT".equals(isoMsg.getString(35))) {
        reply.set(39, "00");
        return reply;
      }
    }

    if (isoMsg.getString(55).contains("R")) {
//   Revert 1 = Success T401
//      if (COUNT_NUMBER_R == 1) {
//        COUNT_NUMBER_R = 1;
//        reply.set(39, "00");
//        return reply;
//      }
//   Revert 2 = Success T402
//      if (COUNT_NUMBER_R < 2) {
//        COUNT_NUMBER_R++;
//        reply.set(39, "01");
//        return reply;
//      } else if (COUNT_NUMBER_R == 2) {
//        COUNT_NUMBER_R = 1;
//        reply.set(39, "00");
//        return reply;
//      }
//   Revert 3 = Success T403
//      if (COUNT_NUMBER_R < 3) {
//        COUNT_NUMBER_R++;
//        reply.set(39, "01");
//        return reply;
//      } else if (COUNT_NUMBER_R == 3) {
//        COUNT_NUMBER_R = 1;
//        reply.set(39, "00");
//        return reply;
//      }
//      T404
//      if (COUNT_NUMBER_R < 4) {
//        COUNT_NUMBER_R++;
//        reply.set(39, "01");
//        return reply;
//      } else if (COUNT_NUMBER_R == 4) {
//        COUNT_NUMBER_R = 1;
//        reply.set(39, "00");
//        return reply;
//      }
//   Revert 4 = Fail T405
      if (COUNT_NUMBER_R < 4) {
        COUNT_NUMBER_R++;
        reply.set(39, "01");
        return reply;
      } else if (COUNT_NUMBER_R == 4) {
        COUNT_NUMBER_R = 1;
        reply.set(39, "01");
        return reply;
      }
    }

    if ("S1".equals(isoMsg.getString(55))) {
      if (counts.get(1) == 1) {
        resetCount();
        counts.set(1, counts.get(1) + 1);
        reply.set(39, "01");
        return reply;
      } else if (counts.get(1) == 2) {
        counts.set(1, 1);
        reply.set(39, "00");
        return reply;
      }
    }

    if ("S7".equals(isoMsg.getString(55))) {
      resetCount();
      reply.set(39, "12");
      return reply;
    }

    if ("S8".equals(isoMsg.getString(55))) {
      if (counts.get(8) == 1) {
        resetCount();
        counts.set(8, counts.get(8) + 1);
        reply.set(39, "02");
        return reply;
      } else if (counts.get(8) == 2) {
        counts.set(8, 1);
        reply.set(39, "00");
        return reply;
      }
    }

    if ("F1".equals(isoMsg.getString(55))) {
      resetCount();
      reply.set(39, "00");
      return reply;
    }

    if ("F6".equals(isoMsg.getString(55))) {
      if (countf.get(6) == 1) {
        resetCount();
        countf.set(6, countf.get(6) + 1);
        reply.set(39, "00");
        return reply;
      } else if (countf.get(6) == 2) {
        countf.set(6, countf.get(6) + 1);
        reply.set(39, "14");
        return reply;
      } else if (countf.get(6) == 3) {
        countf.set(6, countf.get(6) + 1);
        reply.set(39, "00");
        return reply;
      } else if (countf.get(6) == 4) {
        countf.set(6, 1);
        reply.set(39, "00");
        return reply;
      }
    }

    if ("F7".equals(isoMsg.getString(55))) {
      if (countf.get(7) == 1) {
        resetCount();
        countf.set(7, countf.get(7) + 1);
        reply.set(39, "00");
        return reply;
      } else if (countf.get(7) == 2) {
        countf.set(7, countf.get(7) + 1);
        reply.set(39, "14");
        return reply;
      } else if (countf.get(7) == 3) {
        countf.set(7, countf.get(7) + 1);
        reply.set(39, "00");
        return reply;
      } else if (countf.get(7) == 4) {
        countf.set(7, countf.get(7) + 1);
        reply.set(39, "14");
        return reply;
      } else if (countf.get(7) == 5) {
        countf.set(7, countf.get(7) + 1);
        reply.set(39, "00");
        return reply;
      } else if (countf.get(7) == 6) {
        countf.set(7, 1);
        reply.set(39, "00");
        return reply;
      }
    }

    String[] switchingCode = {"F2", "F3", "F4", "F5", "F9", "F10", "F11", "F12", "F13", "F14", "F15", "F16"};

    for (String code : switchingCode) {
      if (code.equals(isoMsg.getString(55))) {
        resetCount();
        reply.set(39, "00");
        return reply;
      }
    }

    if ("F8".equals(isoMsg.getString(55))) {
      if (countf.get(8) == 1) {
        resetCount();
        countf.set(8, countf.get(8) + 1);
        reply.set(39, "00");
        return reply;
      } else if (countf.get(8) == 2) {
        countf.set(8, countf.get(8) + 1);
        reply.set(39, "02");
        return reply;
      } else if (countf.get(8) == 3) {
        countf.set(8, countf.get(8) + 1);
        reply.set(39, "00");
        return reply;
      } else if (countf.get(8) == 4) {
        countf.set(8, 1);
        reply.set(39, "00");
        return reply;
      }
    }

    for (String codex : codeSuccess) {
      if (isoMsg.getValue(55).equals(codex)) {
        reply.set(39, "00");
        return reply;
      }
    }
    for (Integer integer : errorcode) {
      if (isoMsg.getValue(55).equals(integer.toString())) {
        reply.set(39, "99");
      } else {
        reply.set(39, isoMsg.getValue(55).toString());
      }
      return reply;
    }
    return null;
  }

  private void resetCount() {
    for (int i = 0; i < counts.size(); i++) {
      counts.set(i, 1);
    }
    for (int i = 0; i < countf.size(); i++) {
      countf.set(i, 1);
    }
  }
}

