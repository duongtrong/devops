package vn.com.viettel.vds.model.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.Ciphertext;
import org.springframework.vault.support.Plaintext;

import javax.persistence.AttributeConverter;

@Component
public class TransitSampleConverter implements AttributeConverter<String, String> {

    private static VaultOperations vaultOps;

    @Autowired(required = false)
    public void initVaultOperations(VaultOperations vaultOps){
        TransitSampleConverter.vaultOps = vaultOps;
    }

    @Override
    public String convertToDatabaseColumn(String data) {
        Plaintext plaintext = Plaintext.of(data);
        String cipherText = vaultOps.opsForTransit().encrypt("order", plaintext).getCiphertext();
        return cipherText;
    }

    @Override
    public String convertToEntityAttribute(String data) {
        Ciphertext ciphertext = Ciphertext.of(data);
        String plaintext = vaultOps.opsForTransit().decrypt("order", ciphertext).asString();
        return plaintext;
    }
}
