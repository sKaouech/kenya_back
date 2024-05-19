package com.mycompany.myapp.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.myapp.domain.CarInfo;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PersonInfo.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nationalID;

    private String identityType;

    private String passportID;

    private String driveLicenceID;

    private String driveLicenceType;

    private String firstName;

    private String lastName;

    private LocalDate nationalIdIssueDate;

    private String birthDate;

    private LocalDate passportIssueDate;

    private LocalDate driveLicenceIssueDate;

    private String job;

    private String fathersFirstName;

    private String motherFirstName;

    private byte[] photo;

    private String photoContentType;

    private String address;

    private Boolean wanted;

    private List<CarInfo> carInfos = new ArrayList<>();

    public List<CarInfo> getCarInfos() {
        return carInfos;
    }

    public void setCarInfos(List<CarInfo> carInfos) {
        this.carInfos = carInfos;
    }

    public Long getId() {
        return this.id;
    }

    public PersonInfoDTO id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNationalID() {
        return this.nationalID;
    }

    public PersonInfoDTO nationalID(String nationalID) {
        this.setNationalID(nationalID);
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getIdentityType() {
        return this.identityType;
    }

    public PersonInfoDTO identityType(String identityType) {
        this.setIdentityType(identityType);
        return this;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getPassportID() {
        return this.passportID;
    }

    public PersonInfoDTO passportID(String passportID) {
        this.setPassportID(passportID);
        return this;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public String getDriveLicenceID() {
        return this.driveLicenceID;
    }

    public PersonInfoDTO driveLicenceID(String driveLicenceID) {
        this.setDriveLicenceID(driveLicenceID);
        return this;
    }

    public void setDriveLicenceID(String driveLicenceID) {
        this.driveLicenceID = driveLicenceID;
    }

    public String getDriveLicenceType() {
        return this.driveLicenceType;
    }

    public PersonInfoDTO driveLicenceType(String driveLicenceType) {
        this.setDriveLicenceType(driveLicenceType);
        return this;
    }

    public void setDriveLicenceType(String driveLicenceType) {
        this.driveLicenceType = driveLicenceType;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public PersonInfoDTO firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public PersonInfoDTO lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getNationalIdIssueDate() {
        return this.nationalIdIssueDate;
    }

    public PersonInfoDTO nationalIdIssueDate(LocalDate nationalIdIssueDate) {
        this.setNationalIdIssueDate(nationalIdIssueDate);
        return this;
    }

    public void setNationalIdIssueDate(LocalDate nationalIdIssueDate) {
        this.nationalIdIssueDate = nationalIdIssueDate;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public PersonInfoDTO birthDate(String birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getPassportIssueDate() {
        return this.passportIssueDate;
    }

    public PersonInfoDTO passportIssueDate(LocalDate passportIssueDate) {
        this.setPassportIssueDate(passportIssueDate);
        return this;
    }

    public void setPassportIssueDate(LocalDate passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public LocalDate getDriveLicenceIssueDate() {
        return this.driveLicenceIssueDate;
    }

    public PersonInfoDTO driveLicenceIssueDate(LocalDate driveLicenceIssueDate) {
        this.setDriveLicenceIssueDate(driveLicenceIssueDate);
        return this;
    }

    public void setDriveLicenceIssueDate(LocalDate driveLicenceIssueDate) {
        this.driveLicenceIssueDate = driveLicenceIssueDate;
    }

    public String getJob() {
        return this.job;
    }

    public PersonInfoDTO job(String job) {
        this.setJob(job);
        return this;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getFathersFirstName() {
        return this.fathersFirstName;
    }

    public PersonInfoDTO fathersFirstName(String fathersFirstName) {
        this.setFathersFirstName(fathersFirstName);
        return this;
    }

    public void setFathersFirstName(String fathersFirstName) {
        this.fathersFirstName = fathersFirstName;
    }

    public String getMotherFirstName() {
        return this.motherFirstName;
    }

    public PersonInfoDTO motherFirstName(String motherFirstName) {
        this.setMotherFirstName(motherFirstName);
        return this;
    }

    public void setMotherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public PersonInfoDTO photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public PersonInfoDTO photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Boolean getWanted() {
        return this.wanted;
    }

    public PersonInfoDTO wanted(Boolean wanted) {
        this.setWanted(wanted);
        return this;
    }

    public void setWanted(Boolean wanted) {
        this.wanted = wanted;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonInfoDTO)) {
            return false;
        }
        return id != null && id.equals(((PersonInfoDTO) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonInfo{" +
            "id=" + getId() +
            ", nationalID='" + getNationalID() + "'" +
            ", identityType='" + getIdentityType() + "'" +
            ", passportID='" + getPassportID() + "'" +
            ", driveLicenceID='" + getDriveLicenceID() + "'" +
            ", driveLicenceType='" + getDriveLicenceType() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", nationalIdIssueDate='" + getNationalIdIssueDate() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", passportIssueDate='" + getPassportIssueDate() + "'" +
            ", driveLicenceIssueDate='" + getDriveLicenceIssueDate() + "'" +
            ", job='" + getJob() + "'" +
            ", fathersFirstName='" + getFathersFirstName() + "'" +
            ", motherFirstName='" + getMotherFirstName() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", wanted='" + getWanted() + "'" +
            "}";
    }
}
