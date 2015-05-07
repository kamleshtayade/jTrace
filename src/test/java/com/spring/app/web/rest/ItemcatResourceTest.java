package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Itemcat;
import com.spring.app.repository.ItemcatRepository;

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
 * Test class for the ItemcatResource REST controller.
 *
 * @see ItemcatResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ItemcatResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Inject
    private ItemcatRepository itemcatRepository;

    private MockMvc restItemcatMockMvc;

    private Itemcat itemcat;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemcatResource itemcatResource = new ItemcatResource();
        ReflectionTestUtils.setField(itemcatResource, "itemcatRepository", itemcatRepository);
        this.restItemcatMockMvc = MockMvcBuilders.standaloneSetup(itemcatResource).build();
    }

    @Before
    public void initTest() {
        itemcat = new Itemcat();
        itemcat.setName(DEFAULT_NAME);
        itemcat.setDescription(DEFAULT_DESCRIPTION);
        itemcat.setEnabled(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void createItemcat() throws Exception {
        int databaseSizeBeforeCreate = itemcatRepository.findAll().size();

        // Create the Itemcat
        restItemcatMockMvc.perform(post("/api/itemcats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemcat)))
                .andExpect(status().isCreated());

        // Validate the Itemcat in the database
        List<Itemcat> itemcats = itemcatRepository.findAll();
        assertThat(itemcats).hasSize(databaseSizeBeforeCreate + 1);
        Itemcat testItemcat = itemcats.get(itemcats.size() - 1);
        assertThat(testItemcat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItemcat.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testItemcat.getEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void getAllItemcats() throws Exception {
        // Initialize the database
        itemcatRepository.saveAndFlush(itemcat);

        // Get all the itemcats
        restItemcatMockMvc.perform(get("/api/itemcats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(itemcat.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getItemcat() throws Exception {
        // Initialize the database
        itemcatRepository.saveAndFlush(itemcat);

        // Get the itemcat
        restItemcatMockMvc.perform(get("/api/itemcats/{id}", itemcat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(itemcat.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemcat() throws Exception {
        // Get the itemcat
        restItemcatMockMvc.perform(get("/api/itemcats/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemcat() throws Exception {
        // Initialize the database
        itemcatRepository.saveAndFlush(itemcat);

		int databaseSizeBeforeUpdate = itemcatRepository.findAll().size();

        // Update the itemcat
        itemcat.setName(UPDATED_NAME);
        itemcat.setDescription(UPDATED_DESCRIPTION);
        itemcat.setEnabled(UPDATED_ENABLED);
        restItemcatMockMvc.perform(put("/api/itemcats")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemcat)))
                .andExpect(status().isOk());

        // Validate the Itemcat in the database
        List<Itemcat> itemcats = itemcatRepository.findAll();
        assertThat(itemcats).hasSize(databaseSizeBeforeUpdate);
        Itemcat testItemcat = itemcats.get(itemcats.size() - 1);
        assertThat(testItemcat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItemcat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testItemcat.getEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void deleteItemcat() throws Exception {
        // Initialize the database
        itemcatRepository.saveAndFlush(itemcat);

		int databaseSizeBeforeDelete = itemcatRepository.findAll().size();

        // Get the itemcat
        restItemcatMockMvc.perform(delete("/api/itemcats/{id}", itemcat.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Itemcat> itemcats = itemcatRepository.findAll();
        assertThat(itemcats).hasSize(databaseSizeBeforeDelete - 1);
    }
}
