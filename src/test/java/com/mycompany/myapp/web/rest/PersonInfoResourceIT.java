package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PersonInfo;
import com.mycompany.myapp.repository.PersonInfoRepository;
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
 * Integration tests for the {@link PersonInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonInfoResourceIT {

    private static final String DEFAULT_NATIONAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTITY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DRIVE_LICENCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DRIVE_LICENCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DRIVE_LICENCE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DRIVE_LICENCE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NATIONAL_ID_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NATIONAL_ID_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BIRTH_DATE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_DATE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PASSPORT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PASSPORT_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DRIVE_LICENCE_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DRIVE_LICENCE_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_JOB = "AAAAAAAAAA";
    private static final String UPDATED_JOB = "BBBBBBBBBB";

    private static final String DEFAULT_FATHERS_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHERS_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_FIRST_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_WANTED = false;
    private static final Boolean UPDATED_WANTED = true;

    private static final String ENTITY_API_URL = "/api/person-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonInfoRepository personInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonInfoMockMvc;

    private PersonInfo personInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonInfo createEntity(EntityManager em) {
        PersonInfo personInfo = new PersonInfo()
            .nationalID(DEFAULT_NATIONAL_ID)
            .identityType(DEFAULT_IDENTITY_TYPE)
            .passportID(DEFAULT_PASSPORT_ID)
            .driveLicenceID(DEFAULT_DRIVE_LICENCE_ID)
            .driveLicenceType(DEFAULT_DRIVE_LICENCE_TYPE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .nationalIdIssueDate(DEFAULT_NATIONAL_ID_ISSUE_DATE)
            .birthDate(DEFAULT_BIRTH_DATE)
            .passportIssueDate(DEFAULT_PASSPORT_ISSUE_DATE)
            .driveLicenceIssueDate(DEFAULT_DRIVE_LICENCE_ISSUE_DATE)
            .job(DEFAULT_JOB)
            .fathersFirstName(DEFAULT_FATHERS_FIRST_NAME)
            .motherFirstName(DEFAULT_MOTHER_FIRST_NAME)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .wanted(DEFAULT_WANTED);
        return personInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonInfo createUpdatedEntity(EntityManager em) {
        PersonInfo personInfo = new PersonInfo()
            .nationalID(UPDATED_NATIONAL_ID)
            .identityType(UPDATED_IDENTITY_TYPE)
            .passportID(UPDATED_PASSPORT_ID)
            .driveLicenceID(UPDATED_DRIVE_LICENCE_ID)
            .driveLicenceType(UPDATED_DRIVE_LICENCE_TYPE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .nationalIdIssueDate(UPDATED_NATIONAL_ID_ISSUE_DATE)
            .birthDate(UPDATED_BIRTH_DATE)
            .passportIssueDate(UPDATED_PASSPORT_ISSUE_DATE)
            .driveLicenceIssueDate(UPDATED_DRIVE_LICENCE_ISSUE_DATE)
            .job(UPDATED_JOB)
            .fathersFirstName(UPDATED_FATHERS_FIRST_NAME)
            .motherFirstName(UPDATED_MOTHER_FIRST_NAME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .wanted(UPDATED_WANTED);
        return personInfo;
    }

    @BeforeEach
    public void initTest() {
        personInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonInfo() throws Exception {
        int databaseSizeBeforeCreate = personInfoRepository.findAll().size();
        // Create the PersonInfo
        restPersonInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personInfo)))
            .andExpect(status().isCreated());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PersonInfo testPersonInfo = personInfoList.get(personInfoList.size() - 1);
        assertThat(testPersonInfo.getNationalID()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testPersonInfo.getIdentityType()).isEqualTo(DEFAULT_IDENTITY_TYPE);
        assertThat(testPersonInfo.getPassportID()).isEqualTo(DEFAULT_PASSPORT_ID);
        assertThat(testPersonInfo.getDriveLicenceID()).isEqualTo(DEFAULT_DRIVE_LICENCE_ID);
        assertThat(testPersonInfo.getDriveLicenceType()).isEqualTo(DEFAULT_DRIVE_LICENCE_TYPE);
        assertThat(testPersonInfo.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPersonInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPersonInfo.getNationalIdIssueDate()).isEqualTo(DEFAULT_NATIONAL_ID_ISSUE_DATE);
        assertThat(testPersonInfo.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPersonInfo.getPassportIssueDate()).isEqualTo(DEFAULT_PASSPORT_ISSUE_DATE);
        assertThat(testPersonInfo.getDriveLicenceIssueDate()).isEqualTo(DEFAULT_DRIVE_LICENCE_ISSUE_DATE);
        assertThat(testPersonInfo.getJob()).isEqualTo(DEFAULT_JOB);
        assertThat(testPersonInfo.getFathersFirstName()).isEqualTo(DEFAULT_FATHERS_FIRST_NAME);
        assertThat(testPersonInfo.getMotherFirstName()).isEqualTo(DEFAULT_MOTHER_FIRST_NAME);
        assertThat(testPersonInfo.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPersonInfo.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testPersonInfo.getWanted()).isEqualTo(DEFAULT_WANTED);
    }

    @Test
    @Transactional
    void createPersonInfoWithExistingId() throws Exception {
        // Create the PersonInfo with an existing ID
        personInfo.setId(1L);

        int databaseSizeBeforeCreate = personInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personInfo)))
            .andExpect(status().isBadRequest());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPersonInfos() throws Exception {
        // Initialize the database
        personInfoRepository.saveAndFlush(personInfo);

        // Get all the personInfoList
        restPersonInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nationalID").value(hasItem(DEFAULT_NATIONAL_ID)))
            .andExpect(jsonPath("$.[*].identityType").value(hasItem(DEFAULT_IDENTITY_TYPE)))
            .andExpect(jsonPath("$.[*].passportID").value(hasItem(DEFAULT_PASSPORT_ID)))
            .andExpect(jsonPath("$.[*].driveLicenceID").value(hasItem(DEFAULT_DRIVE_LICENCE_ID)))
            .andExpect(jsonPath("$.[*].driveLicenceType").value(hasItem(DEFAULT_DRIVE_LICENCE_TYPE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].nationalIdIssueDate").value(hasItem(DEFAULT_NATIONAL_ID_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.[*].passportIssueDate").value(hasItem(DEFAULT_PASSPORT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].driveLicenceIssueDate").value(hasItem(DEFAULT_DRIVE_LICENCE_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].job").value(hasItem(DEFAULT_JOB)))
            .andExpect(jsonPath("$.[*].fathersFirstName").value(hasItem(DEFAULT_FATHERS_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].motherFirstName").value(hasItem(DEFAULT_MOTHER_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].wanted").value(hasItem(DEFAULT_WANTED.booleanValue())));
    }

    @Test
    @Transactional
    void getPersonInfo() throws Exception {
        // Initialize the database
        personInfoRepository.saveAndFlush(personInfo);

        // Get the personInfo
        restPersonInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, personInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personInfo.getId().intValue()))
            .andExpect(jsonPath("$.nationalID").value(DEFAULT_NATIONAL_ID))
            .andExpect(jsonPath("$.identityType").value(DEFAULT_IDENTITY_TYPE))
            .andExpect(jsonPath("$.passportID").value(DEFAULT_PASSPORT_ID))
            .andExpect(jsonPath("$.driveLicenceID").value(DEFAULT_DRIVE_LICENCE_ID))
            .andExpect(jsonPath("$.driveLicenceType").value(DEFAULT_DRIVE_LICENCE_TYPE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.nationalIdIssueDate").value(DEFAULT_NATIONAL_ID_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE))
            .andExpect(jsonPath("$.passportIssueDate").value(DEFAULT_PASSPORT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.driveLicenceIssueDate").value(DEFAULT_DRIVE_LICENCE_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.job").value(DEFAULT_JOB))
            .andExpect(jsonPath("$.fathersFirstName").value(DEFAULT_FATHERS_FIRST_NAME))
            .andExpect(jsonPath("$.motherFirstName").value(DEFAULT_MOTHER_FIRST_NAME))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.wanted").value(DEFAULT_WANTED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPersonInfo() throws Exception {
        // Get the personInfo
        restPersonInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPersonInfo() throws Exception {
        // Initialize the database
        personInfoRepository.saveAndFlush(personInfo);

        int databaseSizeBeforeUpdate = personInfoRepository.findAll().size();

        // Update the personInfo
        PersonInfo updatedPersonInfo = personInfoRepository.findById(personInfo.getId()).get();
        // Disconnect from session so that the updates on updatedPersonInfo are not directly saved in db
        em.detach(updatedPersonInfo);
        updatedPersonInfo
            .nationalID(UPDATED_NATIONAL_ID)
            .identityType(UPDATED_IDENTITY_TYPE)
            .passportID(UPDATED_PASSPORT_ID)
            .driveLicenceID(UPDATED_DRIVE_LICENCE_ID)
            .driveLicenceType(UPDATED_DRIVE_LICENCE_TYPE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .nationalIdIssueDate(UPDATED_NATIONAL_ID_ISSUE_DATE)
            .birthDate(UPDATED_BIRTH_DATE)
            .passportIssueDate(UPDATED_PASSPORT_ISSUE_DATE)
            .driveLicenceIssueDate(UPDATED_DRIVE_LICENCE_ISSUE_DATE)
            .job(UPDATED_JOB)
            .fathersFirstName(UPDATED_FATHERS_FIRST_NAME)
            .motherFirstName(UPDATED_MOTHER_FIRST_NAME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .wanted(UPDATED_WANTED);

        restPersonInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPersonInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPersonInfo))
            )
            .andExpect(status().isOk());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeUpdate);
        PersonInfo testPersonInfo = personInfoList.get(personInfoList.size() - 1);
        assertThat(testPersonInfo.getNationalID()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testPersonInfo.getIdentityType()).isEqualTo(UPDATED_IDENTITY_TYPE);
        assertThat(testPersonInfo.getPassportID()).isEqualTo(UPDATED_PASSPORT_ID);
        assertThat(testPersonInfo.getDriveLicenceID()).isEqualTo(UPDATED_DRIVE_LICENCE_ID);
        assertThat(testPersonInfo.getDriveLicenceType()).isEqualTo(UPDATED_DRIVE_LICENCE_TYPE);
        assertThat(testPersonInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPersonInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPersonInfo.getNationalIdIssueDate()).isEqualTo(UPDATED_NATIONAL_ID_ISSUE_DATE);
        assertThat(testPersonInfo.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPersonInfo.getPassportIssueDate()).isEqualTo(UPDATED_PASSPORT_ISSUE_DATE);
        assertThat(testPersonInfo.getDriveLicenceIssueDate()).isEqualTo(UPDATED_DRIVE_LICENCE_ISSUE_DATE);
        assertThat(testPersonInfo.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testPersonInfo.getFathersFirstName()).isEqualTo(UPDATED_FATHERS_FIRST_NAME);
        assertThat(testPersonInfo.getMotherFirstName()).isEqualTo(UPDATED_MOTHER_FIRST_NAME);
        assertThat(testPersonInfo.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPersonInfo.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPersonInfo.getWanted()).isEqualTo(UPDATED_WANTED);
    }

    @Test
    @Transactional
    void putNonExistingPersonInfo() throws Exception {
        int databaseSizeBeforeUpdate = personInfoRepository.findAll().size();
        personInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonInfo() throws Exception {
        int databaseSizeBeforeUpdate = personInfoRepository.findAll().size();
        personInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonInfo() throws Exception {
        int databaseSizeBeforeUpdate = personInfoRepository.findAll().size();
        personInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonInfoWithPatch() throws Exception {
        // Initialize the database
        personInfoRepository.saveAndFlush(personInfo);

        int databaseSizeBeforeUpdate = personInfoRepository.findAll().size();

        // Update the personInfo using partial update
        PersonInfo partialUpdatedPersonInfo = new PersonInfo();
        partialUpdatedPersonInfo.setId(personInfo.getId());

        partialUpdatedPersonInfo.nationalIdIssueDate(UPDATED_NATIONAL_ID_ISSUE_DATE);

        restPersonInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonInfo))
            )
            .andExpect(status().isOk());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeUpdate);
        PersonInfo testPersonInfo = personInfoList.get(personInfoList.size() - 1);
        assertThat(testPersonInfo.getNationalID()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testPersonInfo.getIdentityType()).isEqualTo(DEFAULT_IDENTITY_TYPE);
        assertThat(testPersonInfo.getPassportID()).isEqualTo(DEFAULT_PASSPORT_ID);
        assertThat(testPersonInfo.getDriveLicenceID()).isEqualTo(DEFAULT_DRIVE_LICENCE_ID);
        assertThat(testPersonInfo.getDriveLicenceType()).isEqualTo(DEFAULT_DRIVE_LICENCE_TYPE);
        assertThat(testPersonInfo.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPersonInfo.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPersonInfo.getNationalIdIssueDate()).isEqualTo(UPDATED_NATIONAL_ID_ISSUE_DATE);
        assertThat(testPersonInfo.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPersonInfo.getPassportIssueDate()).isEqualTo(DEFAULT_PASSPORT_ISSUE_DATE);
        assertThat(testPersonInfo.getDriveLicenceIssueDate()).isEqualTo(DEFAULT_DRIVE_LICENCE_ISSUE_DATE);
        assertThat(testPersonInfo.getJob()).isEqualTo(DEFAULT_JOB);
        assertThat(testPersonInfo.getFathersFirstName()).isEqualTo(DEFAULT_FATHERS_FIRST_NAME);
        assertThat(testPersonInfo.getMotherFirstName()).isEqualTo(DEFAULT_MOTHER_FIRST_NAME);
        assertThat(testPersonInfo.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPersonInfo.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testPersonInfo.getWanted()).isEqualTo(DEFAULT_WANTED);
    }

    @Test
    @Transactional
    void fullUpdatePersonInfoWithPatch() throws Exception {
        // Initialize the database
        personInfoRepository.saveAndFlush(personInfo);

        int databaseSizeBeforeUpdate = personInfoRepository.findAll().size();

        // Update the personInfo using partial update
        PersonInfo partialUpdatedPersonInfo = new PersonInfo();
        partialUpdatedPersonInfo.setId(personInfo.getId());

        partialUpdatedPersonInfo
            .nationalID(UPDATED_NATIONAL_ID)
            .identityType(UPDATED_IDENTITY_TYPE)
            .passportID(UPDATED_PASSPORT_ID)
            .driveLicenceID(UPDATED_DRIVE_LICENCE_ID)
            .driveLicenceType(UPDATED_DRIVE_LICENCE_TYPE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .nationalIdIssueDate(UPDATED_NATIONAL_ID_ISSUE_DATE)
            .birthDate(UPDATED_BIRTH_DATE)
            .passportIssueDate(UPDATED_PASSPORT_ISSUE_DATE)
            .driveLicenceIssueDate(UPDATED_DRIVE_LICENCE_ISSUE_DATE)
            .job(UPDATED_JOB)
            .fathersFirstName(UPDATED_FATHERS_FIRST_NAME)
            .motherFirstName(UPDATED_MOTHER_FIRST_NAME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .wanted(UPDATED_WANTED);

        restPersonInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonInfo))
            )
            .andExpect(status().isOk());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeUpdate);
        PersonInfo testPersonInfo = personInfoList.get(personInfoList.size() - 1);
        assertThat(testPersonInfo.getNationalID()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testPersonInfo.getIdentityType()).isEqualTo(UPDATED_IDENTITY_TYPE);
        assertThat(testPersonInfo.getPassportID()).isEqualTo(UPDATED_PASSPORT_ID);
        assertThat(testPersonInfo.getDriveLicenceID()).isEqualTo(UPDATED_DRIVE_LICENCE_ID);
        assertThat(testPersonInfo.getDriveLicenceType()).isEqualTo(UPDATED_DRIVE_LICENCE_TYPE);
        assertThat(testPersonInfo.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPersonInfo.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPersonInfo.getNationalIdIssueDate()).isEqualTo(UPDATED_NATIONAL_ID_ISSUE_DATE);
        assertThat(testPersonInfo.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPersonInfo.getPassportIssueDate()).isEqualTo(UPDATED_PASSPORT_ISSUE_DATE);
        assertThat(testPersonInfo.getDriveLicenceIssueDate()).isEqualTo(UPDATED_DRIVE_LICENCE_ISSUE_DATE);
        assertThat(testPersonInfo.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testPersonInfo.getFathersFirstName()).isEqualTo(UPDATED_FATHERS_FIRST_NAME);
        assertThat(testPersonInfo.getMotherFirstName()).isEqualTo(UPDATED_MOTHER_FIRST_NAME);
        assertThat(testPersonInfo.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPersonInfo.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPersonInfo.getWanted()).isEqualTo(UPDATED_WANTED);
    }

    @Test
    @Transactional
    void patchNonExistingPersonInfo() throws Exception {
        int databaseSizeBeforeUpdate = personInfoRepository.findAll().size();
        personInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonInfo() throws Exception {
        int databaseSizeBeforeUpdate = personInfoRepository.findAll().size();
        personInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonInfo() throws Exception {
        int databaseSizeBeforeUpdate = personInfoRepository.findAll().size();
        personInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonInfo in the database
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonInfo() throws Exception {
        // Initialize the database
        personInfoRepository.saveAndFlush(personInfo);

        int databaseSizeBeforeDelete = personInfoRepository.findAll().size();

        // Delete the personInfo
        restPersonInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, personInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonInfo> personInfoList = personInfoRepository.findAll();
        assertThat(personInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
