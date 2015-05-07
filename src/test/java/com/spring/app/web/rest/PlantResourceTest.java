package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Plant;
import com.spring.app.repository.PlantRepository;

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
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ISENABLED = false;
    private static final Boolean UPDATED_ISENABLED = true;

    private static final Integer DEFAULT_CAPACITY = 0;
    private static final Integer UPDATED_CAPACITY = 1;

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
        plant.setAddress(DEFAULT_ADDRESS);
        plant.setIsenabled(DEFAULT_ISENABLED);
        plant.setCapacity(DEFAULT_CAPACITY);
    }

    @Test
    @Transactional
    public void createPlant() throws Exception {
        int databaseSizeBeforeCreate = plantRepository.findAll().size();

        // Create the Plant
        restPlantMockMvc.perform(post("/api/plants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plant)))
                .andExpect(status().isCreated());

        // Validate the Plant in the database
        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(databaseSizeBeforeCreate + 1);
        Plant testPlant = plants.get(plants.size() - 1);
        assertThat(testPlant.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPlant.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPlant.getIsenabled()).isEqualTo(DEFAULT_ISENABLED);
        assertThat(testPlant.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(plantRepository.findAll()).hasSize(0);
        // set the field null
        plant.setCode(null);

        // Create the Plant, which fails.
        restPlantMockMvc.perform(post("/api/plants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plant)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(0);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(plantRepository.findAll()).hasSize(0);
        // set the field null
        plant.setAddress(null);

        // Create the Plant, which fails.
        restPlantMockMvc.perform(post("/api/plants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plant)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(0);
    }

    @Test
    @Transactional
    public void checkCapacityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(plantRepository.findAll()).hasSize(0);
        // set the field null
        plant.setCapacity(null);

        // Create the Plant, which fails.
        restPlantMockMvc.perform(post("/api/plants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plant)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(0);
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
                .andExpect(jsonPath("$.[*].id").value(hasItem(plant.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].isenabled").value(hasItem(DEFAULT_ISENABLED.booleanValue())))
                .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)));
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
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.isenabled").value(DEFAULT_ISENABLED.booleanValue()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY));
    }

    @Test
    @Transactional
    public void getNonExistingPlant() throws Exception {
        // Get the plant
        restPlantMockMvc.perform(get("/api/plants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

		int databaseSizeBeforeUpdate = plantRepository.findAll().size();

        // Update the plant
        plant.setCode(UPDATED_CODE);
        plant.setAddress(UPDATED_ADDRESS);
        plant.setIsenabled(UPDATED_ISENABLED);
        plant.setCapacity(UPDATED_CAPACITY);
        restPlantMockMvc.perform(put("/api/plants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plant)))
                .andExpect(status().isOk());

        // Validate the Plant in the database
        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(databaseSizeBeforeUpdate);
        Plant testPlant = plants.get(plants.size() - 1);
        assertThat(testPlant.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPlant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPlant.getIsenabled()).isEqualTo(UPDATED_ISENABLED);
        assertThat(testPlant.getCapacity()).isEqualTo(UPDATED_CAPACITY);
    }

    @Test
    @Transactional
    public void deletePlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

		int databaseSizeBeforeDelete = plantRepository.findAll().size();

        // Get the plant
        restPlantMockMvc.perform(delete("/api/plants/{id}", plant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Plant> plants = plantRepository.findAll();
        assertThat(plants).hasSize(databaseSizeBeforeDelete - 1);
    }
}
