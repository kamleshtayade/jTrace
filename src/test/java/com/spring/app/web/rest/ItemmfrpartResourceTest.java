package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Itemmfrpart;
import com.spring.app.repository.ItemmfrpartRepository;

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
 * Test class for the ItemmfrpartResource REST controller.
 *
 * @see ItemmfrpartResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ItemmfrpartResourceTest {

    private static final String DEFAULT_MFRPART = "SAMPLE_TEXT";
    private static final String UPDATED_MFRPART = "UPDATED_TEXT";
    private static final String DEFAULT_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_STATUS = "UPDATED_TEXT";
    private static final String DEFAULT_SUPPART = "SAMPLE_TEXT";
    private static final String UPDATED_SUPPART = "UPDATED_TEXT";
    private static final String DEFAULT_REMARKS = "SAMPLE_TEXT";
    private static final String UPDATED_REMARKS = "UPDATED_TEXT";

    @Inject
    private ItemmfrpartRepository itemmfrpartRepository;

    private MockMvc restItemmfrpartMockMvc;

    private Itemmfrpart itemmfrpart;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemmfrpartResource itemmfrpartResource = new ItemmfrpartResource();
        ReflectionTestUtils.setField(itemmfrpartResource, "itemmfrpartRepository", itemmfrpartRepository);
        this.restItemmfrpartMockMvc = MockMvcBuilders.standaloneSetup(itemmfrpartResource).build();
    }

    @Before
    public void initTest() {
        itemmfrpart = new Itemmfrpart();
        itemmfrpart.setMfrpart(DEFAULT_MFRPART);
        itemmfrpart.setStatus(DEFAULT_STATUS);
        itemmfrpart.setSuppart(DEFAULT_SUPPART);
        itemmfrpart.setRemarks(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createItemmfrpart() throws Exception {
        // Validate the database is empty
        assertThat(itemmfrpartRepository.findAll()).hasSize(0);

        // Create the Itemmfrpart
        restItemmfrpartMockMvc.perform(post("/api/itemmfrparts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemmfrpart)))
                .andExpect(status().isOk());

        // Validate the Itemmfrpart in the database
        List<Itemmfrpart> itemmfrparts = itemmfrpartRepository.findAll();
        assertThat(itemmfrparts).hasSize(1);
        Itemmfrpart testItemmfrpart = itemmfrparts.iterator().next();
        assertThat(testItemmfrpart.getMfrpart()).isEqualTo(DEFAULT_MFRPART);
        assertThat(testItemmfrpart.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testItemmfrpart.getSuppart()).isEqualTo(DEFAULT_SUPPART);
        assertThat(testItemmfrpart.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void getAllItemmfrparts() throws Exception {
        // Initialize the database
        itemmfrpartRepository.saveAndFlush(itemmfrpart);

        // Get all the itemmfrparts
        restItemmfrpartMockMvc.perform(get("/api/itemmfrparts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(itemmfrpart.getId().intValue()))
                .andExpect(jsonPath("$.[0].mfrpart").value(DEFAULT_MFRPART.toString()))
                .andExpect(jsonPath("$.[0].status").value(DEFAULT_STATUS.toString()))
                .andExpect(jsonPath("$.[0].suppart").value(DEFAULT_SUPPART.toString()))
                .andExpect(jsonPath("$.[0].remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getItemmfrpart() throws Exception {
        // Initialize the database
        itemmfrpartRepository.saveAndFlush(itemmfrpart);

        // Get the itemmfrpart
        restItemmfrpartMockMvc.perform(get("/api/itemmfrparts/{id}", itemmfrpart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(itemmfrpart.getId().intValue()))
            .andExpect(jsonPath("$.mfrpart").value(DEFAULT_MFRPART.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.suppart").value(DEFAULT_SUPPART.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingItemmfrpart() throws Exception {
        // Get the itemmfrpart
        restItemmfrpartMockMvc.perform(get("/api/itemmfrparts/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemmfrpart() throws Exception {
        // Initialize the database
        itemmfrpartRepository.saveAndFlush(itemmfrpart);

        // Update the itemmfrpart
        itemmfrpart.setMfrpart(UPDATED_MFRPART);
        itemmfrpart.setStatus(UPDATED_STATUS);
        itemmfrpart.setSuppart(UPDATED_SUPPART);
        itemmfrpart.setRemarks(UPDATED_REMARKS);
        restItemmfrpartMockMvc.perform(post("/api/itemmfrparts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemmfrpart)))
                .andExpect(status().isOk());

        // Validate the Itemmfrpart in the database
        List<Itemmfrpart> itemmfrparts = itemmfrpartRepository.findAll();
        assertThat(itemmfrparts).hasSize(1);
        Itemmfrpart testItemmfrpart = itemmfrparts.iterator().next();
        assertThat(testItemmfrpart.getMfrpart()).isEqualTo(UPDATED_MFRPART);
        assertThat(testItemmfrpart.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testItemmfrpart.getSuppart()).isEqualTo(UPDATED_SUPPART);
        assertThat(testItemmfrpart.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void deleteItemmfrpart() throws Exception {
        // Initialize the database
        itemmfrpartRepository.saveAndFlush(itemmfrpart);

        // Get the itemmfrpart
        restItemmfrpartMockMvc.perform(delete("/api/itemmfrparts/{id}", itemmfrpart.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Itemmfrpart> itemmfrparts = itemmfrpartRepository.findAll();
        assertThat(itemmfrparts).hasSize(0);
    }
}
