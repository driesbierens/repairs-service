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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
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
    public void givenRepair_whenGetRepairByUuid_thenReturnJsonRepair() throws Exception {
        Repair repairCustomer1Employee1 = new Repair("c001", "e001", "onderhoud", 250.0, LocalDate.now().toString(), "Groot onderhoud", new String[]{"abc1", "abc2"}, "r001");

        given(repairRepository.findRepairByRepairUuid(repairCustomer1Employee1.getRepairUuid())).willReturn(repairCustomer1Employee1);

        mockMvc.perform(get("/repairs/uuid/{uuid}", repairCustomer1Employee1.getRepairUuid()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is("c001")))
                .andExpect(jsonPath("$.employeeId", is("e001")))
                .andExpect(jsonPath("$.type", is("onderhoud")))
                .andExpect(jsonPath("$.price", is(250.0)))
                .andExpect(jsonPath("$.date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.description", is("Groot onderhoud")))
                .andExpect(jsonPath("$.parts").isArray())
                .andExpect(jsonPath("$.parts", hasSize(2)))
                .andExpect(jsonPath("$.parts", hasItem("abc1")))
                .andExpect(jsonPath("$.parts", hasItem("abc2")));
    }

    @Test
    public void givenRepair_whenGetRepairsByEmployeeId_thenReturnJsonRepairs() throws Exception {
        Repair repairCustomer1Employee1 = new Repair("c001", "e001", "onderhoud", 250.0, LocalDate.now().toString(), "Groot onderhoud", new String[]{"abc1", "abc2"}, "r001");
        Repair repairCustomer2Employee1 = new Repair("c002", "e001", "onderhoud", 350.0, LocalDate.now().toString(), "Groot onderhoud en smering", new String[]{"abc1", "abc2", "abc3"}, "r002");

        List<Repair> repairList = new ArrayList<>();
        repairList.add(repairCustomer1Employee1);
        repairList.add(repairCustomer2Employee1);

        given(repairRepository.findRepairsByEmployeeId("e001")).willReturn(repairList);

        mockMvc.perform(get("/repairs/employee/{employeeId}", "e001"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customerId", is("c001")))
                .andExpect(jsonPath("$[0].employeeId", is("e001")))
                .andExpect(jsonPath("$[0].type", is("onderhoud")))
                .andExpect(jsonPath("$[0].price", is(250.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[0].description", is("Groot onderhoud")))
                .andExpect(jsonPath("$[0].parts").isArray())
                .andExpect(jsonPath("$[0].parts", hasSize(2)))
                .andExpect(jsonPath("$[0].parts", hasItem("abc1")))
                .andExpect(jsonPath("$[0].parts", hasItem("abc2")))
                .andExpect(jsonPath("$[1].customerId", is("c002")))
                .andExpect(jsonPath("$[1].employeeId", is("e001")))
                .andExpect(jsonPath("$[1].type", is("onderhoud")))
                .andExpect(jsonPath("$[1].price", is(350.0)))
                .andExpect(jsonPath("$[1].date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[1].description", is("Groot onderhoud en smering")))
                .andExpect(jsonPath("$[1].parts").isArray())
                .andExpect(jsonPath("$[1].parts", hasSize(3)))
                .andExpect(jsonPath("$[1].parts", hasItem("abc1")))
                .andExpect(jsonPath("$[1].parts", hasItem("abc2")))
                .andExpect(jsonPath("$[1].parts", hasItem("abc3")));
    }

    @Test
    public void givenRepair_whenGetRepairsByCustomerId_thenReturnJsonRepairs() throws Exception {
        Repair repairCustomer2Employee1 = new Repair("c002", "e001", "onderhoud", 350.0, LocalDate.now().toString(), "Groot onderhoud en smering", new String[]{"abc1", "abc2", "abc3"}, "r003");
        List<Repair> repairList = new ArrayList<>();
        repairList.add(repairCustomer2Employee1);

        given(repairRepository.findRepairsByCustomerId("c002")).willReturn(repairList);

        mockMvc.perform(get("/repairs/customer/{customerId}", "c002"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].customerId", is("c002")))
                .andExpect(jsonPath("$[0].employeeId", is("e001")))
                .andExpect(jsonPath("$[0].type", is("onderhoud")))
                .andExpect(jsonPath("$[0].price", is(350.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[0].description", is("Groot onderhoud en smering")))
                .andExpect(jsonPath("$[0].parts").isArray())
                .andExpect(jsonPath("$[0].parts", hasSize(3)))
                .andExpect(jsonPath("$[0].parts", hasItem("abc1")))
                .andExpect(jsonPath("$[0].parts", hasItem("abc2")))
                .andExpect(jsonPath("$[0].parts", hasItem("abc3")));
    }

    @Test
    public void givenRepair_whenGetRepairByCustomerIdAndEmployeeId_thenReturnJsonRepairs() throws Exception {
        Repair repairCustomer3Employee2 = new Repair("c003", "e002", "motor", 3500.0, LocalDate.of(1996, 5, 30).toString(), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"}, "r004");
        List<Repair> repairList = new ArrayList<>();
        repairList.add(repairCustomer3Employee2);

        given(repairRepository.findRepairsByCustomerIdAndEmployeeId("c003", "e002")).willReturn(repairList);

        mockMvc.perform(get("/repairs/customer/{customerId}/employee/{employeeId}", "c003", "e002"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].customerId", is("c003")))
                .andExpect(jsonPath("$[0].employeeId", is("e002")))
                .andExpect(jsonPath("$[0].type", is("motor")))
                .andExpect(jsonPath("$[0].price", is(3500.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.of(1996, 5, 30).toString())))
                .andExpect(jsonPath("$[0].description", is("Motor vervangen")))
                .andExpect(jsonPath("$[0].parts").isArray())
                .andExpect(jsonPath("$[0].parts", hasSize(3)))
                .andExpect(jsonPath("$[0].parts", hasItem("abc4")))
                .andExpect(jsonPath("$[0].parts", hasItem("abc5")))
                .andExpect(jsonPath("$[0].parts", hasItem("abc7")));
    }

    @Test
    public void givenRepair_whenGetRepairsByType_thenReturnJsonRepairs() throws Exception {
        Repair repairCustomer1Employee1 = new Repair("c001", "e001", "onderhoud", 250.0, LocalDate.now().toString(), "Groot onderhoud", new String[]{"abc1", "abc2"}, "r001");
        Repair repairCustomer2Employee1 = new Repair("c002", "e001", "onderhoud", 350.0, LocalDate.now().toString(), "Groot onderhoud en smering", new String[]{"abc1", "abc2", "abc3"}, "r002");
        List<Repair> repairList = new ArrayList<>();
        repairList.add(repairCustomer1Employee1);
        repairList.add(repairCustomer2Employee1);

        given(repairRepository.findRepairsByType("onderhoud")).willReturn(repairList);

        mockMvc.perform(get("/repairs/type/{type}", "onderhoud"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customerId", is("c001")))
                .andExpect(jsonPath("$[0].employeeId", is("e001")))
                .andExpect(jsonPath("$[0].type", is("onderhoud")))
                .andExpect(jsonPath("$[0].price", is(250.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[0].description", is("Groot onderhoud")))
                .andExpect(jsonPath("$[0].parts").isArray())
                .andExpect(jsonPath("$[0].parts", hasSize(2)))
                .andExpect(jsonPath("$[0].parts", hasItem("abc1")))
                .andExpect(jsonPath("$[0].parts", hasItem("abc2")))
                .andExpect(jsonPath("$[1].customerId", is("c002")))
                .andExpect(jsonPath("$[1].employeeId", is("e001")))
                .andExpect(jsonPath("$[1].type", is("onderhoud")))
                .andExpect(jsonPath("$[1].price", is(350.0)))
                .andExpect(jsonPath("$[1].date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$[1].description", is("Groot onderhoud en smering")))
                .andExpect(jsonPath("$[1].parts").isArray())
                .andExpect(jsonPath("$[1].parts", hasSize(3)))
                .andExpect(jsonPath("$[1].parts", hasItem("abc1")))
                .andExpect(jsonPath("$[1].parts", hasItem("abc2")))
                .andExpect(jsonPath("$[1].parts", hasItem("abc3")));
    }

    @Test
    public void givenRepair_whenGetRepairsByDate_thenReturnJsonRepairs() throws Exception {
        Repair repairCustomer3Employee2 = new Repair("c003", "e002", "motor", 3500.0, LocalDate.of(1996, 5, 30).toString(), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"}, "r003");
        Repair repairToBeDeleted = new Repair("c050", "e002", "motor", 3500.0, LocalDate.of(1996, 5, 30).toString(), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"}, "r004");

        List<Repair> repairList = new ArrayList<>();
        repairList.add(repairCustomer3Employee2);
        repairList.add(repairToBeDeleted);

        given(repairRepository.findRepairsByDate(LocalDate.of(1996, 5, 30).toString())).willReturn(repairList);

        mockMvc.perform(get("/repairs/date/{date}", LocalDate.of(1996, 5, 30)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customerId", is("c003")))
                .andExpect(jsonPath("$[0].employeeId", is("e002")))
                .andExpect(jsonPath("$[0].type", is("motor")))
                .andExpect(jsonPath("$[0].price", is(3500.0)))
                .andExpect(jsonPath("$[0].date", is(LocalDate.of(1996, 5, 30).toString())))
                .andExpect(jsonPath("$[0].description", is("Motor vervangen")))
                .andExpect(jsonPath("$[0].parts").isArray())
                .andExpect(jsonPath("$[0].parts", hasSize(3)))
                .andExpect(jsonPath("$[0].parts", hasItem("abc4")))
                .andExpect(jsonPath("$[0].parts", hasItem("abc5")))
                .andExpect(jsonPath("$[0].parts", hasItem("abc7")))
                .andExpect(jsonPath("$[1].customerId", is("c050")))
                .andExpect(jsonPath("$[1].employeeId", is("e002")))
                .andExpect(jsonPath("$[1].type", is("motor")))
                .andExpect(jsonPath("$[1].price", is(3500.0)))
                .andExpect(jsonPath("$[1].date", is(LocalDate.of(1996, 5, 30).toString())))
                .andExpect(jsonPath("$[1].description", is("Motor vervangen")))
                .andExpect(jsonPath("$[1].parts").isArray())
                .andExpect(jsonPath("$[1].parts", hasSize(3)))
                .andExpect(jsonPath("$[1].parts", hasItem("abc4")))
                .andExpect(jsonPath("$[1].parts", hasItem("abc5")))
                .andExpect(jsonPath("$[1].parts", hasItem("abc7")));
    }

    @Test
    public void givenRepair_whenAddRepair_thenReturnJsonRepair() throws Exception {
        Repair repairCustomer4Employee2 = new Repair("c004", "e002", "motor", 3500.0, LocalDate.of(1996, 5, 30).toString(), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"}, "r005");

        mockMvc.perform(post("/repairs")
                .content(mapper.writeValueAsString(repairCustomer4Employee2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is("c004")))
                .andExpect(jsonPath("$.employeeId", is("e002")))
                .andExpect(jsonPath("$.type", is("motor")))
                .andExpect(jsonPath("$.price", is(3500.0)))
                .andExpect(jsonPath("$.date", is(LocalDate.of(1996, 5, 30).toString())))
                .andExpect(jsonPath("$.description", is("Motor vervangen")))
                .andExpect(jsonPath("$.parts").isArray())
                .andExpect(jsonPath("$.parts", hasSize(3)))
                .andExpect(jsonPath("$.parts", hasItem("abc4")))
                .andExpect(jsonPath("$.parts", hasItem("abc5")))
                .andExpect(jsonPath("$.parts", hasItem("abc7")));
    }

    @Test
    public void givenRepair_whenUpdatedRepair_thenReturnJsonRepair() throws Exception {
        Repair repairCustomer1Employee1 = new Repair("c001", "e001", "onderhoud", 250.0, LocalDate.now().toString(), "Groot onderhoud", new String[]{"abc1", "abc2"}, "r001");

        given(repairRepository.findRepairByRepairUuid(repairCustomer1Employee1.getRepairUuid())).willReturn(repairCustomer1Employee1);

        Repair updatedRepair = repairCustomer1Employee1;
        updatedRepair.setPrice(275.0);
        updatedRepair.setDescription("Groot onderhoud en banden vervangen");
        updatedRepair.setListParts(new String[]{"abc1", "abc2", "abc3"});

        //Repair updatedRepair = new Repair("c001", "e001", "onderhoud", 275.0, LocalDate.now().toString(), "Groot onderhoud en banden vervangen", new String[]{"abc1", "abc2", "abc3"});

        mockMvc.perform(put("/repairs")
                .content(mapper.writeValueAsString(updatedRepair))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is("c001")))
                .andExpect(jsonPath("$.employeeId", is("e001")))
                .andExpect(jsonPath("$.type", is("onderhoud")))
                .andExpect(jsonPath("$.price", is(275.0)))
                .andExpect(jsonPath("$.date", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.description", is("Groot onderhoud en banden vervangen")))
                .andExpect(jsonPath("$.parts").isArray())
                .andExpect(jsonPath("$.parts", hasSize(3)))
                .andExpect(jsonPath("$.parts", hasItem("abc1")))
                .andExpect(jsonPath("$.parts", hasItem("abc2")))
                .andExpect(jsonPath("$.parts", hasItem("abc3")));
    }

    @Test
    public void givenRepair_whenDeleteRepair_thenStatusOk() throws Exception {
        Repair repairToBeDeleted = new Repair("c050", "e002", "motor", 3500.0, LocalDate.of(1996, 5, 30).toString(), "Motor vervangen", new String[]{"abc4", "abc5", "abc7"}, "r004");

        given(repairRepository.findRepairByRepairUuid(repairToBeDeleted.getRepairUuid())).willReturn(repairToBeDeleted);

        mockMvc.perform(delete("/repairs/uuid/{uuid}", repairToBeDeleted.getRepairUuid())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoRepair_whenDeleteRepair_thenStatusNotFound() throws Exception {
        given(repairRepository.findRepairByRepairUuid("r65467698456")).willReturn(null);

        mockMvc.perform(delete("/repairs/uuid/{uuid}", "r65467698456")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
