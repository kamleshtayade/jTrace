package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Plantmfgline;
import com.spring.app.repository.PlantmfglineRepository;

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
 * Test class for the PlantmfglineResource REST controller.
 *
 * @see PlantmfglineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlantmfglineResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_CAPACITY = "SAMPLE_TEXT";
    private static final String UPDATED_CAPACITY = "UPDATED_TEXT";

    @Inject
    private PlantmfglineRepository plantmfglineRepository;

    private MockMvc restPlantmfglineMockMvc;

    private Plantmfgline plantmfgline;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlantmfglineResource plantmfglineResource = new PlantmfglineResource();
        ReflectionTestUtils.setField(plantmfglineResource, "plantmfglineRepository", plantmfglineRepository);
        this.restPlantmfglineMockMvc = MockMvcBuilders.standaloneSetup(plantmfglineResource).build();
    }

    @Before
    public void initTest() {
        plantmfgline = new Plantmfgline();
        plantmfgline.setName(DEFAULT_NAME);
        plantmfgline.setCapacity(DEFAULT_CAPACITY);
    }

    @Test
    @Transactional
    public void createPlantmfgline() throws Exception {
        int databaseSizeBeforeCreate = plantmfglineRepository.findAll().size();

        // Create the Plantmfgline
        restPlantmfglineMockMvc.perform(post("/api/plantmfglines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantmfgline)))
                .andExpect(status().isCreated());

        // Validate the Plantmfgline in the database
        List<Plantmfgline> plantmfglines = plantmfglineRepository.findAll();
        assertThat(plantmfglines).hasSize(databaseSizeBeforeCreate + 1);
        Plantmfgline testPlantmfgline = plantmfglines.get(plantmfglines.size() - 1);
        assertThat(testPlantmfgline.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlantmfgline.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(plantmfglineRepository.findAll()).hasSize(0);
        // set the field null
        plantmfgline.setName(null);

        // Create the Plantmfgline, which fails.
        restPlantmfglineMockMvc.perform(post("/api/plantmfglines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantmfgline)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Plantmfgline> plantmfglines = plantmfglineRepository.findAll();
        assertThat(plantmfglines).hasSize(0);
    }

    @Test
    @Transactional
    public void checkCapacityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(plantmfglineRepository.findAll()).hasSize(0);
        // set the field null
        plantmfgline.setCapacity(null);

        // Create the Plantmfgline, which fails.
        restPlantmfglineMockMvc.perform(post("/api/plantmfglines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantmfgline)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Plantmfgline> plantmfglines = plantmfglineRepository.findAll();
        assertThat(plantmfglines).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllPlantmfglines() throws Exception {
        // Initialize the database
        plantmfglineRepository.saveAndFlush(plantmfgline);

        // Get all the plantmfglines
        restPlantmfglineMockMvc.perform(get("/api/plantmfglines"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(plantmfgline.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY.toString())));
    }

    @Test
    @Transactional
    public void getPlantmfgline() throws Exception {
        // Initialize the database
        plantmfglineRepository.saveAndFlush(plantmfgline);

        // Get the plantmfgline
        restPlantmfglineMockMvc.perform(get("/api/plantmfglines/{id}", plantmfgline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(plantmfgline.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlantmfgline() throws Exception {
        // Get the plantmfgline
        restPlantmfglineMockMvc.perform(get("/api/plantmfglines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlantmfgline() throws Exception {
        // Initialize the database
        plantmfglineRepository.saveAndFlush(plantmfgline);

		int databaseSizeBeforeUpdate = plantmfglineRepository.findAll().size();

        // Update the plantmfgline
        plantmfgline.setName(UPDATED_NAME);
        plantmfgline.setCapacity(UPDATED_CAPACITY);
        restPlantmfglineMockMvc.perform(put("/api/plantmfglines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantmfgline)))
                .andExpect(status().isOk());

        // Validate the Plantmfgline in the database
        List<Plantmfgline> plantmfglines = plantmfglineRepository.findAll();
        assertThat(plantmfglines).hasSize(databaseSizeBeforeUpdate);
        Plantmfgline testPlantmfgline = plantmfglines.get(plantmfglines.size() - 1);
        assertThat(testPlantmfgline.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlantmfgline.getCapacity()).isEqualTo(UPDATED_CAPACITY);
    }

    @Test
    @Transactional
    public void deletePlantmfgline() throws Exception {
        // Initialize the database
        plantmfglineRepository.saveAndFlush(plantmfgline);

		int databaseSizeBeforeDelete = plantmfglineRepository.findAll().size();

        // Get the plantmfgline
        restPlantmfglineMockMvc.perform(delete("/api/plantmfglines/{id}", plantmfgline.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Plantmfgline> plantmfglines = plantmfglineRepository.findAll();
        assertThat(plantmfglines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
