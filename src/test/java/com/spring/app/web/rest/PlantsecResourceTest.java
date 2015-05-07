package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Plantsec;
import com.spring.app.repository.PlantsecRepository;

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
 * Test class for the PlantsecResource REST controller.
 *
 * @see PlantsecResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlantsecResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ISENABLED = false;
    private static final Boolean UPDATED_ISENABLED = true;

    private static final Integer DEFAULT_CAPACITY = 0;
    private static final Integer UPDATED_CAPACITY = 1;

    @Inject
    private PlantsecRepository plantsecRepository;

    private MockMvc restPlantsecMockMvc;

    private Plantsec plantsec;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlantsecResource plantsecResource = new PlantsecResource();
        ReflectionTestUtils.setField(plantsecResource, "plantsecRepository", plantsecRepository);
        this.restPlantsecMockMvc = MockMvcBuilders.standaloneSetup(plantsecResource).build();
    }

    @Before
    public void initTest() {
        plantsec = new Plantsec();
        plantsec.setName(DEFAULT_NAME);
        plantsec.setDescription(DEFAULT_DESCRIPTION);
        plantsec.setIsenabled(DEFAULT_ISENABLED);
        plantsec.setCapacity(DEFAULT_CAPACITY);
    }

    @Test
    @Transactional
    public void createPlantsec() throws Exception {
        int databaseSizeBeforeCreate = plantsecRepository.findAll().size();

        // Create the Plantsec
        restPlantsecMockMvc.perform(post("/api/plantsecs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantsec)))
                .andExpect(status().isCreated());

        // Validate the Plantsec in the database
        List<Plantsec> plantsecs = plantsecRepository.findAll();
        assertThat(plantsecs).hasSize(databaseSizeBeforeCreate + 1);
        Plantsec testPlantsec = plantsecs.get(plantsecs.size() - 1);
        assertThat(testPlantsec.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlantsec.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPlantsec.getIsenabled()).isEqualTo(DEFAULT_ISENABLED);
        assertThat(testPlantsec.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(plantsecRepository.findAll()).hasSize(0);
        // set the field null
        plantsec.setName(null);

        // Create the Plantsec, which fails.
        restPlantsecMockMvc.perform(post("/api/plantsecs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantsec)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Plantsec> plantsecs = plantsecRepository.findAll();
        assertThat(plantsecs).hasSize(0);
    }

    @Test
    @Transactional
    public void checkCapacityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(plantsecRepository.findAll()).hasSize(0);
        // set the field null
        plantsec.setCapacity(null);

        // Create the Plantsec, which fails.
        restPlantsecMockMvc.perform(post("/api/plantsecs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantsec)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Plantsec> plantsecs = plantsecRepository.findAll();
        assertThat(plantsecs).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllPlantsecs() throws Exception {
        // Initialize the database
        plantsecRepository.saveAndFlush(plantsec);

        // Get all the plantsecs
        restPlantsecMockMvc.perform(get("/api/plantsecs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(plantsec.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].isenabled").value(hasItem(DEFAULT_ISENABLED.booleanValue())))
                .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)));
    }

    @Test
    @Transactional
    public void getPlantsec() throws Exception {
        // Initialize the database
        plantsecRepository.saveAndFlush(plantsec);

        // Get the plantsec
        restPlantsecMockMvc.perform(get("/api/plantsecs/{id}", plantsec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(plantsec.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isenabled").value(DEFAULT_ISENABLED.booleanValue()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY));
    }

    @Test
    @Transactional
    public void getNonExistingPlantsec() throws Exception {
        // Get the plantsec
        restPlantsecMockMvc.perform(get("/api/plantsecs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlantsec() throws Exception {
        // Initialize the database
        plantsecRepository.saveAndFlush(plantsec);

		int databaseSizeBeforeUpdate = plantsecRepository.findAll().size();

        // Update the plantsec
        plantsec.setName(UPDATED_NAME);
        plantsec.setDescription(UPDATED_DESCRIPTION);
        plantsec.setIsenabled(UPDATED_ISENABLED);
        plantsec.setCapacity(UPDATED_CAPACITY);
        restPlantsecMockMvc.perform(put("/api/plantsecs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plantsec)))
                .andExpect(status().isOk());

        // Validate the Plantsec in the database
        List<Plantsec> plantsecs = plantsecRepository.findAll();
        assertThat(plantsecs).hasSize(databaseSizeBeforeUpdate);
        Plantsec testPlantsec = plantsecs.get(plantsecs.size() - 1);
        assertThat(testPlantsec.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlantsec.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPlantsec.getIsenabled()).isEqualTo(UPDATED_ISENABLED);
        assertThat(testPlantsec.getCapacity()).isEqualTo(UPDATED_CAPACITY);
    }

    @Test
    @Transactional
    public void deletePlantsec() throws Exception {
        // Initialize the database
        plantsecRepository.saveAndFlush(plantsec);

		int databaseSizeBeforeDelete = plantsecRepository.findAll().size();

        // Get the plantsec
        restPlantsecMockMvc.perform(delete("/api/plantsecs/{id}", plantsec.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Plantsec> plantsecs = plantsecRepository.findAll();
        assertThat(plantsecs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
