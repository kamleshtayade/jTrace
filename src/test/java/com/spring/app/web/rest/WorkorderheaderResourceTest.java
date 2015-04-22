package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Workorderheader;
import com.spring.app.repository.WorkorderheaderRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WorkorderheaderResource REST controller.
 *
 * @see WorkorderheaderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class WorkorderheaderResourceTest {

    private static final String DEFAULT_WO_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_WO_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_KIT_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_KIT_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_CUSTOMER = "SAMPLE_TEXT";
    private static final String UPDATED_CUSTOMER = "UPDATED_TEXT";
    private static final String DEFAULT_SHIP_TO_LOC = "SAMPLE_TEXT";
    private static final String UPDATED_SHIP_TO_LOC = "UPDATED_TEXT";
    private static final String DEFAULT_PLANT = "SAMPLE_TEXT";
    private static final String UPDATED_PLANT = "UPDATED_TEXT";
    private static final String DEFAULT_PLANT_MFG_LINE = "SAMPLE_TEXT";
    private static final String UPDATED_PLANT_MFG_LINE = "UPDATED_TEXT";
    private static final String DEFAULT_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_STATUS = "UPDATED_TEXT";

    private static final Integer DEFAULT_QTY = 0;
    private static final Integer UPDATED_QTY = 1;
    private static final String DEFAULT_SO_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_SO_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_ITEM = "SAMPLE_TEXT";
    private static final String UPDATED_ITEM = "UPDATED_TEXT";
    private static final String DEFAULT_ASY_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_ASY_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_BOM = "SAMPLE_TEXT";
    private static final String UPDATED_BOM = "UPDATED_TEXT";

    @Inject
    private WorkorderheaderRepository workorderheaderRepository;

    private MockMvc restWorkorderheaderMockMvc;

    private Workorderheader workorderheader;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkorderheaderResource workorderheaderResource = new WorkorderheaderResource();
        ReflectionTestUtils.setField(workorderheaderResource, "workorderheaderRepository", workorderheaderRepository);
        this.restWorkorderheaderMockMvc = MockMvcBuilders.standaloneSetup(workorderheaderResource).build();
    }

    @Before
    public void initTest() {
        workorderheader = new Workorderheader();
        workorderheader.setWoNumber(DEFAULT_WO_NUMBER);
        workorderheader.setKitNumber(DEFAULT_KIT_NUMBER);
        workorderheader.setCustomer(DEFAULT_CUSTOMER);
        workorderheader.setShipToLoc(DEFAULT_SHIP_TO_LOC);
        workorderheader.setPlant(DEFAULT_PLANT);
        workorderheader.setPlantMfgLine(DEFAULT_PLANT_MFG_LINE);
        workorderheader.setStatus(DEFAULT_STATUS);
        workorderheader.setQty(DEFAULT_QTY);
        workorderheader.setSoNumber(DEFAULT_SO_NUMBER);
        workorderheader.setItem(DEFAULT_ITEM);
        workorderheader.setAsyCode(DEFAULT_ASY_CODE);
        workorderheader.setBom(DEFAULT_BOM);
    }

    @Test
    @Transactional
    public void createWorkorderheader() throws Exception {
        // Validate the database is empty
        assertThat(workorderheaderRepository.findAll()).hasSize(0);

        // Create the Workorderheader
        restWorkorderheaderMockMvc.perform(post("/api/workorderheaders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(workorderheader)))
                .andExpect(status().isOk());

        // Validate the Workorderheader in the database
        List<Workorderheader> workorderheaders = workorderheaderRepository.findAll();
        assertThat(workorderheaders).hasSize(1);
        Workorderheader testWorkorderheader = workorderheaders.iterator().next();
        assertThat(testWorkorderheader.getWoNumber()).isEqualTo(DEFAULT_WO_NUMBER);
        assertThat(testWorkorderheader.getKitNumber()).isEqualTo(DEFAULT_KIT_NUMBER);
        assertThat(testWorkorderheader.getCustomer()).isEqualTo(DEFAULT_CUSTOMER);
        assertThat(testWorkorderheader.getShipToLoc()).isEqualTo(DEFAULT_SHIP_TO_LOC);
        assertThat(testWorkorderheader.getPlant()).isEqualTo(DEFAULT_PLANT);
        assertThat(testWorkorderheader.getPlantMfgLine()).isEqualTo(DEFAULT_PLANT_MFG_LINE);
        assertThat(testWorkorderheader.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkorderheader.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testWorkorderheader.getSoNumber()).isEqualTo(DEFAULT_SO_NUMBER);
        assertThat(testWorkorderheader.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testWorkorderheader.getAsyCode()).isEqualTo(DEFAULT_ASY_CODE);
        assertThat(testWorkorderheader.getBom()).isEqualTo(DEFAULT_BOM);
    }

    @Test
    @Transactional
    public void getAllWorkorderheaders() throws Exception {
        // Initialize the database
        workorderheaderRepository.saveAndFlush(workorderheader);

        // Get all the workorderheaders
        restWorkorderheaderMockMvc.perform(get("/api/workorderheaders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(workorderheader.getId().intValue()))
                .andExpect(jsonPath("$.[0].woNumber").value(DEFAULT_WO_NUMBER.toString()))
                .andExpect(jsonPath("$.[0].kitNumber").value(DEFAULT_KIT_NUMBER.toString()))
                .andExpect(jsonPath("$.[0].customer").value(DEFAULT_CUSTOMER.toString()))
                .andExpect(jsonPath("$.[0].shipToLoc").value(DEFAULT_SHIP_TO_LOC.toString()))
                .andExpect(jsonPath("$.[0].plant").value(DEFAULT_PLANT.toString()))
                .andExpect(jsonPath("$.[0].plantMfgLine").value(DEFAULT_PLANT_MFG_LINE.toString()))
                .andExpect(jsonPath("$.[0].status").value(DEFAULT_STATUS.toString()))
                .andExpect(jsonPath("$.[0].qty").value(DEFAULT_QTY))
                .andExpect(jsonPath("$.[0].soNumber").value(DEFAULT_SO_NUMBER.toString()))
                .andExpect(jsonPath("$.[0].item").value(DEFAULT_ITEM.toString()))
                .andExpect(jsonPath("$.[0].asyCode").value(DEFAULT_ASY_CODE.toString()))
                .andExpect(jsonPath("$.[0].bom").value(DEFAULT_BOM.toString()));
    }

    @Test
    @Transactional
    public void getWorkorderheader() throws Exception {
        // Initialize the database
        workorderheaderRepository.saveAndFlush(workorderheader);

        // Get the workorderheader
        restWorkorderheaderMockMvc.perform(get("/api/workorderheaders/{id}", workorderheader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(workorderheader.getId().intValue()))
            .andExpect(jsonPath("$.woNumber").value(DEFAULT_WO_NUMBER.toString()))
            .andExpect(jsonPath("$.kitNumber").value(DEFAULT_KIT_NUMBER.toString()))
            .andExpect(jsonPath("$.customer").value(DEFAULT_CUSTOMER.toString()))
            .andExpect(jsonPath("$.shipToLoc").value(DEFAULT_SHIP_TO_LOC.toString()))
            .andExpect(jsonPath("$.plant").value(DEFAULT_PLANT.toString()))
            .andExpect(jsonPath("$.plantMfgLine").value(DEFAULT_PLANT_MFG_LINE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.soNumber").value(DEFAULT_SO_NUMBER.toString()))
            .andExpect(jsonPath("$.item").value(DEFAULT_ITEM.toString()))
            .andExpect(jsonPath("$.asyCode").value(DEFAULT_ASY_CODE.toString()))
            .andExpect(jsonPath("$.bom").value(DEFAULT_BOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkorderheader() throws Exception {
        // Get the workorderheader
        restWorkorderheaderMockMvc.perform(get("/api/workorderheaders/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkorderheader() throws Exception {
        // Initialize the database
        workorderheaderRepository.saveAndFlush(workorderheader);

        // Update the workorderheader
        workorderheader.setWoNumber(UPDATED_WO_NUMBER);
        workorderheader.setKitNumber(UPDATED_KIT_NUMBER);
        workorderheader.setCustomer(UPDATED_CUSTOMER);
        workorderheader.setShipToLoc(UPDATED_SHIP_TO_LOC);
        workorderheader.setPlant(UPDATED_PLANT);
        workorderheader.setPlantMfgLine(UPDATED_PLANT_MFG_LINE);
        workorderheader.setStatus(UPDATED_STATUS);
        workorderheader.setQty(UPDATED_QTY);
        workorderheader.setSoNumber(UPDATED_SO_NUMBER);
        workorderheader.setItem(UPDATED_ITEM);
        workorderheader.setAsyCode(UPDATED_ASY_CODE);
        workorderheader.setBom(UPDATED_BOM);
        restWorkorderheaderMockMvc.perform(post("/api/workorderheaders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(workorderheader)))
                .andExpect(status().isOk());

        // Validate the Workorderheader in the database
        List<Workorderheader> workorderheaders = workorderheaderRepository.findAll();
        assertThat(workorderheaders).hasSize(1);
        Workorderheader testWorkorderheader = workorderheaders.iterator().next();
        assertThat(testWorkorderheader.getWoNumber()).isEqualTo(UPDATED_WO_NUMBER);
        assertThat(testWorkorderheader.getKitNumber()).isEqualTo(UPDATED_KIT_NUMBER);
        assertThat(testWorkorderheader.getCustomer()).isEqualTo(UPDATED_CUSTOMER);
        assertThat(testWorkorderheader.getShipToLoc()).isEqualTo(UPDATED_SHIP_TO_LOC);
        assertThat(testWorkorderheader.getPlant()).isEqualTo(UPDATED_PLANT);
        assertThat(testWorkorderheader.getPlantMfgLine()).isEqualTo(UPDATED_PLANT_MFG_LINE);
        assertThat(testWorkorderheader.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkorderheader.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testWorkorderheader.getSoNumber()).isEqualTo(UPDATED_SO_NUMBER);
        assertThat(testWorkorderheader.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testWorkorderheader.getAsyCode()).isEqualTo(UPDATED_ASY_CODE);
        assertThat(testWorkorderheader.getBom()).isEqualTo(UPDATED_BOM);
    }

    @Test
    @Transactional
    public void deleteWorkorderheader() throws Exception {
        // Initialize the database
        workorderheaderRepository.saveAndFlush(workorderheader);

        // Get the workorderheader
        restWorkorderheaderMockMvc.perform(delete("/api/workorderheaders/{id}", workorderheader.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Workorderheader> workorderheaders = workorderheaderRepository.findAll();
        assertThat(workorderheaders).hasSize(0);
    }
}
