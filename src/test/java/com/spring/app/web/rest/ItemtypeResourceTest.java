package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Itemtype;
import com.spring.app.repository.ItemtypeRepository;

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

    private static final Boolean DEFAULT_ISENABLED = false;
    private static final Boolean UPDATED_ISENABLED = true;

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
        itemtype.setIsenabled(DEFAULT_ISENABLED);
    }

    @Test
    @Transactional
    public void createItemtype() throws Exception {
        int databaseSizeBeforeCreate = itemtypeRepository.findAll().size();

        // Create the Itemtype
        restItemtypeMockMvc.perform(post("/api/itemtypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemtype)))
                .andExpect(status().isCreated());

        // Validate the Itemtype in the database
        List<Itemtype> itemtypes = itemtypeRepository.findAll();
        assertThat(itemtypes).hasSize(databaseSizeBeforeCreate + 1);
        Itemtype testItemtype = itemtypes.get(itemtypes.size() - 1);
        assertThat(testItemtype.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItemtype.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testItemtype.getIsenabled()).isEqualTo(DEFAULT_ISENABLED);
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
                .andExpect(jsonPath("$.[*].id").value(hasItem(itemtype.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].isenabled").value(hasItem(DEFAULT_ISENABLED.booleanValue())));
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
            .andExpect(jsonPath("$.isenabled").value(DEFAULT_ISENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemtype() throws Exception {
        // Get the itemtype
        restItemtypeMockMvc.perform(get("/api/itemtypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemtype() throws Exception {
        // Initialize the database
        itemtypeRepository.saveAndFlush(itemtype);

		int databaseSizeBeforeUpdate = itemtypeRepository.findAll().size();

        // Update the itemtype
        itemtype.setName(UPDATED_NAME);
        itemtype.setDescription(UPDATED_DESCRIPTION);
        itemtype.setIsenabled(UPDATED_ISENABLED);
        restItemtypeMockMvc.perform(put("/api/itemtypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemtype)))
                .andExpect(status().isOk());

        // Validate the Itemtype in the database
        List<Itemtype> itemtypes = itemtypeRepository.findAll();
        assertThat(itemtypes).hasSize(databaseSizeBeforeUpdate);
        Itemtype testItemtype = itemtypes.get(itemtypes.size() - 1);
        assertThat(testItemtype.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItemtype.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testItemtype.getIsenabled()).isEqualTo(UPDATED_ISENABLED);
    }

    @Test
    @Transactional
    public void deleteItemtype() throws Exception {
        // Initialize the database
        itemtypeRepository.saveAndFlush(itemtype);

		int databaseSizeBeforeDelete = itemtypeRepository.findAll().size();

        // Get the itemtype
        restItemtypeMockMvc.perform(delete("/api/itemtypes/{id}", itemtype.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Itemtype> itemtypes = itemtypeRepository.findAll();
        assertThat(itemtypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
