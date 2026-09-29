package br.com.fiap.cidadesesg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "emissions")
public class CarbonEmissionRecord {
    @Id
    private String id;
    private String sector;
    private String cityZone;
    private Double co2Tons;
    private Double reductionTargetTons;
    private String yearMonth;
    private Boolean certified;
    private Instant recordedAt;

    public CarbonEmissionRecord() {
        this.recordedAt = Instant.now();
        this.certified = true;
    }

    public CarbonEmissionRecord(String sector, String cityZone, Double co2Tons, Double reductionTargetTons, String yearMonth) {
        this();
        this.sector = sector;
        this.cityZone = cityZone;
        this.co2Tons = co2Tons;
        this.reductionTargetTons = reductionTargetTons;
        this.yearMonth = yearMonth;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
    public String getCityZone() { return cityZone; }
    public void setCityZone(String cityZone) { this.cityZone = cityZone; }
    public Double getCo2Tons() { return co2Tons; }
    public void setCo2Tons(Double co2Tons) { this.co2Tons = co2Tons; }
    public Double getReductionTargetTons() { return reductionTargetTons; }
    public void setReductionTargetTons(Double reductionTargetTons) { this.reductionTargetTons = reductionTargetTons; }
    public String getYearMonth() { return yearMonth; }
    public void setYearMonth(String yearMonth) { this.yearMonth = yearMonth; }
    public Boolean getCertified() { return certified; }
    public void setCertified(Boolean certified) { this.certified = certified; }
    public Instant getRecordedAt() { return recordedAt; }
    public void setRecordedAt(Instant recordedAt) { this.recordedAt = recordedAt; }
}
