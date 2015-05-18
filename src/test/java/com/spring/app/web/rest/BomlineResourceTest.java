package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Bomline;
import com.spring.app.repository.BomlineRepository;

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
 * Test class for the BomlineResource REST controller.
 *
 * @see BomlineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BomlineResourceTest {


    private static final Integer DEFAULT_LINEID = 0;
    private static final Integer UPDATED_LINEID = 1;

    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;
    private static final String DEFAULT_REFDESIGNATOR = "SAMPLE_TEXT";
    private static final String UPDATED_REFDESIGNATOR = "UPDATED_TEXT";

    private static final Integer DEFAULT_PHYSICAL = 0;
    private static final Integer UPDATED_PHYSICAL = 1;

    @Inject
    private BomlineRepository bomlineRepository;

    private MockMvc restBomlineMockMvc;

    private Bomline bomline;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BomlineResource bomlineResource = new BomlineResource();
        ReflectionTestUtils.setField(bomlineResource, "bomlineRepository", bomlineRepository);
        this.restBomlineMockMvc = MockMvcBuilders.standaloneSetup(bomlineResource).build();
    }

    @Before
    public void initTest() {
        bomline = new Bomline();
        bomline.setLineid(DEFAULT_LINEID);
        bomline.setQuantity(DEFAULT_QUANTITY);
        bomline.setRefdesignator(DEFAULT_REFDESIGNATOR);
        bomline.setPhysical(DEFAULT_PHYSICAL);
    }

    @Test
    @Transactional
    public void createBomline() throws Exception {
        int databaseSizeBeforeCreate = bomlineRepository.findAll().size();

        // Create the Bomline
        restBomlineMockMvc.perform(post("/api/bomlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bomline)))
                .andExpect(status().isCreated());

        // Validate the Bomline in the database
        List<Bomline> bomlines = bomlineRepository.findAll();
        assertThat(bomlines).hasSize(databaseSizeBeforeCreate + 1);
        Bomline testBomline = bomlines.get(bomlines.size() - 1);
        assertThat(testBomline.getLineid()).isEqualTo(DEFAULT_LINEID);
        assertThat(testBomline.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testBomline.getRefdesignator()).isEqualTo(DEFAULT_REFDESIGNATOR);
        assertThat(testBomline.getPhysical()).isEqualTo(DEFAULT_PHYSICAL);
    }

    @Test
    @Transactional
    public void checkLineidIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bomlineRepository.findAll()).hasSize(0);
        // set the field null
        bomline.setLineid(null);

        // Create the Bomline, which fails.
        restBomlineMockMvc.perform(post("/api/bomlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bomline)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bomline> bomlines = bomlineRepository.findAll();
        assertThat(bomlines).hasSize(0);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bomlineRepository.findAll()).hasSize(0);
        // set the field null
        bomline.setQuantity(null);

        // Create the Bomline, which fails.
        restBomlineMockMvc.perform(post("/api/bomlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bomline)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bomline> bomlines = bomlineRepository.findAll();
        assertThat(bomlines).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllBomlines() throws Exception {
        // Initialize the database
        bomlineRepository.saveAndFlush(bomline);

        // Get all the bomlines
        restBomlineMockMvc.perform(get("/api/bomlines"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bomline.getId().intValue())))
                .andExpect(jsonPath("$.[*].lineid").value(hasItem(DEFAULT_LINEID)))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
                .andExpect(jsonPath("$.[*].refdesignator").value(hasItem(DEFAULT_REFDESIGNATOR.toString())))
                .andExpect(jsonPath("$.[*].physical").value(hasItem(DEFAULT_PHYSICAL)));
    }

    @Test
    @Transactional
    public void getBomline() throws Exception {
        // Initialize the database
        bomlineRepository.saveAndFlush(bomline);

        // Get the bomline
        restBomlineMockMvc.perform(get("/api/bomlines/{id}", bomline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bomline.getId().intValue()))
            .andExpect(jsonPath("$.lineid").value(DEFAULT_LINEID))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.refdesignator").value(DEFAULT_REFDESIGNATOR.toString()))
            .andExpect(jsonPath("$.physical").value(DEFAULT_PHYSICAL));
    }

    @Test
    @Transactional
    public void getNonExistingBomline() throws Exception {
        // Get the bomline
        restBomlineMockMvc.perform(get("/api/bomlines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBomline() throws Exception {
        // Initialize the database
        bomlineRepository.saveAndFlush(bomline);

		int databaseSizeBeforeUpdate = bomlineRepository.findAll().size();

        // Update the bomline
        bomline.setLineid(UPDATED_LINEID);
        bomline.setQuantity(UPDATED_QUANTITY);
        bomline.setRefdesignator(UPDATED_REFDESIGNATOR);
        bomline.setPhysical(UPDATED_PHYSICAL);
        restBomlineMockMvc.perform(put("/api/bomlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bomline)))
                .andExpect(status().isOk());

        // Validate the Bomline in the database
        List<Bomline> bomlines = bomlineRepository.findAll();
        assertThat(bomlines).hasSize(databaseSizeBeforeUpdate);
        Bomline testBomline = bomlines.get(bomlines.size() - 1);
        assertThat(testBomline.getLineid()).isEqualTo(UPDATED_LINEID);
        assertThat(testBomline.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBomline.getRefdesignator()).isEqualTo(UPDATED_REFDESIGNATOR);
        assertThat(testBomline.getPhysical()).isEqualTo(UPDATED_PHYSICAL);
    }

    @Test
    @Transactional
    public void deleteBomline() throws Exception {
        // Initialize the database
        bomlineRepository.saveAndFlush(bomline);

		int databaseSizeBeforeDelete = bomlineRepository.findAll().size();

        // Get the bomline
        restBomlineMockMvc.perform(delete("/api/bomlines/{id}", bomline.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bomline> bomlines = bomlineRepository.findAll();
        assertThat(bomlines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
