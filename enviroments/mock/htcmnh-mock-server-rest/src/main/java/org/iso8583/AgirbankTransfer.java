package org.iso8583;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AgirbankTransfer {
  public static int countS5 = 0;
  public static int countF5 = 0;
  public static int countF400 = 0;
  public static int COUNT_NUMBER_SEND = 0;
  public static int COUNT_NUMBER_T409 = 1;

  public ISOMsg bankRechargeProcessCode(ISOMsg isoMsg) throws ISOException {
    ISOMsg reply = new ISOMsg();
    reply.setMTI("0410");
    reply.set(3, isoMsg.getString(3));
    reply.set(4, isoMsg.getString(4));
    reply.set(7, isoMsg.getString(7));
    reply.set(11, isoMsg.getString(11));
    reply.set(28, "1000");
    reply.set(32, isoMsg.getString(32));
    reply.set(54, "1010");
    reply.set(55, isoMsg.getString(55));
    reply.set(116, isoMsg.getString(116));
    reply.set(102, isoMsg.getString(102));//0
    reply.set(103, isoMsg.getString(103));//0
    reply.set(124, isoMsg.getString(124));//0
    return checkTransfer(isoMsg, reply);
  }

  public ISOMsg accountBalance(ISOMsg isoMsg) throws ISOException {
    ISOMsg reply = new ISOMsg();
    reply.setMTI("210");
    reply.set(3, isoMsg.getString(3)); // Process code
    reply.set(7, isoMsg.getString(7)); // Transmission Date/Time
    reply.set(11, isoMsg.getString(11)); // System trace audit number
    reply.set(32, isoMsg.getString(32)); // Tele code
    reply.set(90, isoMsg.getString(90)); // Original Request Id
    if ("VBA0111".equals(isoMsg.getString(102))) {
      reply.set(54, "10000000");
    }
    //reply.set(54,isoMsg.getString(54)); // Balance
    reply.set(55, isoMsg.getString(55)); // Request Id
    reply.set(116, isoMsg.getString(116));  // Messages Signature
    reply.set(102, isoMsg.getString(102)); // Account Number
    reply.set(39, "00"); // auto Success
    return reply;
  }

  public ISOMsg timeOut(ISOMsg isoMsg) throws ISOException {
    ISOMsg reply = new ISOMsg();
    reply.setMTI("210");
    reply.set(3, isoMsg.getString(3)); // Process code
    reply.set(7, isoMsg.getString(7)); // Transmission Date/Time
    reply.set(11, isoMsg.getString(11)); // System trace audit number
    reply.set(32, isoMsg.getString(32)); // Tele code
    reply.set(90, isoMsg.getString(90)); // Original Request Id
    reply.set(55, isoMsg.getString(55)); // Request Id
    reply.set(124, isoMsg.getString(124)); // Content description
    reply.set(116, isoMsg.getString(116));  // Messages Signature
    reply.set(39, "00");
    return checkTimeOut(isoMsg, reply);
  }

  public ISOMsg checkTransfer(ISOMsg isoMsg, ISOMsg reply) {
    String[] codeFail = {"T4", "T15", "T24", "T35", "T44", "T55", "T45", "T64", "T75", "T84", "T95", "T402"};
    String[] codeTimeOut = {"T112", "T208", "T400"};
    List<Integer> errorcode = new ArrayList<>(Arrays.asList(23, 25, 27, 30, 31, 33, 34, 35,
            36, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 56,
            57, 64, 65, 66, 67, 68, 71, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95));

    if ("T409".equals(isoMsg.getString(55))) {
      if (COUNT_NUMBER_T409 < 5) {
        COUNT_NUMBER_T409++;
        reply.set(39, "02");
        return reply;
      } else if (COUNT_NUMBER_T409 == 5) {
        COUNT_NUMBER_T409 = 1;
        reply.set(39, "00");
        return reply;
      }
    }

    if ("S5".equals(isoMsg.getString(55))) {
      if (countS5 == 0) {
        resetCount();
        countS5++;
        reply.set(39, "11");
        return reply;
      } else if (countS5 == 1) {
        countS5++;
        reply.set(39, "00");
        return reply;
      } else if (countS5 == 2) {
        countS5++;
        reply.set(39, "00");
        return reply;
      } else if (countS5 == 3) {
        countS5 = 0;
        reply.set(39, "00");
        return reply;
      }
    }

    if ("F5".equals(isoMsg.getString(55))) {
      if (countF5 == 0) {
        resetCount();
        countF5++;
        reply.set(39, "11");
        return reply;
      } else if (countF5 == 1) {
        countF5++;
        reply.set(39, "00");
        return reply;
      } else if (countF5 == 2) {
        countF5++;
        reply.set(39, "00");
        return reply;
      } else if (countF5 == 3) {
        countF5 = 0;
        reply.set(39, "00");
        return reply;
      }
    }

    if ("F12".equals(isoMsg.getString(55))) {
      countF5++;
      reply.set(39, "00");
      return reply;
    }

    if ("F13".equals(isoMsg.getString(55))) {
      if (countF5 == 0) {
        resetCount();
        countF5++;
        reply.set(39, "11");
        return reply;
      } else if (countF5 == 1) {
        countF5++;
        reply.set(39, "00");
        return reply;
      } else if (countF5 == 2) {
        countF5++;
        reply.set(39, "00");
        return reply;
      } else if (countF5 == 3) {
        countF5 = 0;
        reply.set(39, "00");
        return reply;
      }
    }

    if ("L4".equals(isoMsg.getString(55))) {
      reply.set(39, "00");
      return reply;
    }
    if ("Y4".equals(isoMsg.getString(55))) {
      reply.set(39, "00");
      return reply;
    }
    for (String codex : codeFail) {
      if (isoMsg.getValue(55).equals(codex)) {
        reply.set(39, "02");
        return reply;
      }
    }
    for (String codes : codeTimeOut) {
      if (isoMsg.getValue(55).equals(codes)) {
        reply.set(39, "32");
        return reply;
      }
    }
    for (Integer integer : errorcode) {
      if (isoMsg.getValue(55).equals(integer.toString())) {
        reply.set(39, "98");
        return reply;
      } else {
        reply.set(39, isoMsg.getValue(55).toString());
      }
      return reply;
    }
    return null;
  }

  private void resetCount() {
    countF5 = 0;
    countS5 = 0;
  }

  public ISOMsg checkTimeOut(ISOMsg isoMsg, ISOMsg reply) {
    List<Integer> errorcode = new ArrayList<>(Arrays.asList(23, 25, 27, 30, 31, 33, 34, 35,
            36, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 56,
            57, 64, 65, 66, 67, 68, 71, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95));
    if (isoMsg.getValue(55).equals("T112")) {
      if (COUNT_NUMBER_SEND == 0) {
        COUNT_NUMBER_SEND = 0;
        reply.set(39, "02");
        return reply;
      }
    } else if (isoMsg.getValue(55).equals("T208")) {
      if (COUNT_NUMBER_SEND == 0) {
        COUNT_NUMBER_SEND = 0;
        reply.set(39, "02");
        return reply;
      }
    } else if (isoMsg.getValue(55).equals("T400")) {
      if (countF400 == 0) {
        countF400++;
        reply.set(39, "02");
        return reply;
      } else if (countF400 == 2) {
        countF400 = 0;
        reply.set(39, "02");
        return reply;
      }
    } else {
      for (Integer integer : errorcode) {
        if (isoMsg.getValue(55).equals(integer.toString())) {
          reply.set(39, "98");
          return reply;
        } else {
          reply.set(39, isoMsg.getValue(55).toString());
        }
        return reply;
      }
    }
    return null;
  }
}
