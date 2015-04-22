package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Customer;
import com.spring.app.repository.CustomerRepository;

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
 * Test class for the CustomerResource REST controller.
 *
 * @see CustomerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CustomerResourceTest {

    private static final String DEFAULT_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_BILL_TO_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_BILL_TO_ADDRESS = "UPDATED_TEXT";
    private static final String DEFAULT_SHIP_TO_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_SHIP_TO_ADDRESS = "UPDATED_TEXT";
    private static final String DEFAULT_CONTACT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_CONTACT_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_PHONE = "SAMPLE_TEXT";
    private static final String UPDATED_PHONE = "UPDATED_TEXT";

    @Inject
    private CustomerRepository customerRepository;

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerResource customerResource = new CustomerResource();
        ReflectionTestUtils.setField(customerResource, "customerRepository", customerRepository);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource).build();
    }

    @Before
    public void initTest() {
        customer = new Customer();
        customer.setCode(DEFAULT_CODE);
        customer.setName(DEFAULT_NAME);
        customer.setBillToAddress(DEFAULT_BILL_TO_ADDRESS);
        customer.setShipToAddress(DEFAULT_SHIP_TO_ADDRESS);
        customer.setContactName(DEFAULT_CONTACT_NAME);
        customer.setEmail(DEFAULT_EMAIL);
        customer.setPhone(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        // Validate the database is empty
        assertThat(customerRepository.findAll()).hasSize(0);

        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(1);
        Customer testCustomer = customers.iterator().next();
        assertThat(testCustomer.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getBillToAddress()).isEqualTo(DEFAULT_BILL_TO_ADDRESS);
        assertThat(testCustomer.getShipToAddress()).isEqualTo(DEFAULT_SHIP_TO_ADDRESS);
        assertThat(testCustomer.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customers
        restCustomerMockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(customer.getId().intValue()))
                .andExpect(jsonPath("$.[0].code").value(DEFAULT_CODE.toString()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].billToAddress").value(DEFAULT_BILL_TO_ADDRESS.toString()))
                .andExpect(jsonPath("$.[0].shipToAddress").value(DEFAULT_SHIP_TO_ADDRESS.toString()))
                .andExpect(jsonPath("$.[0].contactName").value(DEFAULT_CONTACT_NAME.toString()))
                .andExpect(jsonPath("$.[0].email").value(DEFAULT_EMAIL.toString()))
                .andExpect(jsonPath("$.[0].phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.billToAddress").value(DEFAULT_BILL_TO_ADDRESS.toString()))
            .andExpect(jsonPath("$.shipToAddress").value(DEFAULT_SHIP_TO_ADDRESS.toString()))
            .andExpect(jsonPath("$.contactName").value(DEFAULT_CONTACT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Update the customer
        customer.setCode(UPDATED_CODE);
        customer.setName(UPDATED_NAME);
        customer.setBillToAddress(UPDATED_BILL_TO_ADDRESS);
        customer.setShipToAddress(UPDATED_SHIP_TO_ADDRESS);
        customer.setContactName(UPDATED_CONTACT_NAME);
        customer.setEmail(UPDATED_EMAIL);
        customer.setPhone(UPDATED_PHONE);
        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(1);
        Customer testCustomer = customers.iterator().next();
        assertThat(testCustomer.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getBillToAddress()).isEqualTo(UPDATED_BILL_TO_ADDRESS);
        assertThat(testCustomer.getShipToAddress()).isEqualTo(UPDATED_SHIP_TO_ADDRESS);
        assertThat(testCustomer.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(0);
    }
}
