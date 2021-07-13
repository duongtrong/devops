package org.iso8583;

import org.jpos.iso.ISOMsg;


public class MapErrorCode {
  public final static String INTERNAL_BANK_PROCESS_CODE = "640000";
  public final static String EXTERNAL_BANK_PROCESS_CODE = "640001";
  public final static String CARD_EXTERNAL_BANK_PROCESS_CODE = "851000";
  public final static String BANK_PROCESS_CODE = "649000";
  public final static String TIME_OUT_CODE = "679000";
  public static final String ACCOUNT_BALANCE_CODE = "311000";
  public static final String CARD_INTERNAL_BANK_PROCESS_CODE = "850000";
  public static final String PROCESS_CODE_311001 = "311001";
  private IBankISOTransfer iBankISOTransfer;

  public ISOMsg map(ISOMsg isoMsg) throws Exception {
    iBankISOTransfer = new IBankISOTransfer();
    if (isoMsg.getString(35) == null && isoMsg.getString(0).equals("0200") && isoMsg.getString(100) == null) {
      AgirbankTransfer agirbankTransfer = new AgirbankTransfer();
      if (isoMsg.getValue(3).equals(BANK_PROCESS_CODE)) { // bản tin 649000
        return agirbankTransfer.bankRechargeProcessCode(isoMsg);
      } else if (isoMsg.getValue(3).equals(TIME_OUT_CODE)) { // truy vấn kết quả giao dịch
        return agirbankTransfer.timeOut(isoMsg);
      } else if (isoMsg.getValue(3).equals(ACCOUNT_BALANCE_CODE)) {
        return agirbankTransfer.accountBalance(isoMsg);
      } else {
        throw new Exception("Ngân hàng không hỗ trợ");
      }
    } else {
      if (isoMsg.getValue(3).equals(INTERNAL_BANK_PROCESS_CODE)) { // Trong ngân hàng
        return iBankISOTransfer.bankInternal(isoMsg);
      } else if (isoMsg.getValue(3).equals(EXTERNAL_BANK_PROCESS_CODE)) { // Ngoài ngân hàng
        return iBankISOTransfer.bankExternal(isoMsg);
      } else if (isoMsg.getValue(3).equals(CARD_EXTERNAL_BANK_PROCESS_CODE)) { // Chuyển khoản số thẻ ngoài ngân hàng
        return iBankISOTransfer.bankCardExternal(isoMsg);
      } else if (isoMsg.getValue(3).equals(CARD_INTERNAL_BANK_PROCESS_CODE)) { // Chuyển khoản số thẻ trong ngân hàng
        return iBankISOTransfer.bankCardInternal(isoMsg);
      } else if (isoMsg.getValue(3).equals(BANK_PROCESS_CODE)) { // Chuyển khoản số thẻ ngoài ngân hàng
        return iBankISOTransfer.bankRechargeProcessCode(isoMsg);
      } else if (isoMsg.getValue(3).equals(PROCESS_CODE_311001)) {
        return iBankISOTransfer.accountBalance(isoMsg);
      } else {
        throw new Exception("Ngân hàng không hỗ trợ");
      }
    }
  }
}
