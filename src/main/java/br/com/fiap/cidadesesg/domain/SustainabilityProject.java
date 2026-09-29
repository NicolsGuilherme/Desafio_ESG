package br.com.fiap.cidadesesg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "sustainability_projects")
public class SustainabilityProject {
    @Id
    private String id;
    private String name;
    private String category;
    private Double budget;
    private Double invested;
    private Double expectedCo2ReductionYear;
    private Integer progressPercentage;
    private String status;
    private Instant createdAt;

    public SustainabilityProject() {
        this.createdAt = Instant.now();
        this.status = "EM_EXECUCAO";
        this.progressPercentage = 0;
    }

    public SustainabilityProject(String name, String category, Double budget, Double invested, Double expectedCo2ReductionYear) {
        this();
        this.name = name;
        this.category = category;
        this.budget = budget;
        this.invested = invested;
        this.expectedCo2ReductionYear = expectedCo2ReductionYear;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }
    public Double getInvested() { return invested; }
    public void setInvested(Double invested) { this.invested = invested; }
    public Double getExpectedCo2ReductionYear() { return expectedCo2ReductionYear; }
    public void setExpectedCo2ReductionYear(Double expectedCo2ReductionYear) { this.expectedCo2ReductionYear = expectedCo2ReductionYear; }
    public Integer getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(Integer progressPercentage) { this.progressPercentage = progressPercentage; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
