package vn.com.viettel.vds.factory.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import vn.com.viettel.vds.constant.ResponseStatusCodeEnum;
import vn.com.viettel.vds.service.SimpleSecurityService;


@Component
public class ResponseFactory {

    private SimpleSecurityService simpleSecurityService;

    public ResponseFactory(SimpleSecurityService simpleSecurityService) {
        this.simpleSecurityService = simpleSecurityService;
    }

    public ResponseEntity success(GeneralResponse responseObject) {
        ResponseStatus responseStatus = new ResponseStatus(ResponseStatusCodeEnum.SUCCESS.getCode(), true);
        responseObject.setStatus(responseStatus);
        HttpHeaders responseHeaders = new HttpHeaders();
        // enable vault to use it
        // responseHeaders.set("Signature", simpleSecurityService.sign(responseObject));
        return ResponseEntity.ok().headers(responseHeaders).body(responseObject);
    }

    public ResponseEntity success(Object data, Class<?> clazz) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        ResponseStatus responseStatus = new ResponseStatus(ResponseStatusCodeEnum.SUCCESS.getCode(), true);
        responseObject.setStatus(responseStatus);
        responseObject.setData(clazz.cast(data));
        HttpHeaders responseHeaders = new HttpHeaders();
        // enable vault to use it
        // responseHeaders.set("Signature", simpleSecurityService.sign(responseObject));
        return ResponseEntity.ok().headers(responseHeaders).body(responseObject);
    }

    public ResponseEntity fail(GeneralResponse responseObject, ResponseStatusCodeEnum code) {
        ResponseStatus responseStatus = new ResponseStatus(code.getCode(), true);
        responseObject.setStatus(responseStatus);
        return ResponseEntity.status(code.getHttpCode()).body(responseObject);
    }

    public ResponseEntity fail(Object data, Class<?> clazz, ResponseStatusCodeEnum code) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        ResponseStatus responseStatus = new ResponseStatus(code.getCode(), true);
        responseObject.setStatus(responseStatus);
        responseObject.setData(clazz.cast(data));
        return ResponseEntity.status(code.getHttpCode()).body(responseObject);
    }
}
