package com.project.repairsservice.controller;

import com.project.repairsservice.model.Repair;
import com.project.repairsservice.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@RestController
public class RepairController {

    @Autowired
    private RepairRepository repairRepository;

    @PostConstruct
    public void fillDB() {
        if(repairRepository.count() == 0) {
            repairRepository.save(new Repair(001,001,"onderhoud", 250.0, LocalDate.now(), "Groot onderhoud", new String[]{"abc1", "abc2"}));
            repairRepository.save(new Repair(002,001, "onderhoud", 350.0, LocalDate.now(), "Groot onderhoud en smering", new String[]{"abc1", "abc2", "abc3"}));
            repairRepository.save(new Repair(003,002, "motor", 3500.0, LocalDate.of(1996,5,30), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"}));
            repairRepository.save(new Repair(050,002, "motor", 3500.0, LocalDate.of(1996,5,30), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"}));
        }

        System.out.println("Repairs test: " + repairRepository.findRepairsByEmployeeId(001).size());
    }

    @GetMapping("/repairs/customer/{customerId}/date/{date}")
    public Repair getRepairByCustomerIdAndDate(@PathVariable Integer customerId, @PathVariable LocalDate date) {
        return repairRepository.findRepairByCustomerIdAndDate(customerId, date);
    }

    @GetMapping("/repairs/employee/{employeeId}")
    public List<Repair> getRepairsByEmployeeId(@PathVariable Integer employeeId) {
        return repairRepository.findRepairsByEmployeeId(employeeId);
    }

    @GetMapping("/repairs/customer/{customerId}")
    public List<Repair> getRepairsByCustomerId(@PathVariable Integer customerId) {
        return repairRepository.findRepairsByCustomerId(customerId);
    }

    @GetMapping("/repairs/customer/{customerId}/employee/{employeeId}")
    public List<Repair> getRepairsByCustomerIdAndEmployeeId(@PathVariable Integer customerId, @PathVariable Integer employeeId) {
        return repairRepository.findRepairsByCustomerIdAndEmployeeId(customerId, employeeId);
    }

    @GetMapping("/repairs/type/{type}")
    public List<Repair> getRepairsByType(@PathVariable String type) {
        return repairRepository.findRepairsByType(type);
    }

    @GetMapping("/repairs/date/{date}")
    public List<Repair> getRepairsByDate(@PathVariable LocalDate date) {
        return repairRepository.findRepairsByDate(date);
    }

    @PostMapping("/repairs")
    public Repair addRepair(@RequestBody Repair repair) {
        repairRepository.save(repair);

        return repair;
    }

    @PutMapping("/repairs")
    public Repair updatedRepair(@RequestBody Repair updatedRepair) {
        Repair retrievedRepair = repairRepository.findRepairByCustomerIdAndDate(updatedRepair.getCustomerId(), updatedRepair.getDate());

        retrievedRepair.setDescription(updatedRepair.getDescription());
        retrievedRepair.setPrice(updatedRepair.getPrice());
        retrievedRepair.setListParts(updatedRepair.getParts());

        repairRepository.save(retrievedRepair);

        return retrievedRepair;
    }

    @DeleteMapping("/repairs/customer/{customerId}/date/{date}")
    public ResponseEntity deleteRepair(@PathVariable Integer customerId, @PathVariable LocalDate date) {
        Repair repair = repairRepository.findRepairByCustomerIdAndDate(customerId, date);
        if (repair != null) {
            repairRepository.delete(repair);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
