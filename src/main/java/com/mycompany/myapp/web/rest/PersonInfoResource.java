package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CarInfo;
import com.mycompany.myapp.domain.PersonInfo;
import com.mycompany.myapp.repository.CarInfoRepository;
import com.mycompany.myapp.repository.PersonInfoRepository;
import com.mycompany.myapp.service.dto.PersonInfoDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.PersonInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PersonInfoResource {

    private final Logger log = LoggerFactory.getLogger(PersonInfoResource.class);

    private static final String ENTITY_NAME = "personInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonInfoRepository personInfoRepository;
    private final CarInfoRepository carInfoRepository;

    public PersonInfoResource(PersonInfoRepository personInfoRepository, CarInfoRepository carInfoRepository) {
        this.personInfoRepository = personInfoRepository;
        this.carInfoRepository = carInfoRepository;
    }

    /**
     * {@code POST  /person-infos} : Create a new personInfo.
     *
     * @param personInfo the personInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personInfo, or with status {@code 400 (Bad Request)} if the personInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-infos")
    public ResponseEntity<PersonInfo> createPersonInfo(@RequestBody PersonInfo personInfo) throws URISyntaxException {
        log.debug("REST request to save PersonInfo : {}", personInfo);
        if (personInfo.getId() != null) {
            throw new BadRequestAlertException("A new personInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonInfo result = personInfoRepository.save(personInfo);
        return ResponseEntity
            .created(new URI("/api/person-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-infos/:id} : Updates an existing personInfo.
     *
     * @param id         the id of the personInfo to save.
     * @param personInfo the personInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personInfo,
     * or with status {@code 400 (Bad Request)} if the personInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-infos/{id}")
    public ResponseEntity<PersonInfo> updatePersonInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonInfo personInfo
    ) throws URISyntaxException {
        log.debug("REST request to update PersonInfo : {}, {}", id, personInfo);
        if (personInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonInfo result = personInfoRepository.save(personInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /person-infos/:id} : Partial updates given fields of an existing personInfo, field will ignore if it is null
     *
     * @param id         the id of the personInfo to save.
     * @param personInfo the personInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personInfo,
     * or with status {@code 400 (Bad Request)} if the personInfo is not valid,
     * or with status {@code 404 (Not Found)} if the personInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the personInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/person-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonInfo> partialUpdatePersonInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonInfo personInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonInfo partially : {}, {}", id, personInfo);
        if (personInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonInfo> result = personInfoRepository
            .findById(personInfo.getId())
            .map(existingPersonInfo -> {
                if (personInfo.getNationalID() != null) {
                    existingPersonInfo.setNationalID(personInfo.getNationalID());
                }
                if (personInfo.getIdentityType() != null) {
                    existingPersonInfo.setIdentityType(personInfo.getIdentityType());
                }
                if (personInfo.getPassportID() != null) {
                    existingPersonInfo.setPassportID(personInfo.getPassportID());
                }
                if (personInfo.getDriveLicenceID() != null) {
                    existingPersonInfo.setDriveLicenceID(personInfo.getDriveLicenceID());
                }
                if (personInfo.getDriveLicenceType() != null) {
                    existingPersonInfo.setDriveLicenceType(personInfo.getDriveLicenceType());
                }
                if (personInfo.getFirstName() != null) {
                    existingPersonInfo.setFirstName(personInfo.getFirstName());
                }
                if (personInfo.getLastName() != null) {
                    existingPersonInfo.setLastName(personInfo.getLastName());
                }
                if (personInfo.getNationalIdIssueDate() != null) {
                    existingPersonInfo.setNationalIdIssueDate(personInfo.getNationalIdIssueDate());
                }
                if (personInfo.getBirthDate() != null) {
                    existingPersonInfo.setBirthDate(personInfo.getBirthDate());
                }
                if (personInfo.getPassportIssueDate() != null) {
                    existingPersonInfo.setPassportIssueDate(personInfo.getPassportIssueDate());
                }
                if (personInfo.getDriveLicenceIssueDate() != null) {
                    existingPersonInfo.setDriveLicenceIssueDate(personInfo.getDriveLicenceIssueDate());
                }
                if (personInfo.getJob() != null) {
                    existingPersonInfo.setJob(personInfo.getJob());
                }
                if (personInfo.getFathersFirstName() != null) {
                    existingPersonInfo.setFathersFirstName(personInfo.getFathersFirstName());
                }
                if (personInfo.getMotherFirstName() != null) {
                    existingPersonInfo.setMotherFirstName(personInfo.getMotherFirstName());
                }
                if (personInfo.getAddress() != null) {
                    existingPersonInfo.setAddress(personInfo.getAddress());
                }
                if (personInfo.getPhoto() != null) {
                    existingPersonInfo.setPhoto(personInfo.getPhoto());
                }
                if (personInfo.getPhotoContentType() != null) {
                    existingPersonInfo.setPhotoContentType(personInfo.getPhotoContentType());
                }
                if (personInfo.getWanted() != null) {
                    existingPersonInfo.setWanted(personInfo.getWanted());
                }

                return existingPersonInfo;
            })
            .map(personInfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /person-infos} : get all the personInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personInfos in body.
     */
    @GetMapping("/person-infos")
    public ResponseEntity<List<PersonInfo>> getAllPersonInfos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PersonInfos");
        Page<PersonInfo> page = personInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /person-infos/:id} : get the "id" personInfo.
     *
     * @param id the id of the personInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-infos/{id}")
    public ResponseEntity<PersonInfo> getPersonInfo(@PathVariable Long id) {
        log.debug("REST request to get PersonInfo : {}", id);
        Optional<PersonInfo> personInfo = personInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(personInfo);
    }

    @GetMapping("/person-infos/nationalID/{id}")
    public ResponseEntity<PersonInfoDTO> getPersonInfoByNationalID(@PathVariable String id) {
        log.debug("REST request to get PersonInfo : {}", id);
        Optional<PersonInfo> personInfo = personInfoRepository.findByNationalID(id);
        if (personInfo.isPresent()) {
            PersonInfo personInfo1 = personInfo.get();
            PersonInfoDTO personInfoDTO = new PersonInfoDTO();
            personInfoDTO.setAddress(personInfo1.getAddress());
            personInfoDTO.setId(personInfo1.getId());
            personInfoDTO.setCarInfos(carInfoRepository.findAllByPerson(id));
            personInfoDTO.setNationalID(personInfo1.getNationalID());
            personInfoDTO.setBirthDate(personInfo1.getBirthDate());
            personInfoDTO.setIdentityType(personInfo1.getIdentityType());
            personInfoDTO.setJob(personInfo1.getJob());
            personInfoDTO.setLastName(personInfo1.getLastName());
            personInfoDTO.setFirstName(personInfo1.getFirstName());
            personInfoDTO.setPassportID(personInfo1.getPassportID());
            personInfoDTO.setDriveLicenceID(personInfo1.getDriveLicenceID());
            personInfoDTO.setDriveLicenceIssueDate(personInfo1.getDriveLicenceIssueDate());
            personInfoDTO.setDriveLicenceType(personInfo1.getDriveLicenceType());
            personInfoDTO.setFathersFirstName(personInfo1.getFathersFirstName());
            personInfoDTO.setMotherFirstName(personInfo1.getMotherFirstName());
            personInfoDTO.setNationalIdIssueDate(personInfo1.getNationalIdIssueDate());
            personInfoDTO.setPassportIssueDate(personInfo1.getPassportIssueDate());
            personInfoDTO.setPhoto(personInfo1.getPhoto());
            personInfoDTO.setPhotoContentType(personInfo1.getPhotoContentType());
            personInfoDTO.setWanted(personInfo1.getWanted());
            return ResponseEntity.ok(personInfoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code DELETE  /person-infos/:id} : delete the "id" personInfo.
     *
     * @param id the id of the personInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-infos/{id}")
    public ResponseEntity<Void> deletePersonInfo(@PathVariable Long id) {
        log.debug("REST request to delete PersonInfo : {}", id);
        personInfoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
