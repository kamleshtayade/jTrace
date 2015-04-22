package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Itemctn;
import com.spring.app.repository.ItemctnRepository;

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
    private static final String DEFAULT_SR_NO_FROM = "SAMPLE_TEXT";
    private static final String UPDATED_SR_NO_FROM = "UPDATED_TEXT";

    private static final Integer DEFAULT_REQD_QTY = 0;
    private static final Integer UPDATED_REQD_QTY = 1;

    private static final LocalDate DEFAULT_RECD_DT = new LocalDate(0L);
    private static final LocalDate UPDATED_RECD_DT = new LocalDate();
    private static final String DEFAULT_ITEM = "SAMPLE_TEXT";
    private static final String UPDATED_ITEM = "UPDATED_TEXT";
    private static final String DEFAULT_SR_NO_TO = "SAMPLE_TEXT";
    private static final String UPDATED_SR_NO_TO = "UPDATED_TEXT";
    private static final String DEFAULT_SELF_LIFE = "SAMPLE_TEXT";
    private static final String UPDATED_SELF_LIFE = "UPDATED_TEXT";

    private static final Integer DEFAULT_PO_QTY = 0;
    private static final Integer UPDATED_PO_QTY = 1;
    private static final String DEFAULT_INVOICE = "SAMPLE_TEXT";
    private static final String UPDATED_INVOICE = "UPDATED_TEXT";
    private static final String DEFAULT_MANUFATURER = "SAMPLE_TEXT";
    private static final String UPDATED_MANUFATURER = "UPDATED_TEXT";
    private static final String DEFAULT_MFG_PART_NO = "SAMPLE_TEXT";
    private static final String UPDATED_MFG_PART_NO = "UPDATED_TEXT";
    private static final String DEFAULT_SUPPLIER = "SAMPLE_TEXT";
    private static final String UPDATED_SUPPLIER = "UPDATED_TEXT";
    private static final String DEFAULT_DATE_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_DATE_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_LOT_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_LOT_CODE = "UPDATED_TEXT";

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
        itemctn.setSrNoFrom(DEFAULT_SR_NO_FROM);
        itemctn.setReqdQty(DEFAULT_REQD_QTY);
        itemctn.setRecdDt(DEFAULT_RECD_DT);
        itemctn.setItem(DEFAULT_ITEM);
        itemctn.setSrNoTo(DEFAULT_SR_NO_TO);
        itemctn.setSelfLife(DEFAULT_SELF_LIFE);
        itemctn.setPoQty(DEFAULT_PO_QTY);
        itemctn.setInvoice(DEFAULT_INVOICE);
        itemctn.setManufaturer(DEFAULT_MANUFATURER);
        itemctn.setMfgPartNo(DEFAULT_MFG_PART_NO);
        itemctn.setSupplier(DEFAULT_SUPPLIER);
        itemctn.setDateCode(DEFAULT_DATE_CODE);
        itemctn.setLotCode(DEFAULT_LOT_CODE);
    }

    @Test
    @Transactional
    public void createItemctn() throws Exception {
        // Validate the database is empty
        assertThat(itemctnRepository.findAll()).hasSize(0);

        // Create the Itemctn
        restItemctnMockMvc.perform(post("/api/itemctns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemctn)))
                .andExpect(status().isOk());

        // Validate the Itemctn in the database
        List<Itemctn> itemctns = itemctnRepository.findAll();
        assertThat(itemctns).hasSize(1);
        Itemctn testItemctn = itemctns.iterator().next();
        assertThat(testItemctn.getCtn()).isEqualTo(DEFAULT_CTN);
        assertThat(testItemctn.getSrNoFrom()).isEqualTo(DEFAULT_SR_NO_FROM);
        assertThat(testItemctn.getReqdQty()).isEqualTo(DEFAULT_REQD_QTY);
        assertThat(testItemctn.getRecdDt()).isEqualTo(DEFAULT_RECD_DT);
        assertThat(testItemctn.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testItemctn.getSrNoTo()).isEqualTo(DEFAULT_SR_NO_TO);
        assertThat(testItemctn.getSelfLife()).isEqualTo(DEFAULT_SELF_LIFE);
        assertThat(testItemctn.getPoQty()).isEqualTo(DEFAULT_PO_QTY);
        assertThat(testItemctn.getInvoice()).isEqualTo(DEFAULT_INVOICE);
        assertThat(testItemctn.getManufaturer()).isEqualTo(DEFAULT_MANUFATURER);
        assertThat(testItemctn.getMfgPartNo()).isEqualTo(DEFAULT_MFG_PART_NO);
        assertThat(testItemctn.getSupplier()).isEqualTo(DEFAULT_SUPPLIER);
        assertThat(testItemctn.getDateCode()).isEqualTo(DEFAULT_DATE_CODE);
        assertThat(testItemctn.getLotCode()).isEqualTo(DEFAULT_LOT_CODE);
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
                .andExpect(jsonPath("$.[0].id").value(itemctn.getId().intValue()))
                .andExpect(jsonPath("$.[0].ctn").value(DEFAULT_CTN.toString()))
                .andExpect(jsonPath("$.[0].srNoFrom").value(DEFAULT_SR_NO_FROM.toString()))
                .andExpect(jsonPath("$.[0].reqdQty").value(DEFAULT_REQD_QTY))
                .andExpect(jsonPath("$.[0].recdDt").value(DEFAULT_RECD_DT.toString()))
                .andExpect(jsonPath("$.[0].item").value(DEFAULT_ITEM.toString()))
                .andExpect(jsonPath("$.[0].srNoTo").value(DEFAULT_SR_NO_TO.toString()))
                .andExpect(jsonPath("$.[0].selfLife").value(DEFAULT_SELF_LIFE.toString()))
                .andExpect(jsonPath("$.[0].poQty").value(DEFAULT_PO_QTY))
                .andExpect(jsonPath("$.[0].invoice").value(DEFAULT_INVOICE.toString()))
                .andExpect(jsonPath("$.[0].manufaturer").value(DEFAULT_MANUFATURER.toString()))
                .andExpect(jsonPath("$.[0].mfgPartNo").value(DEFAULT_MFG_PART_NO.toString()))
                .andExpect(jsonPath("$.[0].supplier").value(DEFAULT_SUPPLIER.toString()))
                .andExpect(jsonPath("$.[0].dateCode").value(DEFAULT_DATE_CODE.toString()))
                .andExpect(jsonPath("$.[0].lotCode").value(DEFAULT_LOT_CODE.toString()));
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
            .andExpect(jsonPath("$.srNoFrom").value(DEFAULT_SR_NO_FROM.toString()))
            .andExpect(jsonPath("$.reqdQty").value(DEFAULT_REQD_QTY))
            .andExpect(jsonPath("$.recdDt").value(DEFAULT_RECD_DT.toString()))
            .andExpect(jsonPath("$.item").value(DEFAULT_ITEM.toString()))
            .andExpect(jsonPath("$.srNoTo").value(DEFAULT_SR_NO_TO.toString()))
            .andExpect(jsonPath("$.selfLife").value(DEFAULT_SELF_LIFE.toString()))
            .andExpect(jsonPath("$.poQty").value(DEFAULT_PO_QTY))
            .andExpect(jsonPath("$.invoice").value(DEFAULT_INVOICE.toString()))
            .andExpect(jsonPath("$.manufaturer").value(DEFAULT_MANUFATURER.toString()))
            .andExpect(jsonPath("$.mfgPartNo").value(DEFAULT_MFG_PART_NO.toString()))
            .andExpect(jsonPath("$.supplier").value(DEFAULT_SUPPLIER.toString()))
            .andExpect(jsonPath("$.dateCode").value(DEFAULT_DATE_CODE.toString()))
            .andExpect(jsonPath("$.lotCode").value(DEFAULT_LOT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingItemctn() throws Exception {
        // Get the itemctn
        restItemctnMockMvc.perform(get("/api/itemctns/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemctn() throws Exception {
        // Initialize the database
        itemctnRepository.saveAndFlush(itemctn);

        // Update the itemctn
        itemctn.setCtn(UPDATED_CTN);
        itemctn.setSrNoFrom(UPDATED_SR_NO_FROM);
        itemctn.setReqdQty(UPDATED_REQD_QTY);
        itemctn.setRecdDt(UPDATED_RECD_DT);
        itemctn.setItem(UPDATED_ITEM);
        itemctn.setSrNoTo(UPDATED_SR_NO_TO);
        itemctn.setSelfLife(UPDATED_SELF_LIFE);
        itemctn.setPoQty(UPDATED_PO_QTY);
        itemctn.setInvoice(UPDATED_INVOICE);
        itemctn.setManufaturer(UPDATED_MANUFATURER);
        itemctn.setMfgPartNo(UPDATED_MFG_PART_NO);
        itemctn.setSupplier(UPDATED_SUPPLIER);
        itemctn.setDateCode(UPDATED_DATE_CODE);
        itemctn.setLotCode(UPDATED_LOT_CODE);
        restItemctnMockMvc.perform(post("/api/itemctns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemctn)))
                .andExpect(status().isOk());

        // Validate the Itemctn in the database
        List<Itemctn> itemctns = itemctnRepository.findAll();
        assertThat(itemctns).hasSize(1);
        Itemctn testItemctn = itemctns.iterator().next();
        assertThat(testItemctn.getCtn()).isEqualTo(UPDATED_CTN);
        assertThat(testItemctn.getSrNoFrom()).isEqualTo(UPDATED_SR_NO_FROM);
        assertThat(testItemctn.getReqdQty()).isEqualTo(UPDATED_REQD_QTY);
        assertThat(testItemctn.getRecdDt()).isEqualTo(UPDATED_RECD_DT);
        assertThat(testItemctn.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testItemctn.getSrNoTo()).isEqualTo(UPDATED_SR_NO_TO);
        assertThat(testItemctn.getSelfLife()).isEqualTo(UPDATED_SELF_LIFE);
        assertThat(testItemctn.getPoQty()).isEqualTo(UPDATED_PO_QTY);
        assertThat(testItemctn.getInvoice()).isEqualTo(UPDATED_INVOICE);
        assertThat(testItemctn.getManufaturer()).isEqualTo(UPDATED_MANUFATURER);
        assertThat(testItemctn.getMfgPartNo()).isEqualTo(UPDATED_MFG_PART_NO);
        assertThat(testItemctn.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
        assertThat(testItemctn.getDateCode()).isEqualTo(UPDATED_DATE_CODE);
        assertThat(testItemctn.getLotCode()).isEqualTo(UPDATED_LOT_CODE);
    }

    @Test
    @Transactional
    public void deleteItemctn() throws Exception {
        // Initialize the database
        itemctnRepository.saveAndFlush(itemctn);

        // Get the itemctn
        restItemctnMockMvc.perform(delete("/api/itemctns/{id}", itemctn.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Itemctn> itemctns = itemctnRepository.findAll();
        assertThat(itemctns).hasSize(0);
    }
}
