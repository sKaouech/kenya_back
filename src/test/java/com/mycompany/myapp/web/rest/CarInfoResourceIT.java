package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CarInfo;
import com.mycompany.myapp.repository.CarInfoRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CarInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarInfoResourceIT {

    private static final String DEFAULT_CHASSIS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CHASSIS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LICENSE_PLATE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_PLATE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PURCHASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PURCHASE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GOLAN_FLAG_VALIDITY = "AAAAAAAAAA";
    private static final String UPDATED_GOLAN_FLAG_VALIDITY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VEHICLE_TAX_VALIDITY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VEHICLE_TAX_VALIDITY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ORIGINAL_IN_SERVICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORIGINAL_IN_SERVICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INSURANCE_VALIDITY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INSURANCE_VALIDITY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TECHNICAL_INSPECTION = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_INSPECTION = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_OWNER_ID = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_OWNER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OWNERS_ID = "AAAAAAAAAA";
    private static final String UPDATED_OWNERS_ID = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_WANTED = false;
    private static final Boolean UPDATED_WANTED = true;

    private static final String ENTITY_API_URL = "/api/car-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarInfoRepository carInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarInfoMockMvc;

    private CarInfo carInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarInfo createEntity(EntityManager em) {
        CarInfo carInfo = new CarInfo()
            .chassisNumber(DEFAULT_CHASSIS_NUMBER)
            .licensePlateNumber(DEFAULT_LICENSE_PLATE_NUMBER)
            .brand(DEFAULT_BRAND)
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .golanFlagValidity(DEFAULT_GOLAN_FLAG_VALIDITY)
            .vehicleTaxValidityDate(DEFAULT_VEHICLE_TAX_VALIDITY_DATE)
            .originalInServiceDate(DEFAULT_ORIGINAL_IN_SERVICE_DATE)
            .category(DEFAULT_CATEGORY)
            .insuranceValidityDate(DEFAULT_INSURANCE_VALIDITY_DATE)
            .technicalInspection(DEFAULT_TECHNICAL_INSPECTION)
            .firstOwner(DEFAULT_FIRST_OWNER)
            .firstOwnerID(DEFAULT_FIRST_OWNER_ID)
            .ownersID(DEFAULT_OWNERS_ID)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .wanted(DEFAULT_WANTED);
        return carInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarInfo createUpdatedEntity(EntityManager em) {
        CarInfo carInfo = new CarInfo()
            .chassisNumber(UPDATED_CHASSIS_NUMBER)
            .licensePlateNumber(UPDATED_LICENSE_PLATE_NUMBER)
            .brand(UPDATED_BRAND)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .golanFlagValidity(UPDATED_GOLAN_FLAG_VALIDITY)
            .vehicleTaxValidityDate(UPDATED_VEHICLE_TAX_VALIDITY_DATE)
            .originalInServiceDate(UPDATED_ORIGINAL_IN_SERVICE_DATE)
            .category(UPDATED_CATEGORY)
            .insuranceValidityDate(UPDATED_INSURANCE_VALIDITY_DATE)
            .technicalInspection(UPDATED_TECHNICAL_INSPECTION)
            .firstOwner(UPDATED_FIRST_OWNER)
            .firstOwnerID(UPDATED_FIRST_OWNER_ID)
            .ownersID(UPDATED_OWNERS_ID)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .wanted(UPDATED_WANTED);
        return carInfo;
    }

    @BeforeEach
    public void initTest() {
        carInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createCarInfo() throws Exception {
        int databaseSizeBeforeCreate = carInfoRepository.findAll().size();
        // Create the CarInfo
        restCarInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carInfo)))
            .andExpect(status().isCreated());

        // Validate the CarInfo in the database
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        assertThat(carInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CarInfo testCarInfo = carInfoList.get(carInfoList.size() - 1);
        assertThat(testCarInfo.getChassisNumber()).isEqualTo(DEFAULT_CHASSIS_NUMBER);
        assertThat(testCarInfo.getLicensePlateNumber()).isEqualTo(DEFAULT_LICENSE_PLATE_NUMBER);
        assertThat(testCarInfo.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testCarInfo.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testCarInfo.getGolanFlagValidity()).isEqualTo(DEFAULT_GOLAN_FLAG_VALIDITY);
        assertThat(testCarInfo.getVehicleTaxValidityDate()).isEqualTo(DEFAULT_VEHICLE_TAX_VALIDITY_DATE);
        assertThat(testCarInfo.getOriginalInServiceDate()).isEqualTo(DEFAULT_ORIGINAL_IN_SERVICE_DATE);
        assertThat(testCarInfo.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testCarInfo.getInsuranceValidityDate()).isEqualTo(DEFAULT_INSURANCE_VALIDITY_DATE);
        assertThat(testCarInfo.getTechnicalInspection()).isEqualTo(DEFAULT_TECHNICAL_INSPECTION);
        assertThat(testCarInfo.getFirstOwner()).isEqualTo(DEFAULT_FIRST_OWNER);
        assertThat(testCarInfo.getFirstOwnerID()).isEqualTo(DEFAULT_FIRST_OWNER_ID);
        assertThat(testCarInfo.getOwnersID()).isEqualTo(DEFAULT_OWNERS_ID);
        assertThat(testCarInfo.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testCarInfo.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testCarInfo.getWanted()).isEqualTo(DEFAULT_WANTED);
    }

    @Test
    @Transactional
    void createCarInfoWithExistingId() throws Exception {
        // Create the CarInfo with an existing ID
        carInfo.setId(1L);

        int databaseSizeBeforeCreate = carInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carInfo)))
            .andExpect(status().isBadRequest());

        // Validate the CarInfo in the database
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        assertThat(carInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCarInfos() throws Exception {
        // Initialize the database
        carInfoRepository.saveAndFlush(carInfo);

        // Get all the carInfoList
        restCarInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].chassisNumber").value(hasItem(DEFAULT_CHASSIS_NUMBER)))
            .andExpect(jsonPath("$.[*].licensePlateNumber").value(hasItem(DEFAULT_LICENSE_PLATE_NUMBER)))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].golanFlagValidity").value(hasItem(DEFAULT_GOLAN_FLAG_VALIDITY)))
            .andExpect(jsonPath("$.[*].vehicleTaxValidityDate").value(hasItem(DEFAULT_VEHICLE_TAX_VALIDITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].originalInServiceDate").value(hasItem(DEFAULT_ORIGINAL_IN_SERVICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].insuranceValidityDate").value(hasItem(DEFAULT_INSURANCE_VALIDITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].technicalInspection").value(hasItem(DEFAULT_TECHNICAL_INSPECTION)))
            .andExpect(jsonPath("$.[*].firstOwner").value(hasItem(DEFAULT_FIRST_OWNER)))
            .andExpect(jsonPath("$.[*].firstOwnerID").value(hasItem(DEFAULT_FIRST_OWNER_ID)))
            .andExpect(jsonPath("$.[*].ownersID").value(hasItem(DEFAULT_OWNERS_ID)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].wanted").value(hasItem(DEFAULT_WANTED.booleanValue())));
    }

    @Test
    @Transactional
    void getCarInfo() throws Exception {
        // Initialize the database
        carInfoRepository.saveAndFlush(carInfo);

        // Get the carInfo
        restCarInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, carInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carInfo.getId().intValue()))
            .andExpect(jsonPath("$.chassisNumber").value(DEFAULT_CHASSIS_NUMBER))
            .andExpect(jsonPath("$.licensePlateNumber").value(DEFAULT_LICENSE_PLATE_NUMBER))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.golanFlagValidity").value(DEFAULT_GOLAN_FLAG_VALIDITY))
            .andExpect(jsonPath("$.vehicleTaxValidityDate").value(DEFAULT_VEHICLE_TAX_VALIDITY_DATE.toString()))
            .andExpect(jsonPath("$.originalInServiceDate").value(DEFAULT_ORIGINAL_IN_SERVICE_DATE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.insuranceValidityDate").value(DEFAULT_INSURANCE_VALIDITY_DATE.toString()))
            .andExpect(jsonPath("$.technicalInspection").value(DEFAULT_TECHNICAL_INSPECTION))
            .andExpect(jsonPath("$.firstOwner").value(DEFAULT_FIRST_OWNER))
            .andExpect(jsonPath("$.firstOwnerID").value(DEFAULT_FIRST_OWNER_ID))
            .andExpect(jsonPath("$.ownersID").value(DEFAULT_OWNERS_ID))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.wanted").value(DEFAULT_WANTED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingCarInfo() throws Exception {
        // Get the carInfo
        restCarInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCarInfo() throws Exception {
        // Initialize the database
        carInfoRepository.saveAndFlush(carInfo);

        int databaseSizeBeforeUpdate = carInfoRepository.findAll().size();

        // Update the carInfo
        CarInfo updatedCarInfo = carInfoRepository.findById(carInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCarInfo are not directly saved in db
        em.detach(updatedCarInfo);
        updatedCarInfo
            .chassisNumber(UPDATED_CHASSIS_NUMBER)
            .licensePlateNumber(UPDATED_LICENSE_PLATE_NUMBER)
            .brand(UPDATED_BRAND)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .golanFlagValidity(UPDATED_GOLAN_FLAG_VALIDITY)
            .vehicleTaxValidityDate(UPDATED_VEHICLE_TAX_VALIDITY_DATE)
            .originalInServiceDate(UPDATED_ORIGINAL_IN_SERVICE_DATE)
            .category(UPDATED_CATEGORY)
            .insuranceValidityDate(UPDATED_INSURANCE_VALIDITY_DATE)
            .technicalInspection(UPDATED_TECHNICAL_INSPECTION)
            .firstOwner(UPDATED_FIRST_OWNER)
            .firstOwnerID(UPDATED_FIRST_OWNER_ID)
            .ownersID(UPDATED_OWNERS_ID)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .wanted(UPDATED_WANTED);

        restCarInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCarInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCarInfo))
            )
            .andExpect(status().isOk());

        // Validate the CarInfo in the database
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        assertThat(carInfoList).hasSize(databaseSizeBeforeUpdate);
        CarInfo testCarInfo = carInfoList.get(carInfoList.size() - 1);
        assertThat(testCarInfo.getChassisNumber()).isEqualTo(UPDATED_CHASSIS_NUMBER);
        assertThat(testCarInfo.getLicensePlateNumber()).isEqualTo(UPDATED_LICENSE_PLATE_NUMBER);
        assertThat(testCarInfo.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testCarInfo.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testCarInfo.getGolanFlagValidity()).isEqualTo(UPDATED_GOLAN_FLAG_VALIDITY);
        assertThat(testCarInfo.getVehicleTaxValidityDate()).isEqualTo(UPDATED_VEHICLE_TAX_VALIDITY_DATE);
        assertThat(testCarInfo.getOriginalInServiceDate()).isEqualTo(UPDATED_ORIGINAL_IN_SERVICE_DATE);
        assertThat(testCarInfo.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCarInfo.getInsuranceValidityDate()).isEqualTo(UPDATED_INSURANCE_VALIDITY_DATE);
        assertThat(testCarInfo.getTechnicalInspection()).isEqualTo(UPDATED_TECHNICAL_INSPECTION);
        assertThat(testCarInfo.getFirstOwner()).isEqualTo(UPDATED_FIRST_OWNER);
        assertThat(testCarInfo.getFirstOwnerID()).isEqualTo(UPDATED_FIRST_OWNER_ID);
        assertThat(testCarInfo.getOwnersID()).isEqualTo(UPDATED_OWNERS_ID);
        assertThat(testCarInfo.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCarInfo.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testCarInfo.getWanted()).isEqualTo(UPDATED_WANTED);
    }

    @Test
    @Transactional
    void putNonExistingCarInfo() throws Exception {
        int databaseSizeBeforeUpdate = carInfoRepository.findAll().size();
        carInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarInfo in the database
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        assertThat(carInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarInfo() throws Exception {
        int databaseSizeBeforeUpdate = carInfoRepository.findAll().size();
        carInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarInfo in the database
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        assertThat(carInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarInfo() throws Exception {
        int databaseSizeBeforeUpdate = carInfoRepository.findAll().size();
        carInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarInfo in the database
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        assertThat(carInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarInfoWithPatch() throws Exception {
        // Initialize the database
        carInfoRepository.saveAndFlush(carInfo);

        int databaseSizeBeforeUpdate = carInfoRepository.findAll().size();

        // Update the carInfo using partial update
        CarInfo partialUpdatedCarInfo = new CarInfo();
        partialUpdatedCarInfo.setId(carInfo.getId());

        partialUpdatedCarInfo
            .chassisNumber(UPDATED_CHASSIS_NUMBER)
            .golanFlagValidity(UPDATED_GOLAN_FLAG_VALIDITY)
            .originalInServiceDate(UPDATED_ORIGINAL_IN_SERVICE_DATE)
            .category(UPDATED_CATEGORY)
            .technicalInspection(UPDATED_TECHNICAL_INSPECTION)
            .firstOwner(UPDATED_FIRST_OWNER)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restCarInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarInfo))
            )
            .andExpect(status().isOk());

        // Validate the CarInfo in the database
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        assertThat(carInfoList).hasSize(databaseSizeBeforeUpdate);
        CarInfo testCarInfo = carInfoList.get(carInfoList.size() - 1);
        assertThat(testCarInfo.getChassisNumber()).isEqualTo(UPDATED_CHASSIS_NUMBER);
        assertThat(testCarInfo.getLicensePlateNumber()).isEqualTo(DEFAULT_LICENSE_PLATE_NUMBER);
        assertThat(testCarInfo.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testCarInfo.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testCarInfo.getGolanFlagValidity()).isEqualTo(UPDATED_GOLAN_FLAG_VALIDITY);
        assertThat(testCarInfo.getVehicleTaxValidityDate()).isEqualTo(DEFAULT_VEHICLE_TAX_VALIDITY_DATE);
        assertThat(testCarInfo.getOriginalInServiceDate()).isEqualTo(UPDATED_ORIGINAL_IN_SERVICE_DATE);
        assertThat(testCarInfo.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCarInfo.getInsuranceValidityDate()).isEqualTo(DEFAULT_INSURANCE_VALIDITY_DATE);
        assertThat(testCarInfo.getTechnicalInspection()).isEqualTo(UPDATED_TECHNICAL_INSPECTION);
        assertThat(testCarInfo.getFirstOwner()).isEqualTo(UPDATED_FIRST_OWNER);
        assertThat(testCarInfo.getFirstOwnerID()).isEqualTo(DEFAULT_FIRST_OWNER_ID);
        assertThat(testCarInfo.getOwnersID()).isEqualTo(DEFAULT_OWNERS_ID);
        assertThat(testCarInfo.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCarInfo.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testCarInfo.getWanted()).isEqualTo(DEFAULT_WANTED);
    }

    @Test
    @Transactional
    void fullUpdateCarInfoWithPatch() throws Exception {
        // Initialize the database
        carInfoRepository.saveAndFlush(carInfo);

        int databaseSizeBeforeUpdate = carInfoRepository.findAll().size();

        // Update the carInfo using partial update
        CarInfo partialUpdatedCarInfo = new CarInfo();
        partialUpdatedCarInfo.setId(carInfo.getId());

        partialUpdatedCarInfo
            .chassisNumber(UPDATED_CHASSIS_NUMBER)
            .licensePlateNumber(UPDATED_LICENSE_PLATE_NUMBER)
            .brand(UPDATED_BRAND)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .golanFlagValidity(UPDATED_GOLAN_FLAG_VALIDITY)
            .vehicleTaxValidityDate(UPDATED_VEHICLE_TAX_VALIDITY_DATE)
            .originalInServiceDate(UPDATED_ORIGINAL_IN_SERVICE_DATE)
            .category(UPDATED_CATEGORY)
            .insuranceValidityDate(UPDATED_INSURANCE_VALIDITY_DATE)
            .technicalInspection(UPDATED_TECHNICAL_INSPECTION)
            .firstOwner(UPDATED_FIRST_OWNER)
            .firstOwnerID(UPDATED_FIRST_OWNER_ID)
            .ownersID(UPDATED_OWNERS_ID)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .wanted(UPDATED_WANTED);

        restCarInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarInfo))
            )
            .andExpect(status().isOk());

        // Validate the CarInfo in the database
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        assertThat(carInfoList).hasSize(databaseSizeBeforeUpdate);
        CarInfo testCarInfo = carInfoList.get(carInfoList.size() - 1);
        assertThat(testCarInfo.getChassisNumber()).isEqualTo(UPDATED_CHASSIS_NUMBER);
        assertThat(testCarInfo.getLicensePlateNumber()).isEqualTo(UPDATED_LICENSE_PLATE_NUMBER);
        assertThat(testCarInfo.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testCarInfo.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testCarInfo.getGolanFlagValidity()).isEqualTo(UPDATED_GOLAN_FLAG_VALIDITY);
        assertThat(testCarInfo.getVehicleTaxValidityDate()).isEqualTo(UPDATED_VEHICLE_TAX_VALIDITY_DATE);
        assertThat(testCarInfo.getOriginalInServiceDate()).isEqualTo(UPDATED_ORIGINAL_IN_SERVICE_DATE);
        assertThat(testCarInfo.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCarInfo.getInsuranceValidityDate()).isEqualTo(UPDATED_INSURANCE_VALIDITY_DATE);
        assertThat(testCarInfo.getTechnicalInspection()).isEqualTo(UPDATED_TECHNICAL_INSPECTION);
        assertThat(testCarInfo.getFirstOwner()).isEqualTo(UPDATED_FIRST_OWNER);
        assertThat(testCarInfo.getFirstOwnerID()).isEqualTo(UPDATED_FIRST_OWNER_ID);
        assertThat(testCarInfo.getOwnersID()).isEqualTo(UPDATED_OWNERS_ID);
        assertThat(testCarInfo.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCarInfo.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testCarInfo.getWanted()).isEqualTo(UPDATED_WANTED);
    }

    @Test
    @Transactional
    void patchNonExistingCarInfo() throws Exception {
        int databaseSizeBeforeUpdate = carInfoRepository.findAll().size();
        carInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarInfo in the database
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        assertThat(carInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarInfo() throws Exception {
        int databaseSizeBeforeUpdate = carInfoRepository.findAll().size();
        carInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarInfo in the database
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        assertThat(carInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarInfo() throws Exception {
        int databaseSizeBeforeUpdate = carInfoRepository.findAll().size();
        carInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarInfoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(carInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarInfo in the database
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        assertThat(carInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarInfo() throws Exception {
        // Initialize the database
        carInfoRepository.saveAndFlush(carInfo);

        int databaseSizeBeforeDelete = carInfoRepository.findAll().size();

        // Delete the carInfo
        restCarInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, carInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarInfo> carInfoList = carInfoRepository.findAll();
        assertThat(carInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
