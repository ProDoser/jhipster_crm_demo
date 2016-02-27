package com.hipstercrm.web.rest;

import com.hipstercrm.Application;
import com.hipstercrm.domain.Orders;
import com.hipstercrm.repository.OrdersRepository;
import com.hipstercrm.repository.search.OrdersSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the OrdersResource REST controller.
 *
 * @see OrdersResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrdersResourceIntTest {

    private static final String DEFAULT_DETAILS = "AAAAA";
    private static final String UPDATED_DETAILS = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private OrdersRepository ordersRepository;

    @Inject
    private OrdersSearchRepository ordersSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOrdersMockMvc;

    private Orders orders;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrdersResource ordersResource = new OrdersResource();
        ReflectionTestUtils.setField(ordersResource, "ordersSearchRepository", ordersSearchRepository);
        ReflectionTestUtils.setField(ordersResource, "ordersRepository", ordersRepository);
        this.restOrdersMockMvc = MockMvcBuilders.standaloneSetup(ordersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        orders = new Orders();
        orders.setDetails(DEFAULT_DETAILS);
        orders.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createOrders() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders

        restOrdersMockMvc.perform(post("/api/orderss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orders)))
                .andExpect(status().isCreated());

        // Validate the Orders in the database
        List<Orders> orderss = ordersRepository.findAll();
        assertThat(orderss).hasSize(databaseSizeBeforeCreate + 1);
        Orders testOrders = orderss.get(orderss.size() - 1);
        assertThat(testOrders.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testOrders.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordersRepository.findAll().size();
        // set the field null
        orders.setDate(null);

        // Create the Orders, which fails.

        restOrdersMockMvc.perform(post("/api/orderss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orders)))
                .andExpect(status().isBadRequest());

        List<Orders> orderss = ordersRepository.findAll();
        assertThat(orderss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderss() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the orderss
        restOrdersMockMvc.perform(get("/api/orderss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().intValue())))
                .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get the orders
        restOrdersMockMvc.perform(get("/api/orderss/{id}", orders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orders.getId().intValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrders() throws Exception {
        // Get the orders
        restOrdersMockMvc.perform(get("/api/orderss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

		int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Update the orders
        orders.setDetails(UPDATED_DETAILS);
        orders.setDate(UPDATED_DATE);

        restOrdersMockMvc.perform(put("/api/orderss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orders)))
                .andExpect(status().isOk());

        // Validate the Orders in the database
        List<Orders> orderss = ordersRepository.findAll();
        assertThat(orderss).hasSize(databaseSizeBeforeUpdate);
        Orders testOrders = orderss.get(orderss.size() - 1);
        assertThat(testOrders.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testOrders.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

		int databaseSizeBeforeDelete = ordersRepository.findAll().size();

        // Get the orders
        restOrdersMockMvc.perform(delete("/api/orderss/{id}", orders.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Orders> orderss = ordersRepository.findAll();
        assertThat(orderss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
