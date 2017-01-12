package com.example.rasekgala.firstaid;

import java.io.Serializable;

/**
 * Created by Rasekgala on 2016/09/19.
 */
public class Patient implements Serializable
{
    private String name;
    private String gender;
    private String surname;
    private long IdNumber;
    private String email;
    private String phoneNo;
    private String docPhoneNo;
    private long doctorCode;

    public Patient(String name, String gender, String surname, long idNumber, String email, String phoneNo, long doctorCode, String docPhoneNo) {
        this.name = name;
        this.gender = gender;
        this.surname = surname;
        IdNumber = idNumber;
        this.email = email;
        this.docPhoneNo = docPhoneNo;
        this.phoneNo = phoneNo;
        this.doctorCode = doctorCode;
    }

    public Patient() {
    }

    public String getDocPhoneNo() {
        return docPhoneNo;
    }

    public void setDocPhoneNo(String docPhoneNo) {
        this.docPhoneNo = docPhoneNo;
    }

    public long getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(long doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public long getIdNumber() {
        return IdNumber;
    }

    public void setIdNumber(long idNumber) {
        IdNumber = idNumber;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", surname='" + surname + '\'' +
                ", IdNumber=" + IdNumber +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                '}';
    }
}
