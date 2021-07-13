package vn.com.viettel.vds.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatusCodeEnum {

    /**
     * response codes should follow the standard: XXXYYYY where
     * - XXX: application shortname
     * - YYYY: numeric code of the error code
     */

    SUCCESS("00", 200),
    BUSINESS_ERROR("BSA0001", 400),
    VALIDATION_ERROR("BSA0002", 400),
    INTERNAL_GENERAL_SERVER_ERROR("BSA0003", 500);
    // Adds more response codes here

    private String code;
    private int httpCode;

    @Override
    public String toString() {
        return "ResponseStatus{" +
                "code='" + code + '\'' +
                "httpCode='" + httpCode + '\'' +
                '}';
    }
}