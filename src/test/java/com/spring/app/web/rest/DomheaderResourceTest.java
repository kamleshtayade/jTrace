package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Domheader;
import com.spring.app.repository.DomheaderRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DomheaderResource REST controller.
 *
 * @see DomheaderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DomheaderResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final Boolean DEFAULT_IS_AUTO = false;
    private static final Boolean UPDATED_IS_AUTO = true;

    private static final Integer DEFAULT_CYCLE_TIME = 0;
    private static final Integer UPDATED_CYCLE_TIME = 1;

    private static final Boolean DEFAULT_IS_MULTI = false;
    private static final Boolean UPDATED_IS_MULTI = true;

    private static final Integer DEFAULT_PANEL_QTY = 0;
    private static final Integer UPDATED_PANEL_QTY = 1;
    private static final String DEFAULT_OPR = "SAMPLE_TEXT";
    private static final String UPDATED_OPR = "UPDATED_TEXT";
    private static final String DEFAULT_SHIFTSUP = "SAMPLE_TEXT";
    private static final String UPDATED_SHIFTSUP = "UPDATED_TEXT";
    private static final String DEFAULT_SHIFT = "SAMPLE_TEXT";
    private static final String UPDATED_SHIFT = "UPDATED_TEXT";

    private static final DateTime DEFAULT_SHIFTSTART = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_SHIFTSTART = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_SHIFTSTART_STR = dateTimeFormatter.print(DEFAULT_SHIFTSTART);

    private static final DateTime DEFAULT_SHIFTEND = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_SHIFTEND = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_SHIFTEND_STR = dateTimeFormatter.print(DEFAULT_SHIFTEND);
    private static final String DEFAULT_SOLDER = "SAMPLE_TEXT";
    private static final String UPDATED_SOLDER = "UPDATED_TEXT";
    private static final String DEFAULT_JMXID = "SAMPLE_TEXT";
    private static final String UPDATED_JMXID = "UPDATED_TEXT";


    @Inject
    private DomheaderRepository domheaderRepository;

    private MockMvc restDomheaderMockMvc;

    private Domheader domheader;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DomheaderResource domheaderResource = new DomheaderResource();
        ReflectionTestUtils.setField(domheaderResource, "domheaderRepository", domheaderRepository);
        this.restDomheaderMockMvc = MockMvcBuilders.standaloneSetup(domheaderResource).build();
    }

    @Before
    public void initTest() {
        domheader = new Domheader();
        domheader.setIsAuto(DEFAULT_IS_AUTO);
        domheader.setCycleTime(DEFAULT_CYCLE_TIME);
        domheader.setIsMulti(DEFAULT_IS_MULTI);
        domheader.setPanelQty(DEFAULT_PANEL_QTY);
        domheader.setOpr(DEFAULT_OPR);
        domheader.setShiftsup(DEFAULT_SHIFTSUP);
        domheader.setShift(DEFAULT_SHIFT);
        domheader.setShiftstart(DEFAULT_SHIFTSTART);
        domheader.setShiftend(DEFAULT_SHIFTEND);
        domheader.setSolder(DEFAULT_SOLDER);
        domheader.setJmxid(DEFAULT_JMXID);
    }

    @Test
    @Transactional
    public void createDomheader() throws Exception {
        int databaseSizeBeforeCreate = domheaderRepository.findAll().size();

        // Create the Domheader
        restDomheaderMockMvc.perform(post("/api/domheaders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domheader)))
                .andExpect(status().isCreated());

        // Validate the Domheader in the database
        List<Domheader> domheaders = domheaderRepository.findAll();
        assertThat(domheaders).hasSize(databaseSizeBeforeCreate + 1);
        Domheader testDomheader = domheaders.get(domheaders.size() - 1);
        assertThat(testDomheader.getIsAuto()).isEqualTo(DEFAULT_IS_AUTO);
        assertThat(testDomheader.getCycleTime()).isEqualTo(DEFAULT_CYCLE_TIME);
        assertThat(testDomheader.getIsMulti()).isEqualTo(DEFAULT_IS_MULTI);
        assertThat(testDomheader.getPanelQty()).isEqualTo(DEFAULT_PANEL_QTY);
        assertThat(testDomheader.getOpr()).isEqualTo(DEFAULT_OPR);
        assertThat(testDomheader.getShiftsup()).isEqualTo(DEFAULT_SHIFTSUP);
        assertThat(testDomheader.getShift()).isEqualTo(DEFAULT_SHIFT);
        assertThat(testDomheader.getShiftstart().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_SHIFTSTART);
        assertThat(testDomheader.getShiftend().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_SHIFTEND);
        assertThat(testDomheader.getSolder()).isEqualTo(DEFAULT_SOLDER);
        assertThat(testDomheader.getJmxid()).isEqualTo(DEFAULT_JMXID);
    }

    @Test
    @Transactional
    public void getAllDomheaders() throws Exception {
        // Initialize the database
        domheaderRepository.saveAndFlush(domheader);

        // Get all the domheaders
        restDomheaderMockMvc.perform(get("/api/domheaders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(domheader.getId().intValue())))
                .andExpect(jsonPath("$.[*].isAuto").value(hasItem(DEFAULT_IS_AUTO.booleanValue())))
                .andExpect(jsonPath("$.[*].cycleTime").value(hasItem(DEFAULT_CYCLE_TIME)))
                .andExpect(jsonPath("$.[*].isMulti").value(hasItem(DEFAULT_IS_MULTI.booleanValue())))
                .andExpect(jsonPath("$.[*].panelQty").value(hasItem(DEFAULT_PANEL_QTY)))
                .andExpect(jsonPath("$.[*].opr").value(hasItem(DEFAULT_OPR.toString())))
                .andExpect(jsonPath("$.[*].shiftsup").value(hasItem(DEFAULT_SHIFTSUP.toString())))
                .andExpect(jsonPath("$.[*].shift").value(hasItem(DEFAULT_SHIFT.toString())))
                .andExpect(jsonPath("$.[*].shiftstart").value(hasItem(DEFAULT_SHIFTSTART_STR)))
                .andExpect(jsonPath("$.[*].shiftend").value(hasItem(DEFAULT_SHIFTEND_STR)))
                .andExpect(jsonPath("$.[*].solder").value(hasItem(DEFAULT_SOLDER.toString())))
                .andExpect(jsonPath("$.[*].jmxid").value(hasItem(DEFAULT_JMXID.toString())));
    }

    @Test
    @Transactional
    public void getDomheader() throws Exception {
        // Initialize the database
        domheaderRepository.saveAndFlush(domheader);

        // Get the domheader
        restDomheaderMockMvc.perform(get("/api/domheaders/{id}", domheader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(domheader.getId().intValue()))
            .andExpect(jsonPath("$.isAuto").value(DEFAULT_IS_AUTO.booleanValue()))
            .andExpect(jsonPath("$.cycleTime").value(DEFAULT_CYCLE_TIME))
            .andExpect(jsonPath("$.isMulti").value(DEFAULT_IS_MULTI.booleanValue()))
            .andExpect(jsonPath("$.panelQty").value(DEFAULT_PANEL_QTY))
            .andExpect(jsonPath("$.opr").value(DEFAULT_OPR.toString()))
            .andExpect(jsonPath("$.shiftsup").value(DEFAULT_SHIFTSUP.toString()))
            .andExpect(jsonPath("$.shift").value(DEFAULT_SHIFT.toString()))
            .andExpect(jsonPath("$.shiftstart").value(DEFAULT_SHIFTSTART_STR))
            .andExpect(jsonPath("$.shiftend").value(DEFAULT_SHIFTEND_STR))
            .andExpect(jsonPath("$.solder").value(DEFAULT_SOLDER.toString()))
            .andExpect(jsonPath("$.jmxid").value(DEFAULT_JMXID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDomheader() throws Exception {
        // Get the domheader
        restDomheaderMockMvc.perform(get("/api/domheaders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomheader() throws Exception {
        // Initialize the database
        domheaderRepository.saveAndFlush(domheader);

		int databaseSizeBeforeUpdate = domheaderRepository.findAll().size();

        // Update the domheader
        domheader.setIsAuto(UPDATED_IS_AUTO);
        domheader.setCycleTime(UPDATED_CYCLE_TIME);
        domheader.setIsMulti(UPDATED_IS_MULTI);
        domheader.setPanelQty(UPDATED_PANEL_QTY);
        domheader.setOpr(UPDATED_OPR);
        domheader.setShiftsup(UPDATED_SHIFTSUP);
        domheader.setShift(UPDATED_SHIFT);
        domheader.setShiftstart(UPDATED_SHIFTSTART);
        domheader.setShiftend(UPDATED_SHIFTEND);
        domheader.setSolder(UPDATED_SOLDER);
        domheader.setJmxid(UPDATED_JMXID);
        restDomheaderMockMvc.perform(put("/api/domheaders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domheader)))
                .andExpect(status().isOk());

        // Validate the Domheader in the database
        List<Domheader> domheaders = domheaderRepository.findAll();
        assertThat(domheaders).hasSize(databaseSizeBeforeUpdate);
        Domheader testDomheader = domheaders.get(domheaders.size() - 1);
        assertThat(testDomheader.getIsAuto()).isEqualTo(UPDATED_IS_AUTO);
        assertThat(testDomheader.getCycleTime()).isEqualTo(UPDATED_CYCLE_TIME);
        assertThat(testDomheader.getIsMulti()).isEqualTo(UPDATED_IS_MULTI);
        assertThat(testDomheader.getPanelQty()).isEqualTo(UPDATED_PANEL_QTY);
        assertThat(testDomheader.getOpr()).isEqualTo(UPDATED_OPR);
        assertThat(testDomheader.getShiftsup()).isEqualTo(UPDATED_SHIFTSUP);
        assertThat(testDomheader.getShift()).isEqualTo(UPDATED_SHIFT);
        assertThat(testDomheader.getShiftstart().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_SHIFTSTART);
        assertThat(testDomheader.getShiftend().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_SHIFTEND);
        assertThat(testDomheader.getSolder()).isEqualTo(UPDATED_SOLDER);
        assertThat(testDomheader.getJmxid()).isEqualTo(UPDATED_JMXID);
    }

    @Test
    @Transactional
    public void deleteDomheader() throws Exception {
        // Initialize the database
        domheaderRepository.saveAndFlush(domheader);

		int databaseSizeBeforeDelete = domheaderRepository.findAll().size();

        // Get the domheader
        restDomheaderMockMvc.perform(delete("/api/domheaders/{id}", domheader.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Domheader> domheaders = domheaderRepository.findAll();
        assertThat(domheaders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
