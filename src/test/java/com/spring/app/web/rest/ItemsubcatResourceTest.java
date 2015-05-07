package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Itemsubcat;
import com.spring.app.repository.ItemsubcatRepository;

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
 * Test class for the ItemsubcatResource REST controller.
 *
 * @see ItemsubcatResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ItemsubcatResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Inject
    private ItemsubcatRepository itemsubcatRepository;

    private MockMvc restItemsubcatMockMvc;

    private Itemsubcat itemsubcat;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemsubcatResource itemsubcatResource = new ItemsubcatResource();
        ReflectionTestUtils.setField(itemsubcatResource, "itemsubcatRepository", itemsubcatRepository);
        this.restItemsubcatMockMvc = MockMvcBuilders.standaloneSetup(itemsubcatResource).build();
    }

    @Before
    public void initTest() {
        itemsubcat = new Itemsubcat();
        itemsubcat.setName(DEFAULT_NAME);
        itemsubcat.setDescription(DEFAULT_DESCRIPTION);
        itemsubcat.setEnabled(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void createItemsubcat() throws Exception {
        int databaseSizeBeforeCreate = itemsubcatRepository.findAll().size();

        // Create the Itemsubcat
        restItemsubcatMockMvc.perform(post("/api/itemsubcats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemsubcat)))
                .andExpect(status().isCreated());

        // Validate the Itemsubcat in the database
        List<Itemsubcat> itemsubcats = itemsubcatRepository.findAll();
        assertThat(itemsubcats).hasSize(databaseSizeBeforeCreate + 1);
        Itemsubcat testItemsubcat = itemsubcats.get(itemsubcats.size() - 1);
        assertThat(testItemsubcat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItemsubcat.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testItemsubcat.getEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void getAllItemsubcats() throws Exception {
        // Initialize the database
        itemsubcatRepository.saveAndFlush(itemsubcat);

        // Get all the itemsubcats
        restItemsubcatMockMvc.perform(get("/api/itemsubcats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(itemsubcat.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getItemsubcat() throws Exception {
        // Initialize the database
        itemsubcatRepository.saveAndFlush(itemsubcat);

        // Get the itemsubcat
        restItemsubcatMockMvc.perform(get("/api/itemsubcats/{id}", itemsubcat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(itemsubcat.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemsubcat() throws Exception {
        // Get the itemsubcat
        restItemsubcatMockMvc.perform(get("/api/itemsubcats/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemsubcat() throws Exception {
        // Initialize the database
        itemsubcatRepository.saveAndFlush(itemsubcat);

		int databaseSizeBeforeUpdate = itemsubcatRepository.findAll().size();

        // Update the itemsubcat
        itemsubcat.setName(UPDATED_NAME);
        itemsubcat.setDescription(UPDATED_DESCRIPTION);
        itemsubcat.setEnabled(UPDATED_ENABLED);
        restItemsubcatMockMvc.perform(put("/api/itemsubcats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemsubcat)))
                .andExpect(status().isOk());

        // Validate the Itemsubcat in the database
        List<Itemsubcat> itemsubcats = itemsubcatRepository.findAll();
        assertThat(itemsubcats).hasSize(databaseSizeBeforeUpdate);
        Itemsubcat testItemsubcat = itemsubcats.get(itemsubcats.size() - 1);
        assertThat(testItemsubcat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItemsubcat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testItemsubcat.getEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void deleteItemsubcat() throws Exception {
        // Initialize the database
        itemsubcatRepository.saveAndFlush(itemsubcat);

		int databaseSizeBeforeDelete = itemsubcatRepository.findAll().size();

        // Get the itemsubcat
        restItemsubcatMockMvc.perform(delete("/api/itemsubcats/{id}", itemsubcat.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Itemsubcat> itemsubcats = itemsubcatRepository.findAll();
        assertThat(itemsubcats).hasSize(databaseSizeBeforeDelete - 1);
    }
}
