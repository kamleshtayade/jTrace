package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Workorderline;
import com.spring.app.repository.WorkorderlineRepository;

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
 * Test class for the WorkorderlineResource REST controller.
 *
 * @see WorkorderlineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class WorkorderlineResourceTest {

    private static final String DEFAULT_WORKORDERHEADER = "SAMPLE_TEXT";
    private static final String UPDATED_WORKORDERHEADER = "UPDATED_TEXT";
    private static final String DEFAULT_BOM_CHILD_ITEM = "SAMPLE_TEXT";
    private static final String UPDATED_BOM_CHILD_ITEM = "UPDATED_TEXT";
    private static final String DEFAULT_ATTRITION = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRITION = "UPDATED_TEXT";
    private static final String DEFAULT_REQU_QTY = "SAMPLE_TEXT";
    private static final String UPDATED_REQU_QTY = "UPDATED_TEXT";

    private static final Integer DEFAULT_ISSUED_QTY = 0;
    private static final Integer UPDATED_ISSUED_QTY = 1;

    private static final Boolean DEFAULT_IS_CUST_SUPPLIED = false;
    private static final Boolean UPDATED_IS_CUST_SUPPLIED = true;
    private static final String DEFAULT_ITEM_CTN = "SAMPLE_TEXT";
    private static final String UPDATED_ITEM_CTN = "UPDATED_TEXT";
    private static final String DEFAULT_REMARKS = "SAMPLE_TEXT";
    private static final String UPDATED_REMARKS = "UPDATED_TEXT";

    @Inject
    private WorkorderlineRepository workorderlineRepository;

    private MockMvc restWorkorderlineMockMvc;

    private Workorderline workorderline;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkorderlineResource workorderlineResource = new WorkorderlineResource();
        ReflectionTestUtils.setField(workorderlineResource, "workorderlineRepository", workorderlineRepository);
        this.restWorkorderlineMockMvc = MockMvcBuilders.standaloneSetup(workorderlineResource).build();
    }

    @Before
    public void initTest() {
        workorderline = new Workorderline();
        workorderline.setWorkorderheader(DEFAULT_WORKORDERHEADER);
        workorderline.setBomChildItem(DEFAULT_BOM_CHILD_ITEM);
        workorderline.setAttrition(DEFAULT_ATTRITION);
        workorderline.setRequQty(DEFAULT_REQU_QTY);
        workorderline.setIssuedQty(DEFAULT_ISSUED_QTY);
        workorderline.setIsCustSupplied(DEFAULT_IS_CUST_SUPPLIED);
        workorderline.setItemCtn(DEFAULT_ITEM_CTN);
        workorderline.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createWorkorderline() throws Exception {
        // Validate the database is empty
        assertThat(workorderlineRepository.findAll()).hasSize(0);

        // Create the Workorderline
        restWorkorderlineMockMvc.perform(post("/api/workorderlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(workorderline)))
                .andExpect(status().isOk());

        // Validate the Workorderline in the database
        List<Workorderline> workorderlines = workorderlineRepository.findAll();
        assertThat(workorderlines).hasSize(1);
        Workorderline testWorkorderline = workorderlines.iterator().next();
        assertThat(testWorkorderline.getWorkorderheader()).isEqualTo(DEFAULT_WORKORDERHEADER);
        assertThat(testWorkorderline.getBomChildItem()).isEqualTo(DEFAULT_BOM_CHILD_ITEM);
        assertThat(testWorkorderline.getAttrition()).isEqualTo(DEFAULT_ATTRITION);
        assertThat(testWorkorderline.getRequQty()).isEqualTo(DEFAULT_REQU_QTY);
        assertThat(testWorkorderline.getIssuedQty()).isEqualTo(DEFAULT_ISSUED_QTY);
        assertThat(testWorkorderline.getIsCustSupplied()).isEqualTo(DEFAULT_IS_CUST_SUPPLIED);
        assertThat(testWorkorderline.getItemCtn()).isEqualTo(DEFAULT_ITEM_CTN);
        assertThat(testWorkorderline.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void getAllWorkorderlines() throws Exception {
        // Initialize the database
        workorderlineRepository.saveAndFlush(workorderline);

        // Get all the workorderlines
        restWorkorderlineMockMvc.perform(get("/api/workorderlines"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(workorderline.getId().intValue()))
                .andExpect(jsonPath("$.[0].workorderheader").value(DEFAULT_WORKORDERHEADER.toString()))
                .andExpect(jsonPath("$.[0].bomChildItem").value(DEFAULT_BOM_CHILD_ITEM.toString()))
                .andExpect(jsonPath("$.[0].attrition").value(DEFAULT_ATTRITION.toString()))
                .andExpect(jsonPath("$.[0].requQty").value(DEFAULT_REQU_QTY.toString()))
                .andExpect(jsonPath("$.[0].issuedQty").value(DEFAULT_ISSUED_QTY))
                .andExpect(jsonPath("$.[0].isCustSupplied").value(DEFAULT_IS_CUST_SUPPLIED.booleanValue()))
                .andExpect(jsonPath("$.[0].itemCtn").value(DEFAULT_ITEM_CTN.toString()))
                .andExpect(jsonPath("$.[0].remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getWorkorderline() throws Exception {
        // Initialize the database
        workorderlineRepository.saveAndFlush(workorderline);

        // Get the workorderline
        restWorkorderlineMockMvc.perform(get("/api/workorderlines/{id}", workorderline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(workorderline.getId().intValue()))
            .andExpect(jsonPath("$.workorderheader").value(DEFAULT_WORKORDERHEADER.toString()))
            .andExpect(jsonPath("$.bomChildItem").value(DEFAULT_BOM_CHILD_ITEM.toString()))
            .andExpect(jsonPath("$.attrition").value(DEFAULT_ATTRITION.toString()))
            .andExpect(jsonPath("$.requQty").value(DEFAULT_REQU_QTY.toString()))
            .andExpect(jsonPath("$.issuedQty").value(DEFAULT_ISSUED_QTY))
            .andExpect(jsonPath("$.isCustSupplied").value(DEFAULT_IS_CUST_SUPPLIED.booleanValue()))
            .andExpect(jsonPath("$.itemCtn").value(DEFAULT_ITEM_CTN.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkorderline() throws Exception {
        // Get the workorderline
        restWorkorderlineMockMvc.perform(get("/api/workorderlines/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkorderline() throws Exception {
        // Initialize the database
        workorderlineRepository.saveAndFlush(workorderline);

        // Update the workorderline
        workorderline.setWorkorderheader(UPDATED_WORKORDERHEADER);
        workorderline.setBomChildItem(UPDATED_BOM_CHILD_ITEM);
        workorderline.setAttrition(UPDATED_ATTRITION);
        workorderline.setRequQty(UPDATED_REQU_QTY);
        workorderline.setIssuedQty(UPDATED_ISSUED_QTY);
        workorderline.setIsCustSupplied(UPDATED_IS_CUST_SUPPLIED);
        workorderline.setItemCtn(UPDATED_ITEM_CTN);
        workorderline.setRemarks(UPDATED_REMARKS);
        restWorkorderlineMockMvc.perform(post("/api/workorderlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(workorderline)))
                .andExpect(status().isOk());

        // Validate the Workorderline in the database
        List<Workorderline> workorderlines = workorderlineRepository.findAll();
        assertThat(workorderlines).hasSize(1);
        Workorderline testWorkorderline = workorderlines.iterator().next();
        assertThat(testWorkorderline.getWorkorderheader()).isEqualTo(UPDATED_WORKORDERHEADER);
        assertThat(testWorkorderline.getBomChildItem()).isEqualTo(UPDATED_BOM_CHILD_ITEM);
        assertThat(testWorkorderline.getAttrition()).isEqualTo(UPDATED_ATTRITION);
        assertThat(testWorkorderline.getRequQty()).isEqualTo(UPDATED_REQU_QTY);
        assertThat(testWorkorderline.getIssuedQty()).isEqualTo(UPDATED_ISSUED_QTY);
        assertThat(testWorkorderline.getIsCustSupplied()).isEqualTo(UPDATED_IS_CUST_SUPPLIED);
        assertThat(testWorkorderline.getItemCtn()).isEqualTo(UPDATED_ITEM_CTN);
        assertThat(testWorkorderline.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteWorkorderline() throws Exception {
        // Initialize the database
        workorderlineRepository.saveAndFlush(workorderline);

        // Get the workorderline
        restWorkorderlineMockMvc.perform(delete("/api/workorderlines/{id}", workorderline.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Workorderline> workorderlines = workorderlineRepository.findAll();
        assertThat(workorderlines).hasSize(0);
    }
}
