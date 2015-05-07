package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Workorderheader;
import com.spring.app.repository.WorkorderheaderRepository;

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
    private static final String DEFAULT_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_STATUS = "UPDATED_TEXT";

    private static final Integer DEFAULT_QTY = 0;
    private static final Integer UPDATED_QTY = 1;

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
        workorderheader.setStatus(DEFAULT_STATUS);
        workorderheader.setQty(DEFAULT_QTY);
    }

    @Test
    @Transactional
    public void createWorkorderheader() throws Exception {
        int databaseSizeBeforeCreate = workorderheaderRepository.findAll().size();

        // Create the Workorderheader
        restWorkorderheaderMockMvc.perform(post("/api/workorderheaders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(workorderheader)))
                .andExpect(status().isCreated());

        // Validate the Workorderheader in the database
        List<Workorderheader> workorderheaders = workorderheaderRepository.findAll();
        assertThat(workorderheaders).hasSize(databaseSizeBeforeCreate + 1);
        Workorderheader testWorkorderheader = workorderheaders.get(workorderheaders.size() - 1);
        assertThat(testWorkorderheader.getWoNumber()).isEqualTo(DEFAULT_WO_NUMBER);
        assertThat(testWorkorderheader.getKitNumber()).isEqualTo(DEFAULT_KIT_NUMBER);
        assertThat(testWorkorderheader.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkorderheader.getQty()).isEqualTo(DEFAULT_QTY);
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
                .andExpect(jsonPath("$.[*].id").value(hasItem(workorderheader.getId().intValue())))
                .andExpect(jsonPath("$.[*].woNumber").value(hasItem(DEFAULT_WO_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].kitNumber").value(hasItem(DEFAULT_KIT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)));
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
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY));
    }

    @Test
    @Transactional
    public void getNonExistingWorkorderheader() throws Exception {
        // Get the workorderheader
        restWorkorderheaderMockMvc.perform(get("/api/workorderheaders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkorderheader() throws Exception {
        // Initialize the database
        workorderheaderRepository.saveAndFlush(workorderheader);

		int databaseSizeBeforeUpdate = workorderheaderRepository.findAll().size();

        // Update the workorderheader
        workorderheader.setWoNumber(UPDATED_WO_NUMBER);
        workorderheader.setKitNumber(UPDATED_KIT_NUMBER);
        workorderheader.setStatus(UPDATED_STATUS);
        workorderheader.setQty(UPDATED_QTY);
        restWorkorderheaderMockMvc.perform(put("/api/workorderheaders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(workorderheader)))
                .andExpect(status().isOk());

        // Validate the Workorderheader in the database
        List<Workorderheader> workorderheaders = workorderheaderRepository.findAll();
        assertThat(workorderheaders).hasSize(databaseSizeBeforeUpdate);
        Workorderheader testWorkorderheader = workorderheaders.get(workorderheaders.size() - 1);
        assertThat(testWorkorderheader.getWoNumber()).isEqualTo(UPDATED_WO_NUMBER);
        assertThat(testWorkorderheader.getKitNumber()).isEqualTo(UPDATED_KIT_NUMBER);
        assertThat(testWorkorderheader.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkorderheader.getQty()).isEqualTo(UPDATED_QTY);
    }

    @Test
    @Transactional
    public void deleteWorkorderheader() throws Exception {
        // Initialize the database
        workorderheaderRepository.saveAndFlush(workorderheader);

		int databaseSizeBeforeDelete = workorderheaderRepository.findAll().size();

        // Get the workorderheader
        restWorkorderheaderMockMvc.perform(delete("/api/workorderheaders/{id}", workorderheader.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Workorderheader> workorderheaders = workorderheaderRepository.findAll();
        assertThat(workorderheaders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
