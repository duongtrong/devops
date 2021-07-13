package vn.com.viettel.vds.repository.main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.viettel.vds.model.entity.VaultSampleEntity;

@Repository
public interface VaultSampleRepository extends JpaRepository<VaultSampleEntity, Long> {

}