package com.spring.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Organization.
 */
@Entity
@Table(name = "ORGANIZATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 6, max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @NotNull
    @Size(min = 6, max = 60)
    @Column(name = "address_1", length = 60, nullable = false)
    private String address_1;

    @NotNull
    @Size(min = 6, max = 60)
    @Column(name = "address_2", length = 60, nullable = false)
    private String address_2;

    @NotNull
    @Size(min = 3)
    @Column(name = "country", nullable = false)
    private String country;

    @NotNull
    @Size(min = 3)
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Size(min = 6)
    @Column(name = "pincode", nullable = false)
    private String pincode;

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

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Organization organization = (Organization) o;

        if ( ! Objects.equals(id, organization.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", address_1='" + address_1 + "'" +
                ", address_2='" + address_2 + "'" +
                ", country='" + country + "'" +
                ", city='" + city + "'" +
                ", pincode='" + pincode + "'" +
                '}';
    }
}
