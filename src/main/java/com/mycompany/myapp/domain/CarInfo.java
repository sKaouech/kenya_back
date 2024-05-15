package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CarInfo.
 */
@Entity
@Table(name = "car_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "license_plate_number")
    private String licensePlateNumber;

    @Column(name = "brand")
    private String brand;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "golan_flag_validity")
    private String golanFlagValidity;

    @Column(name = "vehicle_tax_validity_date")
    private LocalDate vehicleTaxValidityDate;

    @Column(name = "original_in_service_date")
    private LocalDate originalInServiceDate;

    @Column(name = "category")
    private String category;

    @Column(name = "insurance_validity_date")
    private LocalDate insuranceValidityDate;

    @Column(name = "technical_inspection")
    private String technicalInspection;

    @Column(name = "first_owner")
    private String firstOwner;

    @Column(name = "first_owner_id")
    private String firstOwnerID;

    @Column(name = "owners_id")
    private String ownersID;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "wanted")
    private Boolean wanted;

    @ManyToOne
    private PersonInfo personInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CarInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChassisNumber() {
        return this.chassisNumber;
    }

    public CarInfo chassisNumber(String chassisNumber) {
        this.setChassisNumber(chassisNumber);
        return this;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getLicensePlateNumber() {
        return this.licensePlateNumber;
    }

    public CarInfo licensePlateNumber(String licensePlateNumber) {
        this.setLicensePlateNumber(licensePlateNumber);
        return this;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getBrand() {
        return this.brand;
    }

    public CarInfo brand(String brand) {
        this.setBrand(brand);
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }

    public CarInfo purchaseDate(LocalDate purchaseDate) {
        this.setPurchaseDate(purchaseDate);
        return this;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getGolanFlagValidity() {
        return this.golanFlagValidity;
    }

    public CarInfo golanFlagValidity(String golanFlagValidity) {
        this.setGolanFlagValidity(golanFlagValidity);
        return this;
    }

    public void setGolanFlagValidity(String golanFlagValidity) {
        this.golanFlagValidity = golanFlagValidity;
    }

    public LocalDate getVehicleTaxValidityDate() {
        return this.vehicleTaxValidityDate;
    }

    public CarInfo vehicleTaxValidityDate(LocalDate vehicleTaxValidityDate) {
        this.setVehicleTaxValidityDate(vehicleTaxValidityDate);
        return this;
    }

    public void setVehicleTaxValidityDate(LocalDate vehicleTaxValidityDate) {
        this.vehicleTaxValidityDate = vehicleTaxValidityDate;
    }

    public LocalDate getOriginalInServiceDate() {
        return this.originalInServiceDate;
    }

    public CarInfo originalInServiceDate(LocalDate originalInServiceDate) {
        this.setOriginalInServiceDate(originalInServiceDate);
        return this;
    }

    public void setOriginalInServiceDate(LocalDate originalInServiceDate) {
        this.originalInServiceDate = originalInServiceDate;
    }

    public String getCategory() {
        return this.category;
    }

    public CarInfo category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getInsuranceValidityDate() {
        return this.insuranceValidityDate;
    }

    public CarInfo insuranceValidityDate(LocalDate insuranceValidityDate) {
        this.setInsuranceValidityDate(insuranceValidityDate);
        return this;
    }

    public void setInsuranceValidityDate(LocalDate insuranceValidityDate) {
        this.insuranceValidityDate = insuranceValidityDate;
    }

    public String getTechnicalInspection() {
        return this.technicalInspection;
    }

    public CarInfo technicalInspection(String technicalInspection) {
        this.setTechnicalInspection(technicalInspection);
        return this;
    }

    public void setTechnicalInspection(String technicalInspection) {
        this.technicalInspection = technicalInspection;
    }

    public String getFirstOwner() {
        return this.firstOwner;
    }

    public CarInfo firstOwner(String firstOwner) {
        this.setFirstOwner(firstOwner);
        return this;
    }

    public void setFirstOwner(String firstOwner) {
        this.firstOwner = firstOwner;
    }

    public String getFirstOwnerID() {
        return this.firstOwnerID;
    }

    public CarInfo firstOwnerID(String firstOwnerID) {
        this.setFirstOwnerID(firstOwnerID);
        return this;
    }

    public void setFirstOwnerID(String firstOwnerID) {
        this.firstOwnerID = firstOwnerID;
    }

    public String getOwnersID() {
        return this.ownersID;
    }

    public CarInfo ownersID(String ownersID) {
        this.setOwnersID(ownersID);
        return this;
    }

    public void setOwnersID(String ownersID) {
        this.ownersID = ownersID;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public CarInfo photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public CarInfo photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Boolean getWanted() {
        return this.wanted;
    }

    public CarInfo wanted(Boolean wanted) {
        this.setWanted(wanted);
        return this;
    }

    public void setWanted(Boolean wanted) {
        this.wanted = wanted;
    }

    public PersonInfo getPersonInfo() {
        return this.personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public CarInfo personInfo(PersonInfo personInfo) {
        this.setPersonInfo(personInfo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarInfo)) {
            return false;
        }
        return id != null && id.equals(((CarInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarInfo{" +
            "id=" + getId() +
            ", chassisNumber='" + getChassisNumber() + "'" +
            ", licensePlateNumber='" + getLicensePlateNumber() + "'" +
            ", brand='" + getBrand() + "'" +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", golanFlagValidity='" + getGolanFlagValidity() + "'" +
            ", vehicleTaxValidityDate='" + getVehicleTaxValidityDate() + "'" +
            ", originalInServiceDate='" + getOriginalInServiceDate() + "'" +
            ", category='" + getCategory() + "'" +
            ", insuranceValidityDate='" + getInsuranceValidityDate() + "'" +
            ", technicalInspection='" + getTechnicalInspection() + "'" +
            ", firstOwner='" + getFirstOwner() + "'" +
            ", firstOwnerID='" + getFirstOwnerID() + "'" +
            ", ownersID='" + getOwnersID() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", wanted='" + getWanted() + "'" +
            "}";
    }
}
