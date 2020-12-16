package com.project.repairservice.repository;

import com.project.repairservice.model.Repair;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepairRepository extends MongoRepository<Repair, String> {
    List<Repair> findRepairsByEmployeeId(Integer employeeId);
    List<Repair> findRepairsByCustomerId(Integer customerId);
    List<Repair> findRepairsByCustomerIdAndEmployeeId(Integer customerId, Integer employeeId);
    List<Repair> findRepairsByType(String type);
    List<Repair> findRepairsByDate(LocalDate date);
    Repair findRepairByCustomerIdAndDate(Integer customerId, LocalDate date);
}
