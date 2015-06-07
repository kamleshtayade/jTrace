package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Itemctn;
import com.spring.app.repository.ItemctnRepository;

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
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ItemctnResource REST controller.
 *
 * @see ItemctnResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ItemctnResourceTest {

    private static final String DEFAULT_CTN = "SAMPLE_TEXT";
    private static final String UPDATED_CTN = "UPDATED_TEXT";

    private static final Integer DEFAULT_REQD_QTY = 0;
    private static final Integer UPDATED_REQD_QTY = 1;

    private static final Integer DEFAULT_PO_QTY = 0;
    private static final Integer UPDATED_PO_QTY = 1;
    
    private static final LocalDate DEFAULT_RECD_DT = new LocalDate(0L);
    private static final LocalDate UPDATED_RECD_DT = new LocalDate();
    private static final String DEFAULT_SR_NO_TO = "SAMPLE_TEXT";
    private static final String UPDATED_SR_NO_TO = "UPDATED_TEXT";
    private static final String DEFAULT_SELF_LIFE = "SAMPLE_TEXT";
    private static final String UPDATED_SELF_LIFE = "UPDATED_TEXT";
    private static final String DEFAULT_INVOICE = "SAMPLE_TEXT";
    private static final String UPDATED_INVOICE = "UPDATED_TEXT";

    @Inject
    private ItemctnRepository itemctnRepository;

    private MockMvc restItemctnMockMvc;

    private Itemctn itemctn;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemctnResource itemctnResource = new ItemctnResource();
        ReflectionTestUtils.setField(itemctnResource, "itemctnRepository", itemctnRepository);
        this.restItemctnMockMvc = MockMvcBuilders.standaloneSetup(itemctnResource).build();
    }

    @Before
    public void initTest() {
        itemctn = new Itemctn();
        itemctn.setCtn(DEFAULT_CTN);
        itemctn.setReqdQty(DEFAULT_REQD_QTY);
        itemctn.setRecdDt(DEFAULT_RECD_DT);
        itemctn.setSrNoTo(DEFAULT_SR_NO_TO);
        itemctn.setSelfLife(DEFAULT_SELF_LIFE);
        itemctn.setPoQty(DEFAULT_PO_QTY);
        itemctn.setInvoice(DEFAULT_INVOICE);
    }

    @Test
    @Transactional
    public void createItemctn() throws Exception {
        int databaseSizeBeforeCreate = itemctnRepository.findAll().size();

        // Create the Itemctn
        restItemctnMockMvc.perform(post("/api/itemctns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemctn)))
                .andExpect(status().isCreated());

        // Validate the Itemctn in the database
        List<Itemctn> itemctns = itemctnRepository.findAll();
        assertThat(itemctns).hasSize(databaseSizeBeforeCreate + 1);
        Itemctn testItemctn = itemctns.get(itemctns.size() - 1);
        assertThat(testItemctn.getCtn()).isEqualTo(DEFAULT_CTN);
        assertThat(testItemctn.getReqdQty()).isEqualTo(DEFAULT_REQD_QTY);
        assertThat(testItemctn.getRecdDt()).isEqualTo(DEFAULT_RECD_DT);
        assertThat(testItemctn.getSrNoTo()).isEqualTo(DEFAULT_SR_NO_TO);
        assertThat(testItemctn.getSelfLife()).isEqualTo(DEFAULT_SELF_LIFE);
        assertThat(testItemctn.getPoQty()).isEqualTo(DEFAULT_PO_QTY);
        assertThat(testItemctn.getInvoice()).isEqualTo(DEFAULT_INVOICE);
    }

    @Test
    @Transactional
    public void checkCtnIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(itemctnRepository.findAll()).hasSize(0);
        // set the field null
        itemctn.setCtn(null);

        // Create the Itemctn, which fails.
        restItemctnMockMvc.perform(post("/api/itemctns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemctn)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Itemctn> itemctns = itemctnRepository.findAll();
        assertThat(itemctns).hasSize(0);
    }

    @Test
    @Transactional
    public void checkReqdQtyIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(itemctnRepository.findAll()).hasSize(0);
        // set the field null
        itemctn.setReqdQty(null);

        // Create the Itemctn, which fails.
        restItemctnMockMvc.perform(post("/api/itemctns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemctn)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Itemctn> itemctns = itemctnRepository.findAll();
        assertThat(itemctns).hasSize(0);
    }

    @Test
    @Transactional
    public void checkRecdDtIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(itemctnRepository.findAll()).hasSize(0);
        // set the field null
        itemctn.setRecdDt(null);

        // Create the Itemctn, which fails.
        restItemctnMockMvc.perform(post("/api/itemctns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemctn)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Itemctn> itemctns = itemctnRepository.findAll();
        assertThat(itemctns).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllItemctns() throws Exception {
        // Initialize the database
        itemctnRepository.saveAndFlush(itemctn);

        // Get all the itemctns
        restItemctnMockMvc.perform(get("/api/itemctns"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(itemctn.getId().intValue())))
                .andExpect(jsonPath("$.[*].ctn").value(hasItem(DEFAULT_CTN.toString())))
                .andExpect(jsonPath("$.[*].reqdQty").value(hasItem(DEFAULT_REQD_QTY)))
                .andExpect(jsonPath("$.[*].recdDt").value(hasItem(DEFAULT_RECD_DT.toString())))
                .andExpect(jsonPath("$.[*].srNoTo").value(hasItem(DEFAULT_SR_NO_TO.toString())))
                .andExpect(jsonPath("$.[*].selfLife").value(hasItem(DEFAULT_SELF_LIFE.toString())))
                .andExpect(jsonPath("$.[*].poQty").value(hasItem(DEFAULT_PO_QTY)))
                .andExpect(jsonPath("$.[*].invoice").value(hasItem(DEFAULT_INVOICE.toString())));
    }

    @Test
    @Transactional
    public void getItemctn() throws Exception {
        // Initialize the database
        itemctnRepository.saveAndFlush(itemctn);

        // Get the itemctn
        restItemctnMockMvc.perform(get("/api/itemctns/{id}", itemctn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(itemctn.getId().intValue()))
            .andExpect(jsonPath("$.ctn").value(DEFAULT_CTN.toString()))
            .andExpect(jsonPath("$.reqdQty").value(DEFAULT_REQD_QTY))
            .andExpect(jsonPath("$.recdDt").value(DEFAULT_RECD_DT.toString()))
            .andExpect(jsonPath("$.srNoTo").value(DEFAULT_SR_NO_TO.toString()))
            .andExpect(jsonPath("$.selfLife").value(DEFAULT_SELF_LIFE.toString()))
            .andExpect(jsonPath("$.poQty").value(DEFAULT_PO_QTY))
            .andExpect(jsonPath("$.invoice").value(DEFAULT_INVOICE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingItemctn() throws Exception {
        // Get the itemctn
        restItemctnMockMvc.perform(get("/api/itemctns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemctn() throws Exception {
        // Initialize the database
        itemctnRepository.saveAndFlush(itemctn);

		int databaseSizeBeforeUpdate = itemctnRepository.findAll().size();

        // Update the itemctn
        itemctn.setCtn(UPDATED_CTN);
        itemctn.setReqdQty(UPDATED_REQD_QTY);
        itemctn.setRecdDt(UPDATED_RECD_DT);
        itemctn.setSrNoTo(UPDATED_SR_NO_TO);
        itemctn.setSelfLife(UPDATED_SELF_LIFE);
        itemctn.setPoQty(UPDATED_PO_QTY);
        itemctn.setInvoice(UPDATED_INVOICE);
        restItemctnMockMvc.perform(put("/api/itemctns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemctn)))
                .andExpect(status().isOk());

        // Validate the Itemctn in the database
        List<Itemctn> itemctns = itemctnRepository.findAll();
        assertThat(itemctns).hasSize(databaseSizeBeforeUpdate);
        Itemctn testItemctn = itemctns.get(itemctns.size() - 1);
        assertThat(testItemctn.getCtn()).isEqualTo(UPDATED_CTN);
        assertThat(testItemctn.getReqdQty()).isEqualTo(UPDATED_REQD_QTY);
        assertThat(testItemctn.getRecdDt()).isEqualTo(UPDATED_RECD_DT);
        assertThat(testItemctn.getSrNoTo()).isEqualTo(UPDATED_SR_NO_TO);
        assertThat(testItemctn.getSelfLife()).isEqualTo(UPDATED_SELF_LIFE);
        assertThat(testItemctn.getPoQty()).isEqualTo(UPDATED_PO_QTY);
        assertThat(testItemctn.getInvoice()).isEqualTo(UPDATED_INVOICE);
    }

    @Test
    @Transactional
    public void deleteItemctn() throws Exception {
        // Initialize the database
        itemctnRepository.saveAndFlush(itemctn);

		int databaseSizeBeforeDelete = itemctnRepository.findAll().size();

        // Get the itemctn
        restItemctnMockMvc.perform(delete("/api/itemctns/{id}", itemctn.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Itemctn> itemctns = itemctnRepository.findAll();
        assertThat(itemctns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
