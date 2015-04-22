package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Manufacturer;
import com.spring.app.repository.ManufacturerRepository;

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

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;
    private static final String DEFAULT_CATEGORY = "SAMPLE_TEXT";
    private static final String UPDATED_CATEGORY = "UPDATED_TEXT";
    private static final String DEFAULT_CONTACT = "SAMPLE_TEXT";
    private static final String UPDATED_CONTACT = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_PHONE = "SAMPLE_TEXT";
    private static final String UPDATED_PHONE = "UPDATED_TEXT";

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
        manufacturer.setEnabled(DEFAULT_ENABLED);
        manufacturer.setCategory(DEFAULT_CATEGORY);
        manufacturer.setContact(DEFAULT_CONTACT);
        manufacturer.setEmail(DEFAULT_EMAIL);
        manufacturer.setPhone(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createManufacturer() throws Exception {
        // Validate the database is empty
        assertThat(manufacturerRepository.findAll()).hasSize(0);

        // Create the Manufacturer
        restManufacturerMockMvc.perform(post("/api/manufacturers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(manufacturer)))
                .andExpect(status().isOk());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        assertThat(manufacturers).hasSize(1);
        Manufacturer testManufacturer = manufacturers.iterator().next();
        assertThat(testManufacturer.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testManufacturer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testManufacturer.getEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testManufacturer.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testManufacturer.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testManufacturer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testManufacturer.getPhone()).isEqualTo(DEFAULT_PHONE);
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
                .andExpect(jsonPath("$.[0].id").value(manufacturer.getId().intValue()))
                .andExpect(jsonPath("$.[0].code").value(DEFAULT_CODE.toString()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].enabled").value(DEFAULT_ENABLED.booleanValue()))
                .andExpect(jsonPath("$.[0].category").value(DEFAULT_CATEGORY.toString()))
                .andExpect(jsonPath("$.[0].contact").value(DEFAULT_CONTACT.toString()))
                .andExpect(jsonPath("$.[0].email").value(DEFAULT_EMAIL.toString()))
                .andExpect(jsonPath("$.[0].phone").value(DEFAULT_PHONE.toString()));
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
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingManufacturer() throws Exception {
        // Get the manufacturer
        restManufacturerMockMvc.perform(get("/api/manufacturers/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Update the manufacturer
        manufacturer.setCode(UPDATED_CODE);
        manufacturer.setName(UPDATED_NAME);
        manufacturer.setEnabled(UPDATED_ENABLED);
        manufacturer.setCategory(UPDATED_CATEGORY);
        manufacturer.setContact(UPDATED_CONTACT);
        manufacturer.setEmail(UPDATED_EMAIL);
        manufacturer.setPhone(UPDATED_PHONE);
        restManufacturerMockMvc.perform(post("/api/manufacturers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(manufacturer)))
                .andExpect(status().isOk());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        assertThat(manufacturers).hasSize(1);
        Manufacturer testManufacturer = manufacturers.iterator().next();
        assertThat(testManufacturer.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testManufacturer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testManufacturer.getEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testManufacturer.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testManufacturer.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testManufacturer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testManufacturer.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void deleteManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get the manufacturer
        restManufacturerMockMvc.perform(delete("/api/manufacturers/{id}", manufacturer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Manufacturer> manufacturers = manufacturerRepository.findAll();
        assertThat(manufacturers).hasSize(0);
    }
}
