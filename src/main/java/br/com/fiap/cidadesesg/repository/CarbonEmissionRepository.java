package br.com.fiap.cidadesesg.repository;

import br.com.fiap.cidadesesg.domain.CarbonEmissionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CarbonEmissionRepository extends MongoRepository<CarbonEmissionRecord, String> {
    List<CarbonEmissionRecord> findBySector(String sector);
    List<CarbonEmissionRecord> findByCityZone(String cityZone);
    List<CarbonEmissionRecord> findByYearMonth(String yearMonth);
}
