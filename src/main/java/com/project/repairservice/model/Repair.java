package com.project.repairservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "repairs")
public class Repair {
    @Id
    private String id;
    private String customerId;
    private String employeeId;
    private String type;
    private Double price;
    private LocalDate date;
    private String description;
    private String[] parts;

    public Repair() {

    }

    public Repair(String customerId, String employeeId, String type, double price, LocalDate date, String description, String[] parts) {
        setCustomerId(customerId);
        setEmployeeId(employeeId);
        setType(type);
        setPrice(price);
        setRepairDate(date);
        setDescription(description);
        setListParts(parts);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setRepairDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getParts() {
        return parts;
    }

    public void setListParts(String[] parts) {
        this.parts = parts;
    }
}
