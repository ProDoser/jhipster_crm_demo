package com.hipstercrm.web.rest;

import com.hipstercrm.Application;
import com.hipstercrm.domain.Customer;
import com.hipstercrm.repository.CustomerRepository;
import com.hipstercrm.repository.search.CustomerSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
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
public class CustomerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CONTACTPERSON = "AAAAA";
    private static final String UPDATED_CONTACTPERSON = "BBBBB";
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_COUNTRY = "AAAAA";
    private static final String UPDATED_COUNTRY = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";
    private static final String DEFAULT_POSTCODE = "AAAAA";
    private static final String UPDATED_POSTCODE = "BBBBB";
    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    private static final Boolean DEFAULT_ISPRIVATE = false;
    private static final Boolean UPDATED_ISPRIVATE = true;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private CustomerSearchRepository customerSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerResource customerResource = new CustomerResource();
        ReflectionTestUtils.setField(customerResource, "customerSearchRepository", customerSearchRepository);
        ReflectionTestUtils.setField(customerResource, "customerRepository", customerRepository);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        customer = new Customer();
        customer.setName(DEFAULT_NAME);
        customer.setContactperson(DEFAULT_CONTACTPERSON);
        customer.setPhone(DEFAULT_PHONE);
        customer.setEmail(DEFAULT_EMAIL);
        customer.setCountry(DEFAULT_COUNTRY);
        customer.setCity(DEFAULT_CITY);
        customer.setPostcode(DEFAULT_POSTCODE);
        customer.setStreet(DEFAULT_STREET);
        customer.setComment(DEFAULT_COMMENT);
        customer.setIsprivate(DEFAULT_ISPRIVATE);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customers.get(customers.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getContactperson()).isEqualTo(DEFAULT_CONTACTPERSON);
        assertThat(testCustomer.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCustomer.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustomer.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testCustomer.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testCustomer.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testCustomer.getIsprivate()).isEqualTo(DEFAULT_ISPRIVATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setName(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactpersonIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setContactperson(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setPhone(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCountry(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCity(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setPostcode(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setStreet(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsprivateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setIsprivate(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isBadRequest());

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customers
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].contactperson").value(hasItem(DEFAULT_CONTACTPERSON.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE.toString())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
                .andExpect(jsonPath("$.[*].isprivate").value(hasItem(DEFAULT_ISPRIVATE.booleanValue())));
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.contactperson").value(DEFAULT_CONTACTPERSON.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.isprivate").value(DEFAULT_ISPRIVATE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

		int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        customer.setName(UPDATED_NAME);
        customer.setContactperson(UPDATED_CONTACTPERSON);
        customer.setPhone(UPDATED_PHONE);
        customer.setEmail(UPDATED_EMAIL);
        customer.setCountry(UPDATED_COUNTRY);
        customer.setCity(UPDATED_CITY);
        customer.setPostcode(UPDATED_POSTCODE);
        customer.setStreet(UPDATED_STREET);
        customer.setComment(UPDATED_COMMENT);
        customer.setIsprivate(UPDATED_ISPRIVATE);

        restCustomerMockMvc.perform(put("/api/customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customer)))
                .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customers.get(customers.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getContactperson()).isEqualTo(UPDATED_CONTACTPERSON);
        assertThat(testCustomer.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCustomer.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustomer.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testCustomer.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testCustomer.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testCustomer.getIsprivate()).isEqualTo(UPDATED_ISPRIVATE);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

		int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Get the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
