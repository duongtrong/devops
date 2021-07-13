package vn.com.viettel.vds.factory.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.com.viettel.vds.locale.Translator;

import java.io.Serializable;


public class ResponseStatus implements Serializable {

    public ResponseStatus(String code, boolean setMessageImplicitly) {
        setCode(code, setMessageImplicitly);
    }

    private String code;

    /**
     * Set the code. this will implicitly set the message based on the locale
     *
     * @param code
     */
    public void setCode(String code) {
        setCode(code, true);
    }

    /**
     * Set the code
     *
     * @param code
     */
    public void setCode(String code, boolean setMessageImplicitly) {
        this.code = code;
        if (setMessageImplicitly) {
            this.message = Translator.toLocale(code);
        }
    }

    public String getCode() {
        return code;
    }

    @JsonProperty("message")
    private String message;

    @Override
    public String toString() {
        return "{" + "\"code\":\"" + code + "\"" +
                ", \"message\":\"" + message + "\"" +
                '}';
    }
}
