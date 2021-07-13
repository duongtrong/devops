package vn.com.viettel.vds.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.viettel.vds.constant.ExampleEnum;
import vn.com.viettel.vds.validator.ValueOfEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExampleRequestDTO {
    private String firstField;
    private String secondField;

    @ValueOfEnum(enumClass = ExampleEnum.class)
    private String exampleEnum;
}
