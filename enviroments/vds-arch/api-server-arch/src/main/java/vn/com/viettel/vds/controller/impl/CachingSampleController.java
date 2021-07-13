package vn.com.viettel.vds.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.com.viettel.vds.controller.api.CachingSampleInterface;
import vn.com.viettel.vds.factory.response.GeneralResponse;
import vn.com.viettel.vds.factory.response.ResponseFactory;
import vn.com.viettel.vds.model.entity.ExampleEntity;
import vn.com.viettel.vds.repository.main.ExampleRepository;

import java.util.List;

@RestController
@Slf4j
public class CachingSampleController implements CachingSampleInterface {
    @Autowired
    ResponseFactory responseFactory;

    @Autowired
    CacheManager localCacheManager;

    @Autowired(required = false)
    ExampleRepository exampleRepository;

    /**
     * Thêm phần tử mẫu vào database
     * @return
     */
    @Override
    public ResponseEntity initialCachingSample() {
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setId(1L);
        exampleEntity.setExampleField("Hello world");
        exampleEntity.getCreatedDate();

        exampleRepository.save(exampleEntity);
        return responseFactory.success("Done", String.class);
    }

    /**
     * Ví dụ về Annotation @Cacheable trong Repository
     * Nếu muốn sử dụng cache ở lớp Controller, cần thực hiện implement class ResponseEntity với interface Serializable
     * @return
     */
    @Override
    public ResponseEntity autoCachingSample() {
        List<ExampleEntity> exampleEntities = exampleRepository.findAll();
        return responseFactory.success(exampleEntities, List.class);
    }

    /**
     * Ví dụ về sử dụng cache manager để quản lý cache trong phạm vi logic của controller
     * Có thể sử dụng bean redisCacheManager tương tự như localCacheManager
     * @return
     */
    @Override
    public ResponseEntity manualCachingSample() {
        localCacheManager.getCache("exampleManualCache").putIfAbsent("Hello", "World");
        log.info("Cache with {}", localCacheManager.getCache("exampleManualCache").getNativeCache().toString());
        return responseFactory.success("Load from cache: key=Hello, value=" + localCacheManager.getCache("exampleManualCache").get("Hello", String.class), String.class);
    }

    @Override
    public ResponseEntity getExample(@PathVariable long id) {
        return responseFactory.success(exampleRepository.findById(id), ExampleEntity.class);
    }

    @Override
    public ResponseEntity verify(@RequestBody GeneralResponse<Object> object) {
        return responseFactory.success(object.getData(), Object.class);
    }
}
