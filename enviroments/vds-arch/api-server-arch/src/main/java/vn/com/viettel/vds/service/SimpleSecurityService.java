package vn.com.viettel.vds.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.Plaintext;
import org.springframework.vault.support.Signature;
import org.springframework.vault.support.VaultSignatureVerificationRequest;

import java.io.IOException;

@Service
public class SimpleSecurityService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired(required = false)
    VaultOperations vaultOps;

    public String sign(Object object){

        try {
            String signDataString = objectMapper.writeValueAsString(object);
            return vaultOps.opsForTransit().sign("key-to-sign", Plaintext.of(signDataString)).getSignature();
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public boolean verify(byte[] object,String signature){
        if (object.length==0){
            return true;
        }
        try {
            Object tmp = objectMapper.readValue(object,Object.class);
            String signDataString = objectMapper.writeValueAsString(tmp);
            VaultSignatureVerificationRequest vaultSignatureVerificationRequest =
                    VaultSignatureVerificationRequest.create(Plaintext.of(signDataString), Signature.of(signature));
            return vaultOps.opsForTransit().verify("key-to-sign", vaultSignatureVerificationRequest).isValid();
        } catch (IOException e) {
            return false;
        }
    }

}
