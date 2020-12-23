package com.project.repairservice.repository;

import com.project.repairservice.model.Repair;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepairRepository extends MongoRepository<Repair, String> {
    List<Repair> findRepairsByEmployeeId(String employeeId);
    List<Repair> findRepairsByCustomerId(String customerId);
    List<Repair> findRepairsByCustomerIdAndEmployeeId(String customerId, String employeeId);
    List<Repair> findRepairsByType(String type);
    List<Repair> findRepairsByDate(LocalDate date);
    Repair findRepairByCustomerIdAndDate(String customerId, LocalDate date);
}
