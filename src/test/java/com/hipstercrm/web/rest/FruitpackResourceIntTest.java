package com.hipstercrm.web.rest;

import com.hipstercrm.Application;
import com.hipstercrm.domain.Fruitpack;
import com.hipstercrm.repository.FruitpackRepository;
import com.hipstercrm.repository.search.FruitpackSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the FruitpackResource REST controller.
 *
 * @see FruitpackResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FruitpackResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private FruitpackRepository fruitpackRepository;

    @Inject
    private FruitpackSearchRepository fruitpackSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFruitpackMockMvc;

    private Fruitpack fruitpack;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FruitpackResource fruitpackResource = new FruitpackResource();
        ReflectionTestUtils.setField(fruitpackResource, "fruitpackSearchRepository", fruitpackSearchRepository);
        ReflectionTestUtils.setField(fruitpackResource, "fruitpackRepository", fruitpackRepository);
        this.restFruitpackMockMvc = MockMvcBuilders.standaloneSetup(fruitpackResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fruitpack = new Fruitpack();
        fruitpack.setName(DEFAULT_NAME);
        fruitpack.setPrice(DEFAULT_PRICE);
        fruitpack.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFruitpack() throws Exception {
        int databaseSizeBeforeCreate = fruitpackRepository.findAll().size();

        // Create the Fruitpack

        restFruitpackMockMvc.perform(post("/api/fruitpacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fruitpack)))
                .andExpect(status().isCreated());

        // Validate the Fruitpack in the database
        List<Fruitpack> fruitpacks = fruitpackRepository.findAll();
        assertThat(fruitpacks).hasSize(databaseSizeBeforeCreate + 1);
        Fruitpack testFruitpack = fruitpacks.get(fruitpacks.size() - 1);
        assertThat(testFruitpack.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFruitpack.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFruitpack.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fruitpackRepository.findAll().size();
        // set the field null
        fruitpack.setName(null);

        // Create the Fruitpack, which fails.

        restFruitpackMockMvc.perform(post("/api/fruitpacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fruitpack)))
                .andExpect(status().isBadRequest());

        List<Fruitpack> fruitpacks = fruitpackRepository.findAll();
        assertThat(fruitpacks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = fruitpackRepository.findAll().size();
        // set the field null
        fruitpack.setPrice(null);

        // Create the Fruitpack, which fails.

        restFruitpackMockMvc.perform(post("/api/fruitpacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fruitpack)))
                .andExpect(status().isBadRequest());

        List<Fruitpack> fruitpacks = fruitpackRepository.findAll();
        assertThat(fruitpacks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFruitpacks() throws Exception {
        // Initialize the database
        fruitpackRepository.saveAndFlush(fruitpack);

        // Get all the fruitpacks
        restFruitpackMockMvc.perform(get("/api/fruitpacks?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fruitpack.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getFruitpack() throws Exception {
        // Initialize the database
        fruitpackRepository.saveAndFlush(fruitpack);

        // Get the fruitpack
        restFruitpackMockMvc.perform(get("/api/fruitpacks/{id}", fruitpack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fruitpack.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFruitpack() throws Exception {
        // Get the fruitpack
        restFruitpackMockMvc.perform(get("/api/fruitpacks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFruitpack() throws Exception {
        // Initialize the database
        fruitpackRepository.saveAndFlush(fruitpack);

		int databaseSizeBeforeUpdate = fruitpackRepository.findAll().size();

        // Update the fruitpack
        fruitpack.setName(UPDATED_NAME);
        fruitpack.setPrice(UPDATED_PRICE);
        fruitpack.setDescription(UPDATED_DESCRIPTION);

        restFruitpackMockMvc.perform(put("/api/fruitpacks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fruitpack)))
                .andExpect(status().isOk());

        // Validate the Fruitpack in the database
        List<Fruitpack> fruitpacks = fruitpackRepository.findAll();
        assertThat(fruitpacks).hasSize(databaseSizeBeforeUpdate);
        Fruitpack testFruitpack = fruitpacks.get(fruitpacks.size() - 1);
        assertThat(testFruitpack.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFruitpack.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFruitpack.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteFruitpack() throws Exception {
        // Initialize the database
        fruitpackRepository.saveAndFlush(fruitpack);

		int databaseSizeBeforeDelete = fruitpackRepository.findAll().size();

        // Get the fruitpack
        restFruitpackMockMvc.perform(delete("/api/fruitpacks/{id}", fruitpack.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fruitpack> fruitpacks = fruitpackRepository.findAll();
        assertThat(fruitpacks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
