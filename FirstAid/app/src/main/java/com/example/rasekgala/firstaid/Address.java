package com.example.rasekgala.firstaid;

import java.io.Serializable;

/**
 * Created by Rasekgala on 2016/09/19.
 */
public class Address implements Serializable
{
    private String streetName;
    private String city;
    private String Postalcode;
    private long address_id;


    public Address() {
    }

    public Address(String streetName, String city, String postalcode, long address_id) {
        this.streetName = streetName;
        this.city = city;
        Postalcode = postalcode;
        this.address_id = address_id;
    }
    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", Postalcode=" + Postalcode +
                ", address_id=" + address_id +
                '}';
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalcode() {
        return Postalcode;
    }

    public void setPostalcode(String postalcode) {
        Postalcode = postalcode;
    }

    public long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(long address_id) {
        this.address_id = address_id;
    }
}
