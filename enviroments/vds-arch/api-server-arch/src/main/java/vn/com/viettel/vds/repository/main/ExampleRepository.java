package vn.com.viettel.vds.repository.main;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.viettel.vds.model.entity.ExampleEntity;

import java.util.List;

@Repository
@CacheConfig(cacheNames = {"exampleRepository"})
public interface ExampleRepository extends JpaRepository<ExampleEntity, Long> {
    // Có thể sử dụng song song 2 bộ nhớ cache
    @Caching(cacheable = {
            @Cacheable(cacheManager = "redisCacheManager"),
            @Cacheable(cacheManager = "localCacheManager")
    })
    List<ExampleEntity> findAll();

    // khong su dung cache
    ExampleEntity findById(long id);
}
