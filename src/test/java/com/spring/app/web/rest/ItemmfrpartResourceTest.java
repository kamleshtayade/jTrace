package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Itemmfrpart;
import com.spring.app.repository.ItemmfrpartRepository;

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
 * Test class for the ItemmfrpartResource REST controller.
 *
 * @see ItemmfrpartResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ItemmfrpartResourceTest {

    private static final String DEFAULT_MFRPART = "SAMPLE_TEXT";
    private static final String UPDATED_MFRPART = "UPDATED_TEXT";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;
    private static final String DEFAULT_SUPPLIER = "SAMPLE_TEXT";
    private static final String UPDATED_SUPPLIER = "UPDATED_TEXT";
    private static final String DEFAULT_REMARK = "SAMPLE_TEXT";
    private static final String UPDATED_REMARK = "UPDATED_TEXT";

    @Inject
    private ItemmfrpartRepository itemmfrpartRepository;

    private MockMvc restItemmfrpartMockMvc;

    private Itemmfrpart itemmfrpart;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemmfrpartResource itemmfrpartResource = new ItemmfrpartResource();
        ReflectionTestUtils.setField(itemmfrpartResource, "itemmfrpartRepository", itemmfrpartRepository);
        this.restItemmfrpartMockMvc = MockMvcBuilders.standaloneSetup(itemmfrpartResource).build();
    }

    @Before
    public void initTest() {
        itemmfrpart = new Itemmfrpart();
        itemmfrpart.setMfrpart(DEFAULT_MFRPART);
        itemmfrpart.setStatus(DEFAULT_STATUS);
        itemmfrpart.setSupplier(DEFAULT_SUPPLIER);
        itemmfrpart.setRemark(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createItemmfrpart() throws Exception {
        int databaseSizeBeforeCreate = itemmfrpartRepository.findAll().size();

        // Create the Itemmfrpart
        restItemmfrpartMockMvc.perform(post("/api/itemmfrparts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemmfrpart)))
                .andExpect(status().isCreated());

        // Validate the Itemmfrpart in the database
        List<Itemmfrpart> itemmfrparts = itemmfrpartRepository.findAll();
        assertThat(itemmfrparts).hasSize(databaseSizeBeforeCreate + 1);
        Itemmfrpart testItemmfrpart = itemmfrparts.get(itemmfrparts.size() - 1);
        assertThat(testItemmfrpart.getMfrpart()).isEqualTo(DEFAULT_MFRPART);
        assertThat(testItemmfrpart.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testItemmfrpart.getSupplier()).isEqualTo(DEFAULT_SUPPLIER);
        assertThat(testItemmfrpart.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void checkMfrpartIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(itemmfrpartRepository.findAll()).hasSize(0);
        // set the field null
        itemmfrpart.setMfrpart(null);

        // Create the Itemmfrpart, which fails.
        restItemmfrpartMockMvc.perform(post("/api/itemmfrparts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemmfrpart)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Itemmfrpart> itemmfrparts = itemmfrpartRepository.findAll();
        assertThat(itemmfrparts).hasSize(0);
    }

    @Test
    @Transactional
    public void checkRemarkIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(itemmfrpartRepository.findAll()).hasSize(0);
        // set the field null
        itemmfrpart.setRemark(null);

        // Create the Itemmfrpart, which fails.
        restItemmfrpartMockMvc.perform(post("/api/itemmfrparts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemmfrpart)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Itemmfrpart> itemmfrparts = itemmfrpartRepository.findAll();
        assertThat(itemmfrparts).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllItemmfrparts() throws Exception {
        // Initialize the database
        itemmfrpartRepository.saveAndFlush(itemmfrpart);

        // Get all the itemmfrparts
        restItemmfrpartMockMvc.perform(get("/api/itemmfrparts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(itemmfrpart.getId().intValue())))
                .andExpect(jsonPath("$.[*].mfrpart").value(hasItem(DEFAULT_MFRPART.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].supplier").value(hasItem(DEFAULT_SUPPLIER.toString())))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getItemmfrpart() throws Exception {
        // Initialize the database
        itemmfrpartRepository.saveAndFlush(itemmfrpart);

        // Get the itemmfrpart
        restItemmfrpartMockMvc.perform(get("/api/itemmfrparts/{id}", itemmfrpart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(itemmfrpart.getId().intValue()))
            .andExpect(jsonPath("$.mfrpart").value(DEFAULT_MFRPART.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.supplier").value(DEFAULT_SUPPLIER.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingItemmfrpart() throws Exception {
        // Get the itemmfrpart
        restItemmfrpartMockMvc.perform(get("/api/itemmfrparts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemmfrpart() throws Exception {
        // Initialize the database
        itemmfrpartRepository.saveAndFlush(itemmfrpart);

		int databaseSizeBeforeUpdate = itemmfrpartRepository.findAll().size();

        // Update the itemmfrpart
        itemmfrpart.setMfrpart(UPDATED_MFRPART);
        itemmfrpart.setStatus(UPDATED_STATUS);
        itemmfrpart.setSupplier(UPDATED_SUPPLIER);
        itemmfrpart.setRemark(UPDATED_REMARK);
        restItemmfrpartMockMvc.perform(put("/api/itemmfrparts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemmfrpart)))
                .andExpect(status().isOk());

        // Validate the Itemmfrpart in the database
        List<Itemmfrpart> itemmfrparts = itemmfrpartRepository.findAll();
        assertThat(itemmfrparts).hasSize(databaseSizeBeforeUpdate);
        Itemmfrpart testItemmfrpart = itemmfrparts.get(itemmfrparts.size() - 1);
        assertThat(testItemmfrpart.getMfrpart()).isEqualTo(UPDATED_MFRPART);
        assertThat(testItemmfrpart.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testItemmfrpart.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
        assertThat(testItemmfrpart.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void deleteItemmfrpart() throws Exception {
        // Initialize the database
        itemmfrpartRepository.saveAndFlush(itemmfrpart);

		int databaseSizeBeforeDelete = itemmfrpartRepository.findAll().size();

        // Get the itemmfrpart
        restItemmfrpartMockMvc.perform(delete("/api/itemmfrparts/{id}", itemmfrpart.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Itemmfrpart> itemmfrparts = itemmfrpartRepository.findAll();
        assertThat(itemmfrparts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
