package vn.com.viettel.vds.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.Plaintext;
import org.springframework.vault.support.Signature;
import org.springframework.vault.support.SignatureValidation;
import org.springframework.vault.support.VaultSignatureVerificationRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.com.viettel.vds.controller.api.VaultSampleInterface;
import vn.com.viettel.vds.factory.response.ResponseFactory;
import vn.com.viettel.vds.model.entity.VaultSampleEntity;
import vn.com.viettel.vds.model.entity.VaultSignData;
import vn.com.viettel.vds.repository.main.VaultSampleRepository;

import java.util.Collection;

@RestController
@Slf4j
public class VaultSampleController implements VaultSampleInterface {


    @Autowired(required = false)
    VaultOperations vaultOps;

    @Autowired
    ResponseFactory responseFactory;


    @Autowired(required = false)
    private VaultSampleRepository vaultSampleRepository;

    @Override
    public ResponseEntity getSecretKV() {
        return responseFactory.success(vaultOps.read("secret/spring-base-api").getData().get("sample").toString(), String.class);
    }

    @Override
    public ResponseEntity signData(@RequestBody VaultSignData data) {
        return responseFactory.success(vaultOps.opsForTransit().sign("key-to-sign", Plaintext.of(data.getInput())), Signature.class);
    }

    @Override
    public ResponseEntity verifyData(@RequestBody VaultSignData data) {
        VaultSignatureVerificationRequest vaultSignatureVerificationRequest =
                VaultSignatureVerificationRequest.create(Plaintext.of(data.getInput()), Signature.of(data.getSignature()));
        return responseFactory.success(vaultOps.opsForTransit().verify("key-to-sign", vaultSignatureVerificationRequest), SignatureValidation.class);
    }

    @Override
    public ResponseEntity addVaultSample(@RequestBody VaultSampleEntity vaultSampleEntity) {
        return responseFactory.success(vaultSampleRepository.save(vaultSampleEntity),VaultSampleEntity.class);
    }

    @Override
    public ResponseEntity getAllVaultSamples() {
        return responseFactory.success(vaultSampleRepository.findAll(), Collection.class);
    }
}