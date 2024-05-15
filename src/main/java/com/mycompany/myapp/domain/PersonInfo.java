package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PersonInfo.
 */
@Entity
@Table(name = "person_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "national_id")
    private String nationalID;

    @Column(name = "identity_type")
    private String identityType;

    @Column(name = "passport_id")
    private String passportID;

    @Column(name = "drive_licence_id")
    private String driveLicenceID;

    @Column(name = "drive_licence_type")
    private String driveLicenceType;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "national_id_issue_date")
    private LocalDate nationalIdIssueDate;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "passport_issue_date")
    private LocalDate passportIssueDate;

    @Column(name = "drive_licence_issue_date")
    private LocalDate driveLicenceIssueDate;

    @Column(name = "job")
    private String job;

    @Column(name = "fathers_first_name")
    private String fathersFirstName;

    @Column(name = "mother_first_name")
    private String motherFirstName;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "wanted")
    private Boolean wanted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNationalID() {
        return this.nationalID;
    }

    public PersonInfo nationalID(String nationalID) {
        this.setNationalID(nationalID);
        return this;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getIdentityType() {
        return this.identityType;
    }

    public PersonInfo identityType(String identityType) {
        this.setIdentityType(identityType);
        return this;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getPassportID() {
        return this.passportID;
    }

    public PersonInfo passportID(String passportID) {
        this.setPassportID(passportID);
        return this;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public String getDriveLicenceID() {
        return this.driveLicenceID;
    }

    public PersonInfo driveLicenceID(String driveLicenceID) {
        this.setDriveLicenceID(driveLicenceID);
        return this;
    }

    public void setDriveLicenceID(String driveLicenceID) {
        this.driveLicenceID = driveLicenceID;
    }

    public String getDriveLicenceType() {
        return this.driveLicenceType;
    }

    public PersonInfo driveLicenceType(String driveLicenceType) {
        this.setDriveLicenceType(driveLicenceType);
        return this;
    }

    public void setDriveLicenceType(String driveLicenceType) {
        this.driveLicenceType = driveLicenceType;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public PersonInfo firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public PersonInfo lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getNationalIdIssueDate() {
        return this.nationalIdIssueDate;
    }

    public PersonInfo nationalIdIssueDate(LocalDate nationalIdIssueDate) {
        this.setNationalIdIssueDate(nationalIdIssueDate);
        return this;
    }

    public void setNationalIdIssueDate(LocalDate nationalIdIssueDate) {
        this.nationalIdIssueDate = nationalIdIssueDate;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public PersonInfo birthDate(String birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getPassportIssueDate() {
        return this.passportIssueDate;
    }

    public PersonInfo passportIssueDate(LocalDate passportIssueDate) {
        this.setPassportIssueDate(passportIssueDate);
        return this;
    }

    public void setPassportIssueDate(LocalDate passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public LocalDate getDriveLicenceIssueDate() {
        return this.driveLicenceIssueDate;
    }

    public PersonInfo driveLicenceIssueDate(LocalDate driveLicenceIssueDate) {
        this.setDriveLicenceIssueDate(driveLicenceIssueDate);
        return this;
    }

    public void setDriveLicenceIssueDate(LocalDate driveLicenceIssueDate) {
        this.driveLicenceIssueDate = driveLicenceIssueDate;
    }

    public String getJob() {
        return this.job;
    }

    public PersonInfo job(String job) {
        this.setJob(job);
        return this;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getFathersFirstName() {
        return this.fathersFirstName;
    }

    public PersonInfo fathersFirstName(String fathersFirstName) {
        this.setFathersFirstName(fathersFirstName);
        return this;
    }

    public void setFathersFirstName(String fathersFirstName) {
        this.fathersFirstName = fathersFirstName;
    }

    public String getMotherFirstName() {
        return this.motherFirstName;
    }

    public PersonInfo motherFirstName(String motherFirstName) {
        this.setMotherFirstName(motherFirstName);
        return this;
    }

    public void setMotherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public PersonInfo photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public PersonInfo photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public Boolean getWanted() {
        return this.wanted;
    }

    public PersonInfo wanted(Boolean wanted) {
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
        if (!(o instanceof PersonInfo)) {
            return false;
        }
        return id != null && id.equals(((PersonInfo) o).id);
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
