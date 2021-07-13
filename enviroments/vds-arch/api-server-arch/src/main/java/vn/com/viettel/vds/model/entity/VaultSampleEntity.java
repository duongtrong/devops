package vn.com.viettel.vds.model.entity;

import vn.com.viettel.vds.model.converter.TransitSampleConverter;

import javax.persistence.*;

@Entity
@Table(name = "vault_samples")
public class VaultSampleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Convert(converter = TransitSampleConverter.class)
	@Column(name = "encrypt_field")
	private String encryptField;

	@Column(name = "raw_field")
	private String rawField;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEncryptField() {
		return encryptField;
	}

	public void setEncryptField(String encryptField) {
		this.encryptField = encryptField;
	}

	public String getRawField() {
		return rawField;
	}

	public void setRawField(String rawField) {
		this.rawField = rawField;
	}


}
