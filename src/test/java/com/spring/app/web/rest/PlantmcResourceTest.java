package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Plantmc;
import com.spring.app.repository.PlantmcRepository;

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
 * Test class for the PlantmcResource REST controller.
 *
 * @see PlantmcResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlantmcResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_SPECIFICATION = "SAMPLE_TEXT";
    private static final String UPDATED_SPECIFICATION = "UPDATED_TEXT";

    @Inject
    private PlantmcRepository plantmcRepository;

    private MockMvc restPlantmcMockMvc;

    private Plantmc plantmc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlantmcResource plantmcResource = new PlantmcResource();
        ReflectionTestUtils.setField(plantmcResource, "plantmcRepository", plantmcRepository);
        this.restPlantmcMockMvc = MockMvcBuilders.standaloneSetup(plantmcResource).build();
    }

    @Before
    public void initTest() {
        plantmc = new Plantmc();
        plantmc.setName(DEFAULT_NAME);
        plantmc.setDescription(DEFAULT_DESCRIPTION);
        plantmc.setSpecification(DEFAULT_SPECIFICATION);
    }

    @Test
    @Transactional
    public void createPlantmc() throws Exception {
        int databaseSizeBeforeCreate = plantmcRepository.findAll().size();

        // Create the Plantmc
        restPlantmcMockMvc.perform(post("/api/plantmcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantmc)))
                .andExpect(status().isCreated());

        // Validate the Plantmc in the database
        List<Plantmc> plantmcs = plantmcRepository.findAll();
        assertThat(plantmcs).hasSize(databaseSizeBeforeCreate + 1);
        Plantmc testPlantmc = plantmcs.get(plantmcs.size() - 1);
        assertThat(testPlantmc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlantmc.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlantmc.getSpecification()).isEqualTo(DEFAULT_SPECIFICATION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(plantmcRepository.findAll()).hasSize(0);
        // set the field null
        plantmc.setName(null);

        // Create the Plantmc, which fails.
        restPlantmcMockMvc.perform(post("/api/plantmcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantmc)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Plantmc> plantmcs = plantmcRepository.findAll();
        assertThat(plantmcs).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllPlantmcs() throws Exception {
        // Initialize the database
        plantmcRepository.saveAndFlush(plantmc);

        // Get all the plantmcs
        restPlantmcMockMvc.perform(get("/api/plantmcs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(plantmc.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].specification").value(hasItem(DEFAULT_SPECIFICATION.toString())));
    }

    @Test
    @Transactional
    public void getPlantmc() throws Exception {
        // Initialize the database
        plantmcRepository.saveAndFlush(plantmc);

        // Get the plantmc
        restPlantmcMockMvc.perform(get("/api/plantmcs/{id}", plantmc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(plantmc.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.specification").value(DEFAULT_SPECIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlantmc() throws Exception {
        // Get the plantmc
        restPlantmcMockMvc.perform(get("/api/plantmcs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlantmc() throws Exception {
        // Initialize the database
        plantmcRepository.saveAndFlush(plantmc);

		int databaseSizeBeforeUpdate = plantmcRepository.findAll().size();

        // Update the plantmc
        plantmc.setName(UPDATED_NAME);
        plantmc.setDescription(UPDATED_DESCRIPTION);
        plantmc.setSpecification(UPDATED_SPECIFICATION);
        restPlantmcMockMvc.perform(put("/api/plantmcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantmc)))
                .andExpect(status().isOk());

        // Validate the Plantmc in the database
        List<Plantmc> plantmcs = plantmcRepository.findAll();
        assertThat(plantmcs).hasSize(databaseSizeBeforeUpdate);
        Plantmc testPlantmc = plantmcs.get(plantmcs.size() - 1);
        assertThat(testPlantmc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlantmc.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlantmc.getSpecification()).isEqualTo(UPDATED_SPECIFICATION);
    }

    @Test
    @Transactional
    public void deletePlantmc() throws Exception {
        // Initialize the database
        plantmcRepository.saveAndFlush(plantmc);

		int databaseSizeBeforeDelete = plantmcRepository.findAll().size();

        // Get the plantmc
        restPlantmcMockMvc.perform(delete("/api/plantmcs/{id}", plantmc.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Plantmc> plantmcs = plantmcRepository.findAll();
        assertThat(plantmcs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
