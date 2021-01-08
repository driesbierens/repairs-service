package com.project.repairservice.controller;

import com.project.repairservice.model.Repair;
import com.project.repairservice.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
            repairRepository.save(new Repair("c001","e001","onderhoud", 250.0, LocalDate.now().toString(), "Groot onderhoud", new String[]{"abc1", "abc2"}));
            repairRepository.save(new Repair("c002","e001", "onderhoud", 350.0, LocalDate.now().toString(), "Groot onderhoud en smering", new String[]{"abc1", "abc2", "abc3"}));
            repairRepository.save(new Repair("c003","e002", "motor", 3500.0, LocalDate.of(1996,5,30).toString(), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"}));
            repairRepository.save(new Repair("c050","e002", "motor", 3500.0, LocalDate.of(1996,5,30).toString(), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"}));
        }

        System.out.println("Repairs test: " + repairRepository.findRepairsByEmployeeId("e001").size());
    }

    @GetMapping(value = "/repairs/uuid/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Repair getRepairByUuid(@PathVariable String uuid) {
        return repairRepository.findRepairByRepairUuid(uuid);
    }

    @GetMapping(value = "/repairs/employee/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Repair> getRepairsByEmployeeId(@PathVariable String employeeId) {
        return repairRepository.findRepairsByEmployeeId(employeeId);
    }

    @GetMapping(value = "/repairs/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Repair> getRepairsByCustomerId(@PathVariable String customerId) {
        return repairRepository.findRepairsByCustomerId(customerId);
    }

    @GetMapping(value = "/repairs/customer/{customerId}/employee/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Repair> getRepairsByCustomerIdAndEmployeeId(@PathVariable String customerId, @PathVariable String employeeId) {
        return repairRepository.findRepairsByCustomerIdAndEmployeeId(customerId, employeeId);
    }

    @GetMapping(value = "/repairs/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Repair> getRepairsByType(@PathVariable String type) {
        return repairRepository.findRepairsByType(type);
    }

    @GetMapping(value = "/repairs/date/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Repair> getRepairsByDate(@PathVariable String date) {
        return repairRepository.findRepairsByDate(date);
    }

    @RequestMapping(value = "/repairs", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public Repair addRepair(@RequestBody Repair repair) {
        repairRepository.save(repair);
        return repair;
    }

    @RequestMapping(value = "/repairs", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    @ResponseBody
    public Repair updatedRepair(@RequestBody Repair updatedRepair) {
        Repair retrievedRepair = repairRepository.findRepairByRepairUuid(updatedRepair.getRepairUuid());

        if (retrievedRepair != null) {
            retrievedRepair.setDescription(updatedRepair.getDescription());
            retrievedRepair.setPrice(updatedRepair.getPrice());
            retrievedRepair.setListParts(updatedRepair.getParts());

            repairRepository.save(retrievedRepair);
        }

        return retrievedRepair;
    }

    @DeleteMapping(value = "/repairs/uuid/{uuid}")
    public @ResponseBody ResponseEntity deleteRepair(@PathVariable String uuid) {
        Repair repair = repairRepository.findRepairByRepairUuid(uuid);
        if (repair != null) {
            repairRepository.delete(repair);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

