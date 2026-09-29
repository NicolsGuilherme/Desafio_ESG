package br.com.fiap.cidadesesg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "sensors")
public class EnvironmentalSensor {
    @Id
    private String id;
    private String sensorCode;
    private String location;
    private String sensorType;
    private Double value;
    private String unit;
    private String status;
    private Instant timestamp;

    public EnvironmentalSensor() {
        this.timestamp = Instant.now();
        this.status = "NORMAL";
    }

    public EnvironmentalSensor(String sensorCode, String location, String sensorType, Double value, String unit) {
        this();
        this.sensorCode = sensorCode;
        this.location = location;
        this.sensorType = sensorType;
        this.value = value;
        this.unit = unit;
        this.status = evaluateStatus(sensorType, value);
    }

    public static String evaluateStatus(String type, Double val) {
        if (val == null) return "NORMAL";
        if ("AIR_QUALITY".equalsIgnoreCase(type) && val > 100.0) return "CRITICAL";
        if ("AIR_QUALITY".equalsIgnoreCase(type) && val > 50.0) return "WARNING";
        if ("NOISE_LEVEL".equalsIgnoreCase(type) && val > 80.0) return "WARNING";
        if ("ENERGY_CONSUMPTION".equalsIgnoreCase(type) && val > 500.0) return "WARNING";
        return "NORMAL";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSensorCode() { return sensorCode; }
    public void setSensorCode(String sensorCode) { this.sensorCode = sensorCode; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getSensorType() { return sensorType; }
    public void setSensorType(String sensorType) { this.sensorType = sensorType; }
    public Double getValue() { return value; }
    public void setValue(Double value) { 
        this.value = value; 
        this.status = evaluateStatus(this.sensorType, value);
    }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
