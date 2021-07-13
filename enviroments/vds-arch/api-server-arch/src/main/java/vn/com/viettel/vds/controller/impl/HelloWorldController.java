package vn.com.viettel.vds.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.com.viettel.vds.controller.api.HelloWorldInterface;
import vn.com.viettel.vds.dto.request.ExampleRequestDTO;
import vn.com.viettel.vds.dto.response.ExampleResponseDTO;
import vn.com.viettel.vds.factory.response.GeneralResponse;
import vn.com.viettel.vds.factory.response.ResponseFactory;

@RestController
@Slf4j
public class HelloWorldController implements HelloWorldInterface {

    @Autowired
    ResponseFactory responseFactory;

    @Override
    public ResponseEntity<GeneralResponse<ExampleResponseDTO>> helloWorld(String id, String name, Integer age) {
        StringBuilder builder = new StringBuilder("Hello,");
        builder.append(name);
        if (age != null) {
            builder.append(" ").append(age.toString()).append(" years old.");
        }
        ExampleResponseDTO exampleResponseDTO = new ExampleResponseDTO();
        exampleResponseDTO.setFirstField(name);
        exampleResponseDTO.setSecondField(builder.toString());
        return responseFactory.success(new GeneralResponse<>(exampleResponseDTO));
    }

    @Override
    public ResponseEntity<GeneralResponse<ExampleResponseDTO>> helloWorldPost(String id, ExampleRequestDTO requestDTO) {
        return responseFactory.success(new GeneralResponse<>(requestDTO));
    }
}