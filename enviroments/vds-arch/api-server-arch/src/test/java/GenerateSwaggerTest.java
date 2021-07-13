import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import vn.com.viettel.vds.Main;
import vn.com.viettel.vds.config.OpenApiConfig;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class, OpenApiConfig.class}, properties = {"io.springfox.staticdocs.outputDir=docs"})
@AutoConfigureMockMvc
@IfProfileValue(name = "docgen", value = "true")
public class GenerateSwaggerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    Environment env;

    @Test
    public void createSpringfoxSwaggerJson() throws Exception {
        //String designFirstSwaggerLocation = Swagger2MarkupTest.class.getResource("/swagger.yaml").getPath();
        String outputDir = env.getProperty("io.springfox.staticdocs.outputDir");
        MvcResult mvcResult = this.mockMvc.perform(get(env.getProperty("springdoc.api-docs.path","/v3/api-docs"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        MockHttpServletResponse response = mvcResult.getResponse();
        byte[] bytes = response.getContentAsByteArray();
        String swaggerJson =new String(bytes, Charset.forName("UTF-8"));
        Object object = mapper.readValue(swaggerJson,Object.class);
        Files.createDirectories(Paths.get(outputDir));
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputDir, "swagger.json"), StandardCharsets.UTF_8)){
            writer.write(mapper.writeValueAsString(object));
        }
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputDir, "swagger.yaml"), StandardCharsets.UTF_8)){
            writer.write(yamlMapper.writeValueAsString(object));
        }
    }
}

