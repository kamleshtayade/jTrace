package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Domline;
import com.spring.app.repository.DomlineRepository;

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
 * Test class for the DomlineResource REST controller.
 *
 * @see DomlineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DomlineResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_SERIALNO = "SAMPLE_TEXT";
    private static final String UPDATED_SERIALNO = "UPDATED_TEXT";

    private static final DateTime DEFAULT_SCANTIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_SCANTIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_SCANTIME_STR = dateTimeFormatter.print(DEFAULT_SCANTIME);

    @Inject
    private DomlineRepository domlineRepository;

    private MockMvc restDomlineMockMvc;

    private Domline domline;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DomlineResource domlineResource = new DomlineResource();
        ReflectionTestUtils.setField(domlineResource, "domlineRepository", domlineRepository);
        this.restDomlineMockMvc = MockMvcBuilders.standaloneSetup(domlineResource).build();
    }

    @Before
    public void initTest() {
        domline = new Domline();
        domline.setSerialno(DEFAULT_SERIALNO);
        domline.setScantime(DEFAULT_SCANTIME);
    }

    @Test
    @Transactional
    public void createDomline() throws Exception {
        int databaseSizeBeforeCreate = domlineRepository.findAll().size();

        // Create the Domline
        restDomlineMockMvc.perform(post("/api/domlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domline)))
                .andExpect(status().isCreated());

        // Validate the Domline in the database
        List<Domline> domlines = domlineRepository.findAll();
        assertThat(domlines).hasSize(databaseSizeBeforeCreate + 1);
        Domline testDomline = domlines.get(domlines.size() - 1);
        assertThat(testDomline.getSerialno()).isEqualTo(DEFAULT_SERIALNO);
        assertThat(testDomline.getScantime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_SCANTIME);
    }

    @Test
    @Transactional
    public void getAllDomlines() throws Exception {
        // Initialize the database
        domlineRepository.saveAndFlush(domline);

        // Get all the domlines
        restDomlineMockMvc.perform(get("/api/domlines"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(domline.getId().intValue())))
                .andExpect(jsonPath("$.[*].serialno").value(hasItem(DEFAULT_SERIALNO.toString())))
                .andExpect(jsonPath("$.[*].scantime").value(hasItem(DEFAULT_SCANTIME_STR)));
    }

    @Test
    @Transactional
    public void getDomline() throws Exception {
        // Initialize the database
        domlineRepository.saveAndFlush(domline);

        // Get the domline
        restDomlineMockMvc.perform(get("/api/domlines/{id}", domline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(domline.getId().intValue()))
            .andExpect(jsonPath("$.serialno").value(DEFAULT_SERIALNO.toString()))
            .andExpect(jsonPath("$.scantime").value(DEFAULT_SCANTIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingDomline() throws Exception {
        // Get the domline
        restDomlineMockMvc.perform(get("/api/domlines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomline() throws Exception {
        // Initialize the database
        domlineRepository.saveAndFlush(domline);

		int databaseSizeBeforeUpdate = domlineRepository.findAll().size();

        // Update the domline
        domline.setSerialno(UPDATED_SERIALNO);
        domline.setScantime(UPDATED_SCANTIME);
        restDomlineMockMvc.perform(put("/api/domlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(domline)))
                .andExpect(status().isOk());

        // Validate the Domline in the database
        List<Domline> domlines = domlineRepository.findAll();
        assertThat(domlines).hasSize(databaseSizeBeforeUpdate);
        Domline testDomline = domlines.get(domlines.size() - 1);
        assertThat(testDomline.getSerialno()).isEqualTo(UPDATED_SERIALNO);
        assertThat(testDomline.getScantime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_SCANTIME);
    }

    @Test
    @Transactional
    public void deleteDomline() throws Exception {
        // Initialize the database
        domlineRepository.saveAndFlush(domline);

		int databaseSizeBeforeDelete = domlineRepository.findAll().size();

        // Get the domline
        restDomlineMockMvc.perform(delete("/api/domlines/{id}", domline.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Domline> domlines = domlineRepository.findAll();
        assertThat(domlines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
