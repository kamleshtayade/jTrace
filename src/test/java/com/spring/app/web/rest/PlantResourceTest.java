package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Plant;
import com.spring.app.repository.PlantRepository;

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
 * Test class for the PlantResource REST controller.
 *
 * @see PlantResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlantResourceTest {

    private static final String DEFAULT_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_LOCATION = "SAMPLE_TEXT";
    private static final String UPDATED_LOCATION = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;
    private static final String DEFAULT_CAPACITY = "SAMPLE_TEXT";
    private static final String UPDATED_CAPACITY = "UPDATED_TEXT";

    @Inject
    private PlantRepository plantRepository;

    private MockMvc restPlantMockMvc;

    private Plant plant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlantResource plantResource = new PlantResource();
        ReflectionTestUtils.setField(plantResource, "plantRepository", plantRepository);
        this.restPlantMockMvc = MockMvcBuilders.standaloneSetup(plantResource).build();
    }

    @Before
    public void initTest() {
        plant = new Plant();
        plant.setCode(DEFAULT_CODE);
        plant.setLocation(DEFAULT_LOCATION);
        plant.setAddress(DEFAULT_ADDRESS);
        plant.setIsActive(DEFAULT_IS_ACTIVE);
        plant.setCapacity(DEFAULT_CAPACITY);
    }

    @Test
    @Transactional
    public void createPlant() throws Exception {
        // Validate the database is empty
        assertThat(plantRepository.findAll()).hasSize(0);

        // Create the Plant
        restPlantMockMvc.perform(post("/api/plants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plant)))
                .andExpect(status().isOk());

        // Validate the Plant in the database
        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(1);
        Plant testPlant = plants.iterator().next();
        assertThat(testPlant.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPlant.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testPlant.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPlant.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testPlant.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllPlants() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plants
        restPlantMockMvc.perform(get("/api/plants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(plant.getId().intValue()))
                .andExpect(jsonPath("$.[0].code").value(DEFAULT_CODE.toString()))
                .andExpect(jsonPath("$.[0].location").value(DEFAULT_LOCATION.toString()))
                .andExpect(jsonPath("$.[0].address").value(DEFAULT_ADDRESS.toString()))
                .andExpect(jsonPath("$.[0].isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
                .andExpect(jsonPath("$.[0].capacity").value(DEFAULT_CAPACITY.toString()));
    }

    @Test
    @Transactional
    public void getPlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get the plant
        restPlantMockMvc.perform(get("/api/plants/{id}", plant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(plant.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlant() throws Exception {
        // Get the plant
        restPlantMockMvc.perform(get("/api/plants/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Update the plant
        plant.setCode(UPDATED_CODE);
        plant.setLocation(UPDATED_LOCATION);
        plant.setAddress(UPDATED_ADDRESS);
        plant.setIsActive(UPDATED_IS_ACTIVE);
        plant.setCapacity(UPDATED_CAPACITY);
        restPlantMockMvc.perform(post("/api/plants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plant)))
                .andExpect(status().isOk());

        // Validate the Plant in the database
        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(1);
        Plant testPlant = plants.iterator().next();
        assertThat(testPlant.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPlant.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testPlant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPlant.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPlant.getCapacity()).isEqualTo(UPDATED_CAPACITY);
    }

    @Test
    @Transactional
    public void deletePlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get the plant
        restPlantMockMvc.perform(delete("/api/plants/{id}", plant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(0);
    }
}
