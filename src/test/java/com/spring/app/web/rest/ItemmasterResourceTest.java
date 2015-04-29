package com.spring.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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

import com.spring.app.Application;
import com.spring.app.domain.Itemmaster;
import com.spring.app.repository.ItemmasterRepository;
import com.spring.app.web.rest.TestUtil;

/**
 * Test class for the ItemmasterResource REST controller.
 *
 * @see ItemmasterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ItemmasterResourceTest {

    private static final String DEFAULT_ITEM_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_ITEM_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_ATTRIBUTES = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTES = "UPDATED_TEXT";
    private static final String DEFAULT_IDCAT = "SAMPLE_TEXT";
    private static final String UPDATED_IDCAT = "UPDATED_TEXT";

    @Inject
    private ItemmasterRepository itemmasterRepository;

    private MockMvc restItemmasterMockMvc;

    private Itemmaster itemmaster;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemmasterResource itemmasterResource = new ItemmasterResource();
        ReflectionTestUtils.setField(itemmasterResource, "itemmasterRepository", itemmasterRepository);
        this.restItemmasterMockMvc = MockMvcBuilders.standaloneSetup(itemmasterResource).build();
    }

    @Before
    public void initTest() {
        itemmaster = new Itemmaster();
        itemmaster.setItemCode(DEFAULT_ITEM_CODE);
        itemmaster.setDescription(DEFAULT_DESCRIPTION);
        itemmaster.setAttributes(DEFAULT_ATTRIBUTES);
        itemmaster.setIdCat(DEFAULT_IDCAT);

    }

    @Test
    @Transactional
    public void createItemmaster() throws Exception {
        // Validate the database is empty
        assertThat(itemmasterRepository.findAll()).hasSize(0);

        // Create the Itemmaster
        restItemmasterMockMvc.perform(post("/api/itemmasters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemmaster)))
                .andExpect(status().isOk());

        // Validate the Itemmaster in the database
        List<Itemmaster> itemmasters = itemmasterRepository.findAll();
        assertThat(itemmasters).hasSize(1);
        Itemmaster testItemmaster = itemmasters.iterator().next();
        assertThat(testItemmaster.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testItemmaster.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testItemmaster.getAttributes()).isEqualTo(DEFAULT_ATTRIBUTES);
        assertThat(testItemmaster.getIdCat()).isEqualTo(DEFAULT_IDCAT);
    }

    @Test
    @Transactional
    public void getAllItemmasters() throws Exception {
        // Initialize the database
        itemmasterRepository.saveAndFlush(itemmaster);

        // Get all the itemmasters
        restItemmasterMockMvc.perform(get("/api/itemmasters"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(itemmaster.getId().intValue()))
                .andExpect(jsonPath("$.[0].itemCode").value(DEFAULT_ITEM_CODE.toString()))
                .andExpect(jsonPath("$.[0].description").value(DEFAULT_DESCRIPTION.toString()))
                .andExpect(jsonPath("$.[0].attributes").value(DEFAULT_ATTRIBUTES.toString()))
                .andExpect(jsonPath("$.[0].idCat").value(DEFAULT_IDCAT.toString()))
                .andExpect(jsonPath("$.[0].attributes").value(DEFAULT_ATTRIBUTES.toString()));
    }

    @Test
    @Transactional
    public void getItemmaster() throws Exception {
        // Initialize the database
        itemmasterRepository.saveAndFlush(itemmaster);

        // Get the itemmaster
        restItemmasterMockMvc.perform(get("/api/itemmasters/{id}", itemmaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(itemmaster.getId().intValue()))
            .andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.attributes").value(DEFAULT_ATTRIBUTES.toString()))
            .andExpect(jsonPath("$.idCat").value(DEFAULT_IDCAT.toString()))
        	.andExpect(jsonPath("$.attributes").value(DEFAULT_ATTRIBUTES.toString()));

    }

    @Test
    @Transactional
    public void getNonExistingItemmaster() throws Exception {
        // Get the itemmaster
        restItemmasterMockMvc.perform(get("/api/itemmasters/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemmaster() throws Exception {
        // Initialize the database
        itemmasterRepository.saveAndFlush(itemmaster);

        // Update the itemmaster
        itemmaster.setItemCode(UPDATED_ITEM_CODE);
        itemmaster.setDescription(UPDATED_DESCRIPTION);
        itemmaster.setAttributes(UPDATED_ATTRIBUTES);
        itemmaster.setIdCat(UPDATED_IDCAT);

        restItemmasterMockMvc.perform(post("/api/itemmasters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemmaster)))
                .andExpect(status().isOk());

        // Validate the Itemmaster in the database
        List<Itemmaster> itemmasters = itemmasterRepository.findAll();
        assertThat(itemmasters).hasSize(1);
        Itemmaster testItemmaster = itemmasters.iterator().next();
        assertThat(testItemmaster.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testItemmaster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testItemmaster.getAttributes()).isEqualTo(UPDATED_ATTRIBUTES);
        assertThat(testItemmaster.getIdCat()).isEqualTo(UPDATED_IDCAT);
    }

    @Test
    @Transactional
    public void deleteItemmaster() throws Exception {
        // Initialize the database
        itemmasterRepository.saveAndFlush(itemmaster);

        // Get the itemmaster
        restItemmasterMockMvc.perform(delete("/api/itemmasters/{id}", itemmaster.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Itemmaster> itemmasters = itemmasterRepository.findAll();
        assertThat(itemmasters).hasSize(0);
    }
}
