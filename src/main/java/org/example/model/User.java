package org.example.model;

import org.example.enums.Gender;

public class User {
    private Long id;
    private String name;
    private String family;
    private String nationalCode;
    private String address;
    private String phoneNumber;
    private Gender gender;

    public User(){

    }

    public User(String name, String family, String nationalCode, String address, String phoneNumber, Gender gender) {
        this.name = name;
        this.family = family;
        this.nationalCode = nationalCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
