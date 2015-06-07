package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Isupplier;
import com.spring.app.repository.IsupplierRepository;

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
 * Test class for the IsupplierResource REST controller.
 *
 * @see IsupplierResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class IsupplierResourceTest {

    private static final String DEFAULT_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_CODE = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ISENABLED = false;
    private static final Boolean UPDATED_ISENABLED = true;
    private static final String DEFAULT_REMARK = "SAMPLE_TEXT";
    private static final String UPDATED_REMARK = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";

    @Inject
    private IsupplierRepository isupplierRepository;

    private MockMvc restIsupplierMockMvc;

    private Isupplier isupplier;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IsupplierResource isupplierResource = new IsupplierResource();
        ReflectionTestUtils.setField(isupplierResource, "isupplierRepository", isupplierRepository);
        this.restIsupplierMockMvc = MockMvcBuilders.standaloneSetup(isupplierResource).build();
    }

    @Before
    public void initTest() {
        isupplier = new Isupplier();
        isupplier.setCode(DEFAULT_CODE);
        isupplier.setIsenabled(DEFAULT_ISENABLED);
        isupplier.setRemark(DEFAULT_REMARK);
        isupplier.setAddress(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createIsupplier() throws Exception {
        int databaseSizeBeforeCreate = isupplierRepository.findAll().size();

        // Create the Isupplier
        restIsupplierMockMvc.perform(post("/api/isuppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(isupplier)))
                .andExpect(status().isCreated());

        // Validate the Isupplier in the database
        List<Isupplier> isuppliers = isupplierRepository.findAll();
        assertThat(isuppliers).hasSize(databaseSizeBeforeCreate + 1);
        Isupplier testIsupplier = isuppliers.get(isuppliers.size() - 1);
        assertThat(testIsupplier.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testIsupplier.getIsenabled()).isEqualTo(DEFAULT_ISENABLED);
        assertThat(testIsupplier.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testIsupplier.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(isupplierRepository.findAll()).hasSize(0);
        // set the field null
        isupplier.setCode(null);

        // Create the Isupplier, which fails.
        restIsupplierMockMvc.perform(post("/api/isuppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(isupplier)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Isupplier> isuppliers = isupplierRepository.findAll();
        assertThat(isuppliers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkRemarkIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(isupplierRepository.findAll()).hasSize(0);
        // set the field null
        isupplier.setRemark(null);

        // Create the Isupplier, which fails.
        restIsupplierMockMvc.perform(post("/api/isuppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(isupplier)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Isupplier> isuppliers = isupplierRepository.findAll();
        assertThat(isuppliers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(isupplierRepository.findAll()).hasSize(0);
        // set the field null
        isupplier.setAddress(null);

        // Create the Isupplier, which fails.
        restIsupplierMockMvc.perform(post("/api/isuppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(isupplier)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Isupplier> isuppliers = isupplierRepository.findAll();
        assertThat(isuppliers).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllIsuppliers() throws Exception {
        // Initialize the database
        isupplierRepository.saveAndFlush(isupplier);

        // Get all the isuppliers
        restIsupplierMockMvc.perform(get("/api/isuppliers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(isupplier.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].isenabled").value(hasItem(DEFAULT_ISENABLED.booleanValue())))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getIsupplier() throws Exception {
        // Initialize the database
        isupplierRepository.saveAndFlush(isupplier);

        // Get the isupplier
        restIsupplierMockMvc.perform(get("/api/isuppliers/{id}", isupplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(isupplier.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.isenabled").value(DEFAULT_ISENABLED.booleanValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIsupplier() throws Exception {
        // Get the isupplier
        restIsupplierMockMvc.perform(get("/api/isuppliers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIsupplier() throws Exception {
        // Initialize the database
        isupplierRepository.saveAndFlush(isupplier);

		int databaseSizeBeforeUpdate = isupplierRepository.findAll().size();

        // Update the isupplier
        isupplier.setCode(UPDATED_CODE);
        isupplier.setIsenabled(UPDATED_ISENABLED);
        isupplier.setRemark(UPDATED_REMARK);
        isupplier.setAddress(UPDATED_ADDRESS);
        restIsupplierMockMvc.perform(put("/api/isuppliers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(isupplier)))
                .andExpect(status().isOk());

        // Validate the Isupplier in the database
        List<Isupplier> isuppliers = isupplierRepository.findAll();
        assertThat(isuppliers).hasSize(databaseSizeBeforeUpdate);
        Isupplier testIsupplier = isuppliers.get(isuppliers.size() - 1);
        assertThat(testIsupplier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIsupplier.getIsenabled()).isEqualTo(UPDATED_ISENABLED);
        assertThat(testIsupplier.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testIsupplier.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void deleteIsupplier() throws Exception {
        // Initialize the database
        isupplierRepository.saveAndFlush(isupplier);

		int databaseSizeBeforeDelete = isupplierRepository.findAll().size();

        // Get the isupplier
        restIsupplierMockMvc.perform(delete("/api/isuppliers/{id}", isupplier.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Isupplier> isuppliers = isupplierRepository.findAll();
        assertThat(isuppliers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
