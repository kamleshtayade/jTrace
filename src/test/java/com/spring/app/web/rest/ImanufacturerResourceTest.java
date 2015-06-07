package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Imanufacturer;
import com.spring.app.repository.ImanufacturerRepository;

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
 * Test class for the ImanufacturerResource REST controller.
 *
 * @see ImanufacturerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ImanufacturerResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ISENABLED = false;
    private static final Boolean UPDATED_ISENABLED = true;
    private static final String DEFAULT_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_MFRCAT = "SAMPLE_TEXT";
    private static final String UPDATED_MFRCAT = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";

    @Inject
    private ImanufacturerRepository imanufacturerRepository;

    private MockMvc restImanufacturerMockMvc;

    private Imanufacturer imanufacturer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ImanufacturerResource imanufacturerResource = new ImanufacturerResource();
        ReflectionTestUtils.setField(imanufacturerResource, "imanufacturerRepository", imanufacturerRepository);
        this.restImanufacturerMockMvc = MockMvcBuilders.standaloneSetup(imanufacturerResource).build();
    }

    @Before
    public void initTest() {
        imanufacturer = new Imanufacturer();
        imanufacturer.setName(DEFAULT_NAME);
        imanufacturer.setIsenabled(DEFAULT_ISENABLED);
        imanufacturer.setCode(DEFAULT_CODE);
        imanufacturer.setMfrcat(DEFAULT_MFRCAT);
        imanufacturer.setAddress(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createImanufacturer() throws Exception {
        int databaseSizeBeforeCreate = imanufacturerRepository.findAll().size();

        // Create the Imanufacturer
        restImanufacturerMockMvc.perform(post("/api/imanufacturers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imanufacturer)))
                .andExpect(status().isCreated());

        // Validate the Imanufacturer in the database
        List<Imanufacturer> imanufacturers = imanufacturerRepository.findAll();
        assertThat(imanufacturers).hasSize(databaseSizeBeforeCreate + 1);
        Imanufacturer testImanufacturer = imanufacturers.get(imanufacturers.size() - 1);
        assertThat(testImanufacturer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testImanufacturer.getIsenabled()).isEqualTo(DEFAULT_ISENABLED);
        assertThat(testImanufacturer.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testImanufacturer.getMfrcat()).isEqualTo(DEFAULT_MFRCAT);
        assertThat(testImanufacturer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(imanufacturerRepository.findAll()).hasSize(0);
        // set the field null
        imanufacturer.setName(null);

        // Create the Imanufacturer, which fails.
        restImanufacturerMockMvc.perform(post("/api/imanufacturers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imanufacturer)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Imanufacturer> imanufacturers = imanufacturerRepository.findAll();
        assertThat(imanufacturers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(imanufacturerRepository.findAll()).hasSize(0);
        // set the field null
        imanufacturer.setCode(null);

        // Create the Imanufacturer, which fails.
        restImanufacturerMockMvc.perform(post("/api/imanufacturers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imanufacturer)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Imanufacturer> imanufacturers = imanufacturerRepository.findAll();
        assertThat(imanufacturers).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllImanufacturers() throws Exception {
        // Initialize the database
        imanufacturerRepository.saveAndFlush(imanufacturer);

        // Get all the imanufacturers
        restImanufacturerMockMvc.perform(get("/api/imanufacturers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(imanufacturer.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].isenabled").value(hasItem(DEFAULT_ISENABLED.booleanValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].mfrcat").value(hasItem(DEFAULT_MFRCAT.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getImanufacturer() throws Exception {
        // Initialize the database
        imanufacturerRepository.saveAndFlush(imanufacturer);

        // Get the imanufacturer
        restImanufacturerMockMvc.perform(get("/api/imanufacturers/{id}", imanufacturer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(imanufacturer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isenabled").value(DEFAULT_ISENABLED.booleanValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.mfrcat").value(DEFAULT_MFRCAT.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImanufacturer() throws Exception {
        // Get the imanufacturer
        restImanufacturerMockMvc.perform(get("/api/imanufacturers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImanufacturer() throws Exception {
        // Initialize the database
        imanufacturerRepository.saveAndFlush(imanufacturer);

		int databaseSizeBeforeUpdate = imanufacturerRepository.findAll().size();

        // Update the imanufacturer
        imanufacturer.setName(UPDATED_NAME);
        imanufacturer.setIsenabled(UPDATED_ISENABLED);
        imanufacturer.setCode(UPDATED_CODE);
        imanufacturer.setMfrcat(UPDATED_MFRCAT);
        imanufacturer.setAddress(UPDATED_ADDRESS);
        restImanufacturerMockMvc.perform(put("/api/imanufacturers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imanufacturer)))
                .andExpect(status().isOk());

        // Validate the Imanufacturer in the database
        List<Imanufacturer> imanufacturers = imanufacturerRepository.findAll();
        assertThat(imanufacturers).hasSize(databaseSizeBeforeUpdate);
        Imanufacturer testImanufacturer = imanufacturers.get(imanufacturers.size() - 1);
        assertThat(testImanufacturer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testImanufacturer.getIsenabled()).isEqualTo(UPDATED_ISENABLED);
        assertThat(testImanufacturer.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testImanufacturer.getMfrcat()).isEqualTo(UPDATED_MFRCAT);
        assertThat(testImanufacturer.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void deleteImanufacturer() throws Exception {
        // Initialize the database
        imanufacturerRepository.saveAndFlush(imanufacturer);

		int databaseSizeBeforeDelete = imanufacturerRepository.findAll().size();

        // Get the imanufacturer
        restImanufacturerMockMvc.perform(delete("/api/imanufacturers/{id}", imanufacturer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Imanufacturer> imanufacturers = imanufacturerRepository.findAll();
        assertThat(imanufacturers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
