package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Manufacturer;
import com.spring.app.repository.ManufacturerRepository;

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
 * Test class for the ManufacturerResource REST controller.
 *
 * @see ManufacturerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ManufacturerResourceTest {

    private static final String DEFAULT_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ISENABLED = false;
    private static final Boolean UPDATED_ISENABLED = true;
    private static final String DEFAULT_MFRCAT = "SAMPLE_TEXT";
    private static final String UPDATED_MFRCAT = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";

    @Inject
    private ManufacturerRepository manufacturerRepository;

    private MockMvc restManufacturerMockMvc;

    private Manufacturer manufacturer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ManufacturerResource manufacturerResource = new ManufacturerResource();
        ReflectionTestUtils.setField(manufacturerResource, "manufacturerRepository", manufacturerRepository);
        this.restManufacturerMockMvc = MockMvcBuilders.standaloneSetup(manufacturerResource).build();
    }

    @Before
    public void initTest() {
        manufacturer = new Manufacturer();
        manufacturer.setCode(DEFAULT_CODE);
        manufacturer.setName(DEFAULT_NAME);
        manufacturer.setIsenabled(DEFAULT_ISENABLED);
        manufacturer.setMfrcat(DEFAULT_MFRCAT);
        manufacturer.setAddress(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createManufacturer() throws Exception {
        int databaseSizeBeforeCreate = manufacturerRepository.findAll().size();

        // Create the Manufacturer
        restManufacturerMockMvc.perform(post("/api/manufacturers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(manufacturer)))
                .andExpect(status().isCreated());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        assertThat(manufacturers).hasSize(databaseSizeBeforeCreate + 1);
        Manufacturer testManufacturer = manufacturers.get(manufacturers.size() - 1);
        assertThat(testManufacturer.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testManufacturer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testManufacturer.getIsenabled()).isEqualTo(DEFAULT_ISENABLED);
        assertThat(testManufacturer.getMfrcat()).isEqualTo(DEFAULT_MFRCAT);
        assertThat(testManufacturer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(manufacturerRepository.findAll()).hasSize(0);
        // set the field null
        manufacturer.setCode(null);

        // Create the Manufacturer, which fails.
        restManufacturerMockMvc.perform(post("/api/manufacturers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(manufacturer)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        assertThat(manufacturers).hasSize(0);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(manufacturerRepository.findAll()).hasSize(0);
        // set the field null
        manufacturer.setName(null);

        // Create the Manufacturer, which fails.
        restManufacturerMockMvc.perform(post("/api/manufacturers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(manufacturer)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        assertThat(manufacturers).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllManufacturers() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturers
        restManufacturerMockMvc.perform(get("/api/manufacturers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(manufacturer.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].isenabled").value(hasItem(DEFAULT_ISENABLED.booleanValue())))
                .andExpect(jsonPath("$.[*].mfrcat").value(hasItem(DEFAULT_MFRCAT.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get the manufacturer
        restManufacturerMockMvc.perform(get("/api/manufacturers/{id}", manufacturer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(manufacturer.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isenabled").value(DEFAULT_ISENABLED.booleanValue()))
            .andExpect(jsonPath("$.mfrcat").value(DEFAULT_MFRCAT.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingManufacturer() throws Exception {
        // Get the manufacturer
        restManufacturerMockMvc.perform(get("/api/manufacturers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

		int databaseSizeBeforeUpdate = manufacturerRepository.findAll().size();

        // Update the manufacturer
        manufacturer.setCode(UPDATED_CODE);
        manufacturer.setName(UPDATED_NAME);
        manufacturer.setIsenabled(UPDATED_ISENABLED);
        manufacturer.setMfrcat(UPDATED_MFRCAT);
        manufacturer.setAddress(UPDATED_ADDRESS);
        restManufacturerMockMvc.perform(put("/api/manufacturers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(manufacturer)))
                .andExpect(status().isOk());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        assertThat(manufacturers).hasSize(databaseSizeBeforeUpdate);
        Manufacturer testManufacturer = manufacturers.get(manufacturers.size() - 1);
        assertThat(testManufacturer.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testManufacturer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testManufacturer.getIsenabled()).isEqualTo(UPDATED_ISENABLED);
        assertThat(testManufacturer.getMfrcat()).isEqualTo(UPDATED_MFRCAT);
        assertThat(testManufacturer.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void deleteManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

		int databaseSizeBeforeDelete = manufacturerRepository.findAll().size();

        // Get the manufacturer
        restManufacturerMockMvc.perform(delete("/api/manufacturers/{id}", manufacturer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        assertThat(manufacturers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
