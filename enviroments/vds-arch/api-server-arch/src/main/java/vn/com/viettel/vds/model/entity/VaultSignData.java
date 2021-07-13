package vn.com.viettel.vds.model.entity;

public class VaultSignData {
    private String input;
    private String signature;

    public VaultSignData(String input, String signature) {
        this.input = input;
        this.signature = signature;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}