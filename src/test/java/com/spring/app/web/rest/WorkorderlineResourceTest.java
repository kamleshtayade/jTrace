package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Workorderline;
import com.spring.app.repository.WorkorderlineRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
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

    private static final String DEFAULT_ATTRITION = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRITION = "UPDATED_TEXT";

    private static final Integer DEFAULT_REQU_QTY = 0;
    private static final Integer UPDATED_REQU_QTY = 1;

    private static final Integer DEFAULT_ISSUED_QTY = 0;
    private static final Integer UPDATED_ISSUED_QTY = 1;
    
    private static final Integer DEFAULT_BOM_QTY = 0;
    private static final Integer UPDATED_BOM_QTY = 1;

    private static final Boolean DEFAULT_IS_CUST_SUPPLIED = false;
    private static final Boolean UPDATED_IS_CUST_SUPPLIED = true;
    private static final String DEFAULT_REMARK = "SAMPLE_TEXT";
    private static final String UPDATED_REMARK = "UPDATED_TEXT";

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
        workorderline.setAttrition(DEFAULT_ATTRITION);
        workorderline.setBomQty(DEFAULT_BOM_QTY);
        workorderline.setRequQty(DEFAULT_REQU_QTY);
        workorderline.setIssuedQty(DEFAULT_ISSUED_QTY);
        workorderline.setIsCustSupplied(DEFAULT_IS_CUST_SUPPLIED);
        workorderline.setRemark(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createWorkorderline() throws Exception {
        int databaseSizeBeforeCreate = workorderlineRepository.findAll().size();

        // Create the Workorderline
        restWorkorderlineMockMvc.perform(post("/api/workorderlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(workorderline)))
                .andExpect(status().isCreated());

        // Validate the Workorderline in the database
        List<Workorderline> workorderlines = workorderlineRepository.findAll();
        assertThat(workorderlines).hasSize(databaseSizeBeforeCreate + 1);
        Workorderline testWorkorderline = workorderlines.get(workorderlines.size() - 1);
        assertThat(testWorkorderline.getBomQty()).isEqualTo(DEFAULT_BOM_QTY);
        assertThat(testWorkorderline.getAttrition()).isEqualTo(DEFAULT_ATTRITION);
        assertThat(testWorkorderline.getRequQty()).isEqualTo(DEFAULT_REQU_QTY);
        assertThat(testWorkorderline.getIssuedQty()).isEqualTo(DEFAULT_ISSUED_QTY);
        assertThat(testWorkorderline.getIsCustSupplied()).isEqualTo(DEFAULT_IS_CUST_SUPPLIED);
        assertThat(testWorkorderline.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

/*    @Test
    @Transactional
    public void checkBomChildItemIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(workorderlineRepository.findAll()).hasSize(0);
        // set the field null
        workorderline.setBomChildItem(null);

        // Create the Workorderline, which fails.
        restWorkorderlineMockMvc.perform(post("/api/workorderlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(workorderline)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Workorderline> workorderlines = workorderlineRepository.findAll();
        assertThat(workorderlines).hasSize(0);
    }*/

    @Test
    @Transactional
    public void getAllWorkorderlines() throws Exception {
        // Initialize the database
        workorderlineRepository.saveAndFlush(workorderline);

        // Get all the workorderlines
        restWorkorderlineMockMvc.perform(get("/api/workorderlines"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(workorderline.getId().intValue())))
                .andExpect(jsonPath("$.[*].bomQty").value(hasItem(DEFAULT_BOM_QTY)))
                .andExpect(jsonPath("$.[*].attrition").value(hasItem(DEFAULT_ATTRITION.toString())))
                .andExpect(jsonPath("$.[*].requQty").value(hasItem(DEFAULT_REQU_QTY)))
                .andExpect(jsonPath("$.[*].issuedQty").value(hasItem(DEFAULT_ISSUED_QTY)))
                .andExpect(jsonPath("$.[*].isCustSupplied").value(hasItem(DEFAULT_IS_CUST_SUPPLIED.booleanValue())))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
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
            .andExpect(jsonPath("$.bomQty").value(DEFAULT_BOM_QTY))
            .andExpect(jsonPath("$.attrition").value(DEFAULT_ATTRITION.toString()))
            .andExpect(jsonPath("$.requQty").value(DEFAULT_REQU_QTY))
            .andExpect(jsonPath("$.issuedQty").value(DEFAULT_ISSUED_QTY))
            .andExpect(jsonPath("$.isCustSupplied").value(DEFAULT_IS_CUST_SUPPLIED.booleanValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkorderline() throws Exception {
        // Get the workorderline
        restWorkorderlineMockMvc.perform(get("/api/workorderlines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkorderline() throws Exception {
        // Initialize the database
        workorderlineRepository.saveAndFlush(workorderline);

		int databaseSizeBeforeUpdate = workorderlineRepository.findAll().size();

        // Update the workorderline
        workorderline.setBomQty(UPDATED_BOM_QTY);
        workorderline.setAttrition(UPDATED_ATTRITION);
        workorderline.setRequQty(UPDATED_REQU_QTY);
        workorderline.setIssuedQty(UPDATED_ISSUED_QTY);
        workorderline.setIsCustSupplied(UPDATED_IS_CUST_SUPPLIED);
        workorderline.setRemark(UPDATED_REMARK);
        restWorkorderlineMockMvc.perform(put("/api/workorderlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(workorderline)))
                .andExpect(status().isOk());

        // Validate the Workorderline in the database
        List<Workorderline> workorderlines = workorderlineRepository.findAll();
        assertThat(workorderlines).hasSize(databaseSizeBeforeUpdate);
        Workorderline testWorkorderline = workorderlines.get(workorderlines.size() - 1);
        assertThat(testWorkorderline.getBomQty()).isEqualTo(UPDATED_BOM_QTY);
        assertThat(testWorkorderline.getAttrition()).isEqualTo(UPDATED_ATTRITION);
        assertThat(testWorkorderline.getRequQty()).isEqualTo(UPDATED_REQU_QTY);
        assertThat(testWorkorderline.getIssuedQty()).isEqualTo(UPDATED_ISSUED_QTY);
        assertThat(testWorkorderline.getIsCustSupplied()).isEqualTo(UPDATED_IS_CUST_SUPPLIED);
        assertThat(testWorkorderline.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void deleteWorkorderline() throws Exception {
        // Initialize the database
        workorderlineRepository.saveAndFlush(workorderline);

		int databaseSizeBeforeDelete = workorderlineRepository.findAll().size();

        // Get the workorderline
        restWorkorderlineMockMvc.perform(delete("/api/workorderlines/{id}", workorderline.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Workorderline> workorderlines = workorderlineRepository.findAll();
        assertThat(workorderlines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
