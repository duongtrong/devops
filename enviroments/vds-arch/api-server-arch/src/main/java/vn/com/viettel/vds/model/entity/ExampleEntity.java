package vn.com.viettel.vds.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "example_entity")
@Data
public class ExampleEntity implements Serializable {
	public static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "example_field")
	private String exampleField;

    @Column(name = "created_date")
    private Date createdDate = new Date();
}
