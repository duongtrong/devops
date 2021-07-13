package vn.com.viettel.vds.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.com.viettel.vds.model.entity.VaultSampleEntity;
import vn.com.viettel.vds.model.entity.VaultSignData;

@RequestMapping("${application-context-name}/v1/api/vault")
public interface VaultSampleInterface {

    @GetMapping(value = "/kv/sample")
    ResponseEntity getSecretKV();

    @PostMapping(value = "/sign")
    ResponseEntity signData(@RequestBody VaultSignData data);

    @PostMapping(value = "/verify")
    ResponseEntity verifyData(@RequestBody VaultSignData data);

    @PostMapping(value = "/transit/create")
    ResponseEntity addVaultSample(@RequestBody VaultSampleEntity vaultSampleEntity);

    @GetMapping(value = "/transit")
    ResponseEntity getAllVaultSamples();

}