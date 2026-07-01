package com.fcc.simulation.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "whisky_inventory")
public class WhiskyInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "age_label", nullable = false)
    private String ageLabel;

    @Column(name = "dist_year")
    private String distYear;

    @Column(name = "ttl")
    private String ttl;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "year_values", columnDefinition = "jsonb", nullable = false)
    private Map<String, String> yearValues = new HashMap<>();

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getAgeLabel() { return ageLabel; }
    public void setAgeLabel(String ageLabel) { this.ageLabel = ageLabel; }
    public String getDistYear() { return distYear; }
    public void setDistYear(String distYear) { this.distYear = distYear; }
    public String getTtl() { return ttl; }
    public void setTtl(String ttl) { this.ttl = ttl; }
    public Map<String, String> getYearValues() { return yearValues; }
    public void setYearValues(Map<String, String> yearValues) { this.yearValues = yearValues; }
    public int getSortOrder() { return sortOrder; }
    public void setSortOrder(int sortOrder) { this.sortOrder = sortOrder; }
}
