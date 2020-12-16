package com.project.repairservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.repairservice.model.Repair;
import com.project.repairservice.repository.RepairRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RepairControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepairRepository repairRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenRepair_whenGetRepairByCustomerIdAndDate_thenReturnJsonRepair() throws Exception {
        Repair repairCustomer1Employee1 = new Repair(001,001, "onderhoud", 250.0, LocalDate.now(), "Groot onderhoud", new String[]{"abc1", "abc2"});

        given(repairRepository.findRepairByCustomerIdAndDate(001, LocalDate.now())).willReturn(repairCustomer1Employee1);

        mockMvc.perform(get("/repairs/customer/{customerId}/date/{date}", 001, LocalDate.now()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(001)))
                .andExpect(jsonPath("$.employeeId", is(001)))
                .andExpect(jsonPath("$.type", is("onderhoud")))
                .andExpect(jsonPath("$.price", is(250.0)))
                .andExpect(jsonPath("$.date", is(LocalDate.now())))
                .andExpect(jsonPath("$.description", is("Groot onderhoud")))
                .andExpect(jsonPath("$.parts", is(new String[]{"abc1", "abc2"})));
    }

    @Test
    public void givenRepair_whenGetRepairsByEmployeeId_thenReturnJsonRepairs() throws Exception {
        Repair repairCustomer1Employee1 = new Repair(001,001, "onderhoud", 250.0, LocalDate.now(), "Groot onderhoud", new String[]{"abc1", "abc2"});
        Repair repairCustomer2Employee1 = new Repair(002,001, "onderhoud", 350.0, LocalDate.now(), "Groot onderhoud en smering", new String[]{"abc1", "abc2", "abc3"});

        List<Repair> repairList = new ArrayList<>();
        repairList.add(repairCustomer1Employee1);
        repairList.add(repairCustomer2Employee1);

        given(repairRepository.findRepairsByEmployeeId(001)).willReturn(repairList);

        mockMvc.perform(get("/repairs/employee/{employeeId}", 001))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customerId", is(001)))
                .andExpect(jsonPath("$[0].employeeId", is(001)))
                .andExpect(jsonPath("$[0].type", is("onderhoud")))
                .andExpect(jsonPath("$[0].price", is(250.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now())))
                .andExpect(jsonPath("$[0].description", is("Groot onderhoud")))
                .andExpect(jsonPath("$[0].parts", is(new String[]{"abc1", "abc2"})))
                .andExpect(jsonPath("$[1].customerId", is(002)))
                .andExpect(jsonPath("$[1].employeeId", is(001)))
                .andExpect(jsonPath("$[1].type", is("onderhoud")))
                .andExpect(jsonPath("$[1].price", is(350.0)))
                .andExpect(jsonPath("$[1].date", is(LocalDate.now())))
                .andExpect(jsonPath("$[1].description", is("Groot onderhoud en smering")))
                .andExpect(jsonPath("$[1].parts", is(new String[]{"abc1", "abc2", "abc3"})));
    }

    @Test
    public void givenRepair_whenGetRepairsByCustomerId_thenReturnJsonRepairs() throws Exception {
        Repair repairCustomer2Employee1 = new Repair(002,001, "onderhoud", 350.0, LocalDate.now(), "Groot onderhoud en smering", new String[]{"abc1", "abc2", "abc3"});
        List<Repair> repairList = new ArrayList<>();
        repairList.add(repairCustomer2Employee1);

        given(repairRepository.findRepairsByCustomerId(002)).willReturn(repairList);

        mockMvc.perform(get("/repairs/customer/{customerId}", 002))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].customerId", is(002)))
                .andExpect(jsonPath("$[0].employeeId", is(001)))
                .andExpect(jsonPath("$[0].type", is("onderhoud")))
                .andExpect(jsonPath("$[0].price", is(350.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now())))
                .andExpect(jsonPath("$[0].description", is("Groot onderhoud en smering")))
                .andExpect(jsonPath("$[0].parts", is(new String[]{"abc1", "abc2", "abc3"})));
    }

    @Test
    public void givenRepair_whenGetRepairByCustomerIdAndEmployeeId_thenReturnJsonRepairs() throws Exception {
        Repair repairCustomer3Employee2 = new Repair(003,002, "motor", 3500.0, LocalDate.of(1996,5,30), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"});
        List<Repair> repairList = new ArrayList<>();
        repairList.add(repairCustomer3Employee2);

        given(repairRepository.findRepairsByCustomerIdAndEmployeeId(003,002)).willReturn(repairList);

        mockMvc.perform(get("/repairs/customer/{customerId}/employee/{employeeId}", 003, 002))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].customerId", is(003)))
                .andExpect(jsonPath("$[0].employeeId", is(002)))
                .andExpect(jsonPath("$[0].type", is("motor")))
                .andExpect(jsonPath("$[0].price", is(3500.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.of(1996,5,30))))
                .andExpect(jsonPath("$[0].description", is("Motor vervangen")))
                .andExpect(jsonPath("$[0].parts", is(new String[]{"abc4", "abc5", "abc7"})));
    }

    @Test
    public void givenRepair_whenGetRepairsByType_thenReturnJsonRepairs() throws Exception {
        Repair repairCustomer1Employee1 = new Repair(001,001, "onderhoud", 250.0, LocalDate.now(), "Groot onderhoud", new String[]{"abc1", "abc2"});
        Repair repairCustomer2Employee1 = new Repair(002,001, "onderhoud", 350.0, LocalDate.now(), "Groot onderhoud en smering", new String[]{"abc1", "abc2", "abc3"});
        List<Repair> repairList = new ArrayList<>();
        repairList.add(repairCustomer1Employee1);
        repairList.add(repairCustomer2Employee1);

        given(repairRepository.findRepairsByType("onderhoud")).willReturn(repairList);

        mockMvc.perform(get("/repairs/type/{type}", "onderhoud"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customerId", is(001)))
                .andExpect(jsonPath("$[0].employeeId", is(001)))
                .andExpect(jsonPath("$[0].type", is("onderhoud")))
                .andExpect(jsonPath("$[0].price", is(250.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now())))
                .andExpect(jsonPath("$[0].description", is("Groot onderhoud")))
                .andExpect(jsonPath("$[0].parts", is(new String[]{"abc1", "abc2"})))
                .andExpect(jsonPath("$[1].customerId", is(002)))
                .andExpect(jsonPath("$[1].employeeId", is(001)))
                .andExpect(jsonPath("$[1].type", is("onderhoud")))
                .andExpect(jsonPath("$[1].price", is(350.0)))
                .andExpect(jsonPath("$[1].date", is(LocalDate.now())))
                .andExpect(jsonPath("$[1].description", is("Groot onderhoud en smering")))
                .andExpect(jsonPath("$[1].parts", is(new String[]{"abc1", "abc2", "abc3"})));
    }

    @Test
    public void givenRepair_whenGetRepairsByDate_thenReturnJsonRepairs() throws Exception {
        Repair repairCustomer3Employee2 = new Repair(003,002, "motor", 3500.0, LocalDate.of(1996,5,30), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"});
        Repair repairToBeDeleted = new Repair(050,002, "motor", 3500.0, LocalDate.of(1996,5,30), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"});

        List<Repair> repairList = new ArrayList<>();
        repairList.add(repairCustomer3Employee2);
        repairList.add(repairToBeDeleted);

        mockMvc.perform(get("/repairs/date/{date}", LocalDate.of(1996,5,30)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customerId", is(003)))
                .andExpect(jsonPath("$[0].employeeId", is(002)))
                .andExpect(jsonPath("$[0].type", is("motor")))
                .andExpect(jsonPath("$[0].price", is(3500.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.of(1996,5,30))))
                .andExpect(jsonPath("$[0].description", is("Motor vervangen")))
                .andExpect(jsonPath("$[0].parts", is(new String[]{"abc4", "abc5", "abc7"})))
                .andExpect(jsonPath("$[1].customerId", is(050)))
                .andExpect(jsonPath("$[1].employeeId", is(002)))
                .andExpect(jsonPath("$[1].type", is("motor")))
                .andExpect(jsonPath("$[1].price", is(3500.0)))
                .andExpect(jsonPath("$[1].date", is(LocalDate.of(1996,5,30))))
                .andExpect(jsonPath("$[1].description", is("Motor vervangen")))
                .andExpect(jsonPath("$[1].parts", is(new String[]{"abc4", "abc5", "abc7"})));
    }

    @Test
    public void givenRepair_whenPostRepair_thenReturnJsonRepair() throws Exception {
        Repair repairCustomer4Employee2 = new Repair(004,002, "motor", 3500.0, LocalDate.of(1996,5,30), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"});

        mockMvc.perform(post("/repairs")
                .content(mapper.writeValueAsString(repairCustomer4Employee2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(004)))
                .andExpect(jsonPath("$.employeeId", is(002)))
                .andExpect(jsonPath("$.type", is("motor")))
                .andExpect(jsonPath("$.price", is(3500.0)))
                .andExpect(jsonPath("$.date", is(LocalDate.of(1996,5,30))))
                .andExpect(jsonPath("$.description", is("Motor vervangen")))
                .andExpect(jsonPath("$.parts", is(new String[]{"abc4", "abc5", "abc7"})));
    }

    @Test
    public void givenRepair_whenPutRepair_thenReturnJsonRepair() throws Exception {
        Repair repairCustomer1Employee1 = new Repair(001,001, "onderhoud", 250.0, LocalDate.now(), "Groot onderhoud", new String[]{"abc1", "abc2"});

        given(repairRepository.findRepairByCustomerIdAndDate(001, LocalDate.now())).willReturn(repairCustomer1Employee1);

        Repair updatedRepair = new Repair(001,001, "onderhoud", 275.0, LocalDate.now(), "Groot onderhoud en banden vervangen", new String[]{"abc1", "abc2", "abc3"});

        mockMvc.perform(put("/repairs")
                .content(mapper.writeValueAsString(updatedRepair))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(001)))
                .andExpect(jsonPath("$.employeeId", is(001)))
                .andExpect(jsonPath("$.type", is("onderhoud")))
                .andExpect(jsonPath("$.price", is(275.0)))
                .andExpect(jsonPath("$.date", is(LocalDate.now())))
                .andExpect(jsonPath("$.description", is("Groot onderhoud en banden vervangen")))
                .andExpect(jsonPath("$.parts", is(new String[]{"abc1", "abc2", "abc3"})));
    }

    @Test
    public void givenRepair_whenDeleteRepair_thenStatusOk() throws Exception {
        Repair repairToBeDeleted = new Repair(050,002, "motor", 3500.0, LocalDate.of(1996,5,30), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"});

        given(repairRepository.findRepairByCustomerIdAndDate(050, LocalDate.of(1996,5,30))).willReturn(repairToBeDeleted);

        mockMvc.perform(delete("/repairs/customer/{customerId}/date/{date}", 050, LocalDate.of(1996,5,30))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoRepair_whenDeleteRepair_thenStatusNotFound() throws Exception {
        given(repairRepository.findRepairByCustomerIdAndDate(100, LocalDate.of(1999, 5, 30))).willReturn(null);

        mockMvc.perform(delete("/repairs/customer/{customerId}/date/{date}", 100, LocalDate.of(1999,5,30))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }




}
