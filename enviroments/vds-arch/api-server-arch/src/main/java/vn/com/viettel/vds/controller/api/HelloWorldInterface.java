package vn.com.viettel.vds.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.viettel.vds.dto.request.ExampleRequestDTO;
import vn.com.viettel.vds.dto.response.ExampleResponseDTO;
import vn.com.viettel.vds.factory.response.GeneralResponse;

@RequestMapping("${application-context-name}/v1/api")
public interface HelloWorldInterface {

    /**
     * Hello World GET Method Example
     * @param id
     * @param name
     * @param age
     * @return
     */
    @GetMapping(value = "/hello-world/{id}")
    @Operation(
            summary = "Hello world",
            tags = {"hello-world"}
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "This is sample response",
                                    summary = "Sample Response",
                                    value = "{\"firstField\": \"sample\", \"secondField\": \"sample\"}"
                            ),
                    }

            )
    )
    ResponseEntity<GeneralResponse<ExampleResponseDTO>> helloWorld(
            @Parameter(
                    name = "id",
                    in = ParameterIn.PATH,
                    description = "ID of Hello world",
                    required = true,
                    examples = {
                            @ExampleObject(
                                    name = "Example 1",
                                    value = "1"
                            ),
                            @ExampleObject(
                                    name = "Example 2",
                                    value = "2"
                            )
                    }
            )
            @PathVariable("id") String id,

            @Parameter(
                    name = "name",
                    in = ParameterIn.QUERY,
                    description = "Name Query",
                    required = true,
                    example = "hoadx"
            )
            @RequestParam String name,

            @Parameter(
                    name = "age",
                    in = ParameterIn.QUERY,
                    description = "Age Query",
                    example = "18"
            )
            @RequestParam(required = false) Integer age
    );

    /**
     * Hello world POST Method Example
     * @param id
     * @param requestDTO
     * @return
     */
    @PostMapping(value = "/hello-world/{id}")
    @Operation(
            summary = "Hello World",
            tags = {"hello-world"}
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "This is sample response",
                                    summary = "Sample Response",
                                    value = "{\"status\":{\"code\":\"00\",\"message\":\"Success\"},\"data\":{\"firstField\":\"sample value\",\"secondField\":\"sample value\"}}"
                            ),
                    }

            )
    )
    ResponseEntity<GeneralResponse<ExampleResponseDTO>> helloWorldPost(
            @Parameter(
                    name = "id",
                    in = ParameterIn.PATH,
                    description = "ID of Hello world",
                    required = true
            )
            @PathVariable("id") String id,
            @RequestBody(
                    description = "Sample Request",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ExampleRequestDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "This is sample request",
                                            summary = "Sample Request",
                                            value = "{\"firstField\": \"sample\", \"secondField\": \"sample\"}"
                                    ),
                            }
                    )
            ) ExampleRequestDTO requestDTO);
}