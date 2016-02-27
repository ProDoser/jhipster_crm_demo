package com.hipstercrm.web.rest;

import com.hipstercrm.Application;
import com.hipstercrm.domain.Deliveryday;
import com.hipstercrm.repository.DeliverydayRepository;
import com.hipstercrm.repository.search.DeliverydaySearchRepository;

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
 * Test class for the DeliverydayResource REST controller.
 *
 * @see DeliverydayResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DeliverydayResourceIntTest {


    private static final Integer DEFAULT_WEEKDAY = 0;
    private static final Integer UPDATED_WEEKDAY = 1;
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private DeliverydayRepository deliverydayRepository;

    @Inject
    private DeliverydaySearchRepository deliverydaySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDeliverydayMockMvc;

    private Deliveryday deliveryday;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeliverydayResource deliverydayResource = new DeliverydayResource();
        ReflectionTestUtils.setField(deliverydayResource, "deliverydaySearchRepository", deliverydaySearchRepository);
        ReflectionTestUtils.setField(deliverydayResource, "deliverydayRepository", deliverydayRepository);
        this.restDeliverydayMockMvc = MockMvcBuilders.standaloneSetup(deliverydayResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        deliveryday = new Deliveryday();
        deliveryday.setWeekday(DEFAULT_WEEKDAY);
        deliveryday.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDeliveryday() throws Exception {
        int databaseSizeBeforeCreate = deliverydayRepository.findAll().size();

        // Create the Deliveryday

        restDeliverydayMockMvc.perform(post("/api/deliverydays")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deliveryday)))
                .andExpect(status().isCreated());

        // Validate the Deliveryday in the database
        List<Deliveryday> deliverydays = deliverydayRepository.findAll();
        assertThat(deliverydays).hasSize(databaseSizeBeforeCreate + 1);
        Deliveryday testDeliveryday = deliverydays.get(deliverydays.size() - 1);
        assertThat(testDeliveryday.getWeekday()).isEqualTo(DEFAULT_WEEKDAY);
        assertThat(testDeliveryday.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkWeekdayIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliverydayRepository.findAll().size();
        // set the field null
        deliveryday.setWeekday(null);

        // Create the Deliveryday, which fails.

        restDeliverydayMockMvc.perform(post("/api/deliverydays")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deliveryday)))
                .andExpect(status().isBadRequest());

        List<Deliveryday> deliverydays = deliverydayRepository.findAll();
        assertThat(deliverydays).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliverydayRepository.findAll().size();
        // set the field null
        deliveryday.setName(null);

        // Create the Deliveryday, which fails.

        restDeliverydayMockMvc.perform(post("/api/deliverydays")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deliveryday)))
                .andExpect(status().isBadRequest());

        List<Deliveryday> deliverydays = deliverydayRepository.findAll();
        assertThat(deliverydays).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeliverydays() throws Exception {
        // Initialize the database
        deliverydayRepository.saveAndFlush(deliveryday);

        // Get all the deliverydays
        restDeliverydayMockMvc.perform(get("/api/deliverydays?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryday.getId().intValue())))
                .andExpect(jsonPath("$.[*].weekday").value(hasItem(DEFAULT_WEEKDAY)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDeliveryday() throws Exception {
        // Initialize the database
        deliverydayRepository.saveAndFlush(deliveryday);

        // Get the deliveryday
        restDeliverydayMockMvc.perform(get("/api/deliverydays/{id}", deliveryday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(deliveryday.getId().intValue()))
            .andExpect(jsonPath("$.weekday").value(DEFAULT_WEEKDAY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeliveryday() throws Exception {
        // Get the deliveryday
        restDeliverydayMockMvc.perform(get("/api/deliverydays/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryday() throws Exception {
        // Initialize the database
        deliverydayRepository.saveAndFlush(deliveryday);

		int databaseSizeBeforeUpdate = deliverydayRepository.findAll().size();

        // Update the deliveryday
        deliveryday.setWeekday(UPDATED_WEEKDAY);
        deliveryday.setName(UPDATED_NAME);

        restDeliverydayMockMvc.perform(put("/api/deliverydays")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deliveryday)))
                .andExpect(status().isOk());

        // Validate the Deliveryday in the database
        List<Deliveryday> deliverydays = deliverydayRepository.findAll();
        assertThat(deliverydays).hasSize(databaseSizeBeforeUpdate);
        Deliveryday testDeliveryday = deliverydays.get(deliverydays.size() - 1);
        assertThat(testDeliveryday.getWeekday()).isEqualTo(UPDATED_WEEKDAY);
        assertThat(testDeliveryday.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteDeliveryday() throws Exception {
        // Initialize the database
        deliverydayRepository.saveAndFlush(deliveryday);

		int databaseSizeBeforeDelete = deliverydayRepository.findAll().size();

        // Get the deliveryday
        restDeliverydayMockMvc.perform(delete("/api/deliverydays/{id}", deliveryday.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Deliveryday> deliverydays = deliverydayRepository.findAll();
        assertThat(deliverydays).hasSize(databaseSizeBeforeDelete - 1);
    }
}
