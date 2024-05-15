package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CarInfo;
import com.mycompany.myapp.repository.CarInfoRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CarInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CarInfoResource {

    private final Logger log = LoggerFactory.getLogger(CarInfoResource.class);

    private static final String ENTITY_NAME = "carInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarInfoRepository carInfoRepository;

    public CarInfoResource(CarInfoRepository carInfoRepository) {
        this.carInfoRepository = carInfoRepository;
    }

    /**
     * {@code POST  /car-infos} : Create a new carInfo.
     *
     * @param carInfo the carInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carInfo, or with status {@code 400 (Bad Request)} if the carInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/car-infos")
    public ResponseEntity<CarInfo> createCarInfo(@RequestBody CarInfo carInfo) throws URISyntaxException {
        log.debug("REST request to save CarInfo : {}", carInfo);
        if (carInfo.getId() != null) {
            throw new BadRequestAlertException("A new carInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarInfo result = carInfoRepository.save(carInfo);
        return ResponseEntity
            .created(new URI("/api/car-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /car-infos/:id} : Updates an existing carInfo.
     *
     * @param id the id of the carInfo to save.
     * @param carInfo the carInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carInfo,
     * or with status {@code 400 (Bad Request)} if the carInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/car-infos/{id}")
    public ResponseEntity<CarInfo> updateCarInfo(@PathVariable(value = "id", required = false) final Long id, @RequestBody CarInfo carInfo)
        throws URISyntaxException {
        log.debug("REST request to update CarInfo : {}, {}", id, carInfo);
        if (carInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CarInfo result = carInfoRepository.save(carInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, carInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /car-infos/:id} : Partial updates given fields of an existing carInfo, field will ignore if it is null
     *
     * @param id the id of the carInfo to save.
     * @param carInfo the carInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carInfo,
     * or with status {@code 400 (Bad Request)} if the carInfo is not valid,
     * or with status {@code 404 (Not Found)} if the carInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the carInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/car-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarInfo> partialUpdateCarInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CarInfo carInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update CarInfo partially : {}, {}", id, carInfo);
        if (carInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarInfo> result = carInfoRepository
            .findById(carInfo.getId())
            .map(existingCarInfo -> {
                if (carInfo.getChassisNumber() != null) {
                    existingCarInfo.setChassisNumber(carInfo.getChassisNumber());
                }
                if (carInfo.getLicensePlateNumber() != null) {
                    existingCarInfo.setLicensePlateNumber(carInfo.getLicensePlateNumber());
                }
                if (carInfo.getBrand() != null) {
                    existingCarInfo.setBrand(carInfo.getBrand());
                }
                if (carInfo.getPurchaseDate() != null) {
                    existingCarInfo.setPurchaseDate(carInfo.getPurchaseDate());
                }
                if (carInfo.getGolanFlagValidity() != null) {
                    existingCarInfo.setGolanFlagValidity(carInfo.getGolanFlagValidity());
                }
                if (carInfo.getVehicleTaxValidityDate() != null) {
                    existingCarInfo.setVehicleTaxValidityDate(carInfo.getVehicleTaxValidityDate());
                }
                if (carInfo.getOriginalInServiceDate() != null) {
                    existingCarInfo.setOriginalInServiceDate(carInfo.getOriginalInServiceDate());
                }
                if (carInfo.getCategory() != null) {
                    existingCarInfo.setCategory(carInfo.getCategory());
                }
                if (carInfo.getInsuranceValidityDate() != null) {
                    existingCarInfo.setInsuranceValidityDate(carInfo.getInsuranceValidityDate());
                }
                if (carInfo.getTechnicalInspection() != null) {
                    existingCarInfo.setTechnicalInspection(carInfo.getTechnicalInspection());
                }
                if (carInfo.getFirstOwner() != null) {
                    existingCarInfo.setFirstOwner(carInfo.getFirstOwner());
                }
                if (carInfo.getFirstOwnerID() != null) {
                    existingCarInfo.setFirstOwnerID(carInfo.getFirstOwnerID());
                }
                if (carInfo.getOwnersID() != null) {
                    existingCarInfo.setOwnersID(carInfo.getOwnersID());
                }
                if (carInfo.getPhoto() != null) {
                    existingCarInfo.setPhoto(carInfo.getPhoto());
                }
                if (carInfo.getPhotoContentType() != null) {
                    existingCarInfo.setPhotoContentType(carInfo.getPhotoContentType());
                }
                if (carInfo.getWanted() != null) {
                    existingCarInfo.setWanted(carInfo.getWanted());
                }

                return existingCarInfo;
            })
            .map(carInfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, carInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /car-infos} : get all the carInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carInfos in body.
     */
    @GetMapping("/car-infos")
    public ResponseEntity<List<CarInfo>> getAllCarInfos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CarInfos");
        Page<CarInfo> page = carInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /car-infos/:id} : get the "id" carInfo.
     *
     * @param id the id of the carInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/car-infos/{id}")
    public ResponseEntity<CarInfo> getCarInfo(@PathVariable Long id) {
        log.debug("REST request to get CarInfo : {}", id);
        Optional<CarInfo> carInfo = carInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(carInfo);
    }

    /**
     * {@code DELETE  /car-infos/:id} : delete the "id" carInfo.
     *
     * @param id the id of the carInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/car-infos/{id}")
    public ResponseEntity<Void> deleteCarInfo(@PathVariable Long id) {
        log.debug("REST request to delete CarInfo : {}", id);
        carInfoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
