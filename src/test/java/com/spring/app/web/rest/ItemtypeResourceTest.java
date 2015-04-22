package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Itemtype;
import com.spring.app.repository.ItemtypeRepository;

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
 * Test class for the ItemtypeResource REST controller.
 *
 * @see ItemtypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ItemtypeResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Inject
    private ItemtypeRepository itemtypeRepository;

    private MockMvc restItemtypeMockMvc;

    private Itemtype itemtype;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemtypeResource itemtypeResource = new ItemtypeResource();
        ReflectionTestUtils.setField(itemtypeResource, "itemtypeRepository", itemtypeRepository);
        this.restItemtypeMockMvc = MockMvcBuilders.standaloneSetup(itemtypeResource).build();
    }

    @Before
    public void initTest() {
        itemtype = new Itemtype();
        itemtype.setName(DEFAULT_NAME);
        itemtype.setDescription(DEFAULT_DESCRIPTION);
        itemtype.setEnabled(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void createItemtype() throws Exception {
        // Validate the database is empty
        assertThat(itemtypeRepository.findAll()).hasSize(0);

        // Create the Itemtype
        restItemtypeMockMvc.perform(post("/api/itemtypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemtype)))
                .andExpect(status().isOk());

        // Validate the Itemtype in the database
        List<Itemtype> itemtypes = itemtypeRepository.findAll();
        assertThat(itemtypes).hasSize(1);
        Itemtype testItemtype = itemtypes.iterator().next();
        assertThat(testItemtype.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItemtype.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testItemtype.getEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void getAllItemtypes() throws Exception {
        // Initialize the database
        itemtypeRepository.saveAndFlush(itemtype);

        // Get all the itemtypes
        restItemtypeMockMvc.perform(get("/api/itemtypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(itemtype.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getItemtype() throws Exception {
        // Initialize the database
        itemtypeRepository.saveAndFlush(itemtype);

        // Get the itemtype
        restItemtypeMockMvc.perform(get("/api/itemtypes/{id}", itemtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(itemtype.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemtype() throws Exception {
        // Get the itemtype
        restItemtypeMockMvc.perform(get("/api/itemtypes/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemtype() throws Exception {
        // Initialize the database
        itemtypeRepository.saveAndFlush(itemtype);

        // Update the itemtype
        itemtype.setName(UPDATED_NAME);
        itemtype.setDescription(UPDATED_DESCRIPTION);
        itemtype.setEnabled(UPDATED_ENABLED);
        restItemtypeMockMvc.perform(post("/api/itemtypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemtype)))
                .andExpect(status().isOk());

        // Validate the Itemtype in the database
        List<Itemtype> itemtypes = itemtypeRepository.findAll();
        assertThat(itemtypes).hasSize(1);
        Itemtype testItemtype = itemtypes.iterator().next();
        assertThat(testItemtype.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItemtype.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testItemtype.getEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void deleteItemtype() throws Exception {
        // Initialize the database
        itemtypeRepository.saveAndFlush(itemtype);

        // Get the itemtype
        restItemtypeMockMvc.perform(delete("/api/itemtypes/{id}", itemtype.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Itemtype> itemtypes = itemtypeRepository.findAll();
        assertThat(itemtypes).hasSize(0);
    }
}
