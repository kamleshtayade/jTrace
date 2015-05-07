package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Itemmtr;
import com.spring.app.repository.ItemmtrRepository;

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
 * Test class for the ItemmtrResource REST controller.
 *
 * @see ItemmtrResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ItemmtrResourceTest {

    private static final String DEFAULT_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_SPECIFICATION = "SAMPLE_TEXT";
    private static final String UPDATED_SPECIFICATION = "UPDATED_TEXT";

    @Inject
    private ItemmtrRepository itemmtrRepository;

    private MockMvc restItemmtrMockMvc;

    private Itemmtr itemmtr;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemmtrResource itemmtrResource = new ItemmtrResource();
        ReflectionTestUtils.setField(itemmtrResource, "itemmtrRepository", itemmtrRepository);
        this.restItemmtrMockMvc = MockMvcBuilders.standaloneSetup(itemmtrResource).build();
    }

    @Before
    public void initTest() {
        itemmtr = new Itemmtr();
        itemmtr.setCode(DEFAULT_CODE);
        itemmtr.setDescription(DEFAULT_DESCRIPTION);
        itemmtr.setSpecification(DEFAULT_SPECIFICATION);
    }

    @Test
    @Transactional
    public void createItemmtr() throws Exception {
        int databaseSizeBeforeCreate = itemmtrRepository.findAll().size();

        // Create the Itemmtr
        restItemmtrMockMvc.perform(post("/api/itemmtrs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemmtr)))
                .andExpect(status().isCreated());

        // Validate the Itemmtr in the database
        List<Itemmtr> itemmtrs = itemmtrRepository.findAll();
        assertThat(itemmtrs).hasSize(databaseSizeBeforeCreate + 1);
        Itemmtr testItemmtr = itemmtrs.get(itemmtrs.size() - 1);
        assertThat(testItemmtr.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testItemmtr.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testItemmtr.getSpecification()).isEqualTo(DEFAULT_SPECIFICATION);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(itemmtrRepository.findAll()).hasSize(0);
        // set the field null
        itemmtr.setCode(null);

        // Create the Itemmtr, which fails.
        restItemmtrMockMvc.perform(post("/api/itemmtrs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemmtr)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Itemmtr> itemmtrs = itemmtrRepository.findAll();
        assertThat(itemmtrs).hasSize(0);
    }

    @Test
    @Transactional
    public void checkSpecificationIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(itemmtrRepository.findAll()).hasSize(0);
        // set the field null
        itemmtr.setSpecification(null);

        // Create the Itemmtr, which fails.
        restItemmtrMockMvc.perform(post("/api/itemmtrs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemmtr)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Itemmtr> itemmtrs = itemmtrRepository.findAll();
        assertThat(itemmtrs).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllItemmtrs() throws Exception {
        // Initialize the database
        itemmtrRepository.saveAndFlush(itemmtr);

        // Get all the itemmtrs
        restItemmtrMockMvc.perform(get("/api/itemmtrs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(itemmtr.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].specification").value(hasItem(DEFAULT_SPECIFICATION.toString())));
    }

    @Test
    @Transactional
    public void getItemmtr() throws Exception {
        // Initialize the database
        itemmtrRepository.saveAndFlush(itemmtr);

        // Get the itemmtr
        restItemmtrMockMvc.perform(get("/api/itemmtrs/{id}", itemmtr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(itemmtr.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.specification").value(DEFAULT_SPECIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingItemmtr() throws Exception {
        // Get the itemmtr
        restItemmtrMockMvc.perform(get("/api/itemmtrs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemmtr() throws Exception {
        // Initialize the database
        itemmtrRepository.saveAndFlush(itemmtr);

		int databaseSizeBeforeUpdate = itemmtrRepository.findAll().size();

        // Update the itemmtr
        itemmtr.setCode(UPDATED_CODE);
        itemmtr.setDescription(UPDATED_DESCRIPTION);
        itemmtr.setSpecification(UPDATED_SPECIFICATION);
        restItemmtrMockMvc.perform(put("/api/itemmtrs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemmtr)))
                .andExpect(status().isOk());

        // Validate the Itemmtr in the database
        List<Itemmtr> itemmtrs = itemmtrRepository.findAll();
        assertThat(itemmtrs).hasSize(databaseSizeBeforeUpdate);
        Itemmtr testItemmtr = itemmtrs.get(itemmtrs.size() - 1);
        assertThat(testItemmtr.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testItemmtr.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testItemmtr.getSpecification()).isEqualTo(UPDATED_SPECIFICATION);
    }

    @Test
    @Transactional
    public void deleteItemmtr() throws Exception {
        // Initialize the database
        itemmtrRepository.saveAndFlush(itemmtr);

		int databaseSizeBeforeDelete = itemmtrRepository.findAll().size();

        // Get the itemmtr
        restItemmtrMockMvc.perform(delete("/api/itemmtrs/{id}", itemmtr.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Itemmtr> itemmtrs = itemmtrRepository.findAll();
        assertThat(itemmtrs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
