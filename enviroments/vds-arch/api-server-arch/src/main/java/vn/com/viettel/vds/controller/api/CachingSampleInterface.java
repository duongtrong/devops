package vn.com.viettel.vds.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.viettel.vds.factory.response.GeneralResponse;

@RequestMapping("${application-context-name}/v1/api")
public interface CachingSampleInterface {
    @GetMapping(value = "/initial-caching")
    ResponseEntity initialCachingSample();

    @GetMapping(value = "/auto-caching")
    ResponseEntity autoCachingSample();

    @GetMapping(value = "/manual-caching")
    ResponseEntity manualCachingSample();

    @GetMapping(value = "/example/{id}")
    ResponseEntity getExample(@PathVariable("id") long id);

    @PostMapping(value = "/verify")
    ResponseEntity verify(@RequestBody GeneralResponse<Object> object);
}
