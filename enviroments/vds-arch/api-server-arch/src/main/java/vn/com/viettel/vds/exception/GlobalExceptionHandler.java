package vn.com.viettel.vds.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.com.viettel.vds.constant.ResponseStatusCodeEnum;
import vn.com.viettel.vds.factory.response.GeneralResponse;
import vn.com.viettel.vds.factory.response.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is an advice to automatically handle all exceptions - that is, developers don't need to create a response manually everytime an exception is thrown.
 *
 * @author thanhnd59
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception ex) {
        log.error("Exception: ", ex);
        return this.createResponse(ResponseStatusCodeEnum.INTERNAL_GENERAL_SERVER_ERROR);
    }

    @ExceptionHandler({BusinessException.class})
    public final ResponseEntity<?> handleValidationExceptions(RuntimeException ex) {
        return this.createResponse(ResponseStatusCodeEnum.BUSINESS_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return this.createResponse(ResponseStatusCodeEnum.VALIDATION_ERROR, errors);
    }

    private ResponseEntity<?> createResponse(ResponseStatusCodeEnum response) {
        ResponseStatus responseStatus = new ResponseStatus(response.getCode(), true);
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        responseObject.setStatus(responseStatus);
        return new ResponseEntity<>(responseObject, HttpStatus.valueOf(response.getHttpCode()));
    }

    private ResponseEntity<Object> createResponse(ResponseStatusCodeEnum response, Object errors) {
        ResponseStatus responseStatus = new ResponseStatus(response.getCode(), true);
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        responseObject.setStatus(responseStatus);
        responseObject.setData(errors);
        return new ResponseEntity<>(responseObject, HttpStatus.valueOf(response.getHttpCode()));
    }

    // Add more exception handler here.
}
