package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Bomheader;
import com.spring.app.repository.BomheaderRepository;

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
 * Test class for the BomheaderResource REST controller.
 *
 * @see BomheaderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BomheaderResourceTest {

    private static final String DEFAULT_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_CODE = "UPDATED_TEXT";

    @Inject
    private BomheaderRepository bomheaderRepository;

    private MockMvc restBomheaderMockMvc;

    private Bomheader bomheader;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BomheaderResource bomheaderResource = new BomheaderResource();
        ReflectionTestUtils.setField(bomheaderResource, "bomheaderRepository", bomheaderRepository);
        this.restBomheaderMockMvc = MockMvcBuilders.standaloneSetup(bomheaderResource).build();
    }

    @Before
    public void initTest() {
        bomheader = new Bomheader();
        bomheader.setCode(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createBomheader() throws Exception {
        int databaseSizeBeforeCreate = bomheaderRepository.findAll().size();

        // Create the Bomheader
        restBomheaderMockMvc.perform(post("/api/bomheaders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bomheader)))
                .andExpect(status().isCreated());

        // Validate the Bomheader in the database
        List<Bomheader> bomheaders = bomheaderRepository.findAll();
        assertThat(bomheaders).hasSize(databaseSizeBeforeCreate + 1);
        Bomheader testBomheader = bomheaders.get(bomheaders.size() - 1);
        assertThat(testBomheader.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(bomheaderRepository.findAll()).hasSize(0);
        // set the field null
        bomheader.setCode(null);

        // Create the Bomheader, which fails.
        restBomheaderMockMvc.perform(post("/api/bomheaders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bomheader)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Bomheader> bomheaders = bomheaderRepository.findAll();
        assertThat(bomheaders).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllBomheaders() throws Exception {
        // Initialize the database
        bomheaderRepository.saveAndFlush(bomheader);

        // Get all the bomheaders
        restBomheaderMockMvc.perform(get("/api/bomheaders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bomheader.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getBomheader() throws Exception {
        // Initialize the database
        bomheaderRepository.saveAndFlush(bomheader);

        // Get the bomheader
        restBomheaderMockMvc.perform(get("/api/bomheaders/{id}", bomheader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bomheader.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBomheader() throws Exception {
        // Get the bomheader
        restBomheaderMockMvc.perform(get("/api/bomheaders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBomheader() throws Exception {
        // Initialize the database
        bomheaderRepository.saveAndFlush(bomheader);

		int databaseSizeBeforeUpdate = bomheaderRepository.findAll().size();

        // Update the bomheader
        bomheader.setCode(UPDATED_CODE);
        restBomheaderMockMvc.perform(put("/api/bomheaders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bomheader)))
                .andExpect(status().isOk());

        // Validate the Bomheader in the database
        List<Bomheader> bomheaders = bomheaderRepository.findAll();
        assertThat(bomheaders).hasSize(databaseSizeBeforeUpdate);
        Bomheader testBomheader = bomheaders.get(bomheaders.size() - 1);
        assertThat(testBomheader.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deleteBomheader() throws Exception {
        // Initialize the database
        bomheaderRepository.saveAndFlush(bomheader);

		int databaseSizeBeforeDelete = bomheaderRepository.findAll().size();

        // Get the bomheader
        restBomheaderMockMvc.perform(delete("/api/bomheaders/{id}", bomheader.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bomheader> bomheaders = bomheaderRepository.findAll();
        assertThat(bomheaders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
