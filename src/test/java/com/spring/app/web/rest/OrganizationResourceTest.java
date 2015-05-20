package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Organization;
import com.spring.app.repository.OrganizationRepository;

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
 * Test class for the OrganizationResource REST controller.
 *
 * @see OrganizationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrganizationResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS_1 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS_1 = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS_2 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS_2 = "UPDATED_TEXT";
    private static final String DEFAULT_COUNTRY = "SAMPLE_TEXT";
    private static final String UPDATED_COUNTRY = "UPDATED_TEXT";
    private static final String DEFAULT_CITY = "SAMPLE_TEXT";
    private static final String UPDATED_CITY = "UPDATED_TEXT";
    private static final String DEFAULT_PINCODE = "SAMPLE_TEXT";
    private static final String UPDATED_PINCODE = "UPDATED_TEXT";

    @Inject
    private OrganizationRepository organizationRepository;

    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrganizationResource organizationResource = new OrganizationResource();
        ReflectionTestUtils.setField(organizationResource, "organizationRepository", organizationRepository);
        this.restOrganizationMockMvc = MockMvcBuilders.standaloneSetup(organizationResource).build();
    }

    @Before
    public void initTest() {
        organization = new Organization();
        organization.setName(DEFAULT_NAME);
        organization.setAddress_1(DEFAULT_ADDRESS_1);
        organization.setAddress_2(DEFAULT_ADDRESS_2);
        organization.setCountry(DEFAULT_COUNTRY);
        organization.setCity(DEFAULT_CITY);
        organization.setPincode(DEFAULT_PINCODE);
    }

    @Test
    @Transactional
    public void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization
        restOrganizationMockMvc.perform(post("/api/organizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organization)))
                .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizations.get(organizations.size() - 1);
        assertThat(testOrganization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganization.getAddress_1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testOrganization.getAddress_2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testOrganization.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testOrganization.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testOrganization.getPincode()).isEqualTo(DEFAULT_PINCODE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(organizationRepository.findAll()).hasSize(0);
        // set the field null
        organization.setName(null);

        // Create the Organization, which fails.
        restOrganizationMockMvc.perform(post("/api/organizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organization)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(0);
    }

    @Test
    @Transactional
    public void checkAddress_1IsRequired() throws Exception {
        // Validate the database is empty
        assertThat(organizationRepository.findAll()).hasSize(0);
        // set the field null
        organization.setAddress_1(null);

        // Create the Organization, which fails.
        restOrganizationMockMvc.perform(post("/api/organizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organization)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(0);
    }

    @Test
    @Transactional
    public void checkAddress_2IsRequired() throws Exception {
        // Validate the database is empty
        assertThat(organizationRepository.findAll()).hasSize(0);
        // set the field null
        organization.setAddress_2(null);

        // Create the Organization, which fails.
        restOrganizationMockMvc.perform(post("/api/organizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organization)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(0);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(organizationRepository.findAll()).hasSize(0);
        // set the field null
        organization.setCountry(null);

        // Create the Organization, which fails.
        restOrganizationMockMvc.perform(post("/api/organizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organization)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(0);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(organizationRepository.findAll()).hasSize(0);
        // set the field null
        organization.setCity(null);

        // Create the Organization, which fails.
        restOrganizationMockMvc.perform(post("/api/organizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organization)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(0);
    }

    @Test
    @Transactional
    public void checkPincodeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(organizationRepository.findAll()).hasSize(0);
        // set the field null
        organization.setPincode(null);

        // Create the Organization, which fails.
        restOrganizationMockMvc.perform(post("/api/organizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organization)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizations
        restOrganizationMockMvc.perform(get("/api/organizations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].address_1").value(hasItem(DEFAULT_ADDRESS_1.toString())))
                .andExpect(jsonPath("$.[*].address_2").value(hasItem(DEFAULT_ADDRESS_2.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.toString())));
    }

    @Test
    @Transactional
    public void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(organization.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address_1").value(DEFAULT_ADDRESS_1.toString()))
            .andExpect(jsonPath("$.address_2").value(DEFAULT_ADDRESS_2.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

		int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        organization.setName(UPDATED_NAME);
        organization.setAddress_1(UPDATED_ADDRESS_1);
        organization.setAddress_2(UPDATED_ADDRESS_2);
        organization.setCountry(UPDATED_COUNTRY);
        organization.setCity(UPDATED_CITY);
        organization.setPincode(UPDATED_PINCODE);
        restOrganizationMockMvc.perform(put("/api/organizations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organization)))
                .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizations.get(organizations.size() - 1);
        assertThat(testOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganization.getAddress_1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testOrganization.getAddress_2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testOrganization.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testOrganization.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testOrganization.getPincode()).isEqualTo(UPDATED_PINCODE);
    }

    @Test
    @Transactional
    public void deleteOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

		int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Get the organization
        restOrganizationMockMvc.perform(delete("/api/organizations/{id}", organization.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Organization> organizations = organizationRepository.findAll();
        assertThat(organizations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
